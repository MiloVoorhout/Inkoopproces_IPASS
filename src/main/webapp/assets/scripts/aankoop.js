//Check if user is logged in
if (window.sessionStorage.getItem("sessionToken") === null) {
	//If not send user back to login page
	window.location.href = "/index.html";
	return;
}

function initPage() {
	//Get everything we need for button events
	const goBack = document.querySelector('.fa-arrow-alt-circle-left');
	const modal = document.querySelector('.modal');
	const openModalButton = document.querySelector('.fa-plus-circle');
	const closeModalButton = document.querySelector('[data-close-button]');
	const overlay = document.getElementById('overlay');
	//Empty every input field on the page
	$("#productSelect, #voorstelAantal, #productReden, #productNaam, #productPrijs, #productCategorie").val('');
	
	//Give an event to the modal opening button
	openModalButton.addEventListener('click', function(){
		modal.classList.add('active')
		overlay.classList.add('active')
	})

	//Give an event for clicking outside the modal
	overlay.addEventListener('click', function(){
		//empty the input fields
		$("#productNaam, #productPrijs, #productCategorie").val('');
		modal.classList.remove('active');
		overlay.classList.remove('active');
	})

	//Give an event for closing the modal button
	closeModalButton.addEventListener('click', function() {
		//empty the input fields
		$("#productNaam, #productPrijs, #productCategorie").val('');
		modal.classList.remove('active');
		overlay.classList.remove('active');
	})
		
	//Give the go back button a event
	goBack.addEventListener('click', function() {
		window.location.href = "/menu.html";
	})
		
	loadProducts();
	saveProductVoorstel();
	loadGebruiker();
	saveAankoopVoorstel();
}

function loadProducts() {	
	//Get all products
	fetch('restservices/product')
	.then(response => response.json())
	.then(function(products){
		var productSelectBody = document.querySelector("#productSelect");
		var categories = [];
		
		//Go through every product
        for (const product of products) {
        	//Check if categorie of product already is added to categories
            if (!categories.includes(product.categorie)) {
                categories[product.categorie] = [];
                categories[product.categorie].push(product);
                categories.push(product.categorie);
            } else {
            	categories[product.categorie].push(product);
            }
        }
        
        //Go through the categories arraylist
        for (const cat of categories) {
        	//Make for every category a group in the select2
        	$("#productSelect").append('<optgroup label="' + cat + '" id="' + cat + '">');
        	for (const item of categories[cat]) {
        		//Add for every category all its products
            	$("#" + cat + "").append('<option value="' + item.id + '">' + item.naam + '</option>');
        	}
        	$("#" + cat + "").append('</optgroup>');
        }
        
        //Set the automatically selected value to nothing
        $("#productSelect").val('');
        
	})
}

function saveProductVoorstel() {
	//Make an event for saving a product proposal
	var saveBtn = document.querySelector(".product-insturen");
	saveBtn.addEventListener("click", function(){
		//Get the value of every input field
		var productNaam = document.getElementById('productNaam').value;
		var productPrijs = document.getElementById('productPrijs').value;
		var productCategorie = document.getElementById('productCategorie').value;
		var gebruikerId = sessionStorage.getItem("id");
		
		//Check if every field is answered
		if(productPrijs.length !== 0 && productNaam.length !== 0 && productCategorie.length !== 0) {
			//First save a status in the approved proposals table
			fetch("restservices/gekeurde_voorstellen/save", { 
				method: 'POST', 
				body: JSON.stringify({productNaam, gebruikerId})})
		    .then(response => response.json())
		    .then(function(response) {	
		    	//The response gives back the id of the newly added approved proposal
		    	//Check if the response gives a good response
		    	if (response !== -1) {
		    		//Save the product product proposal
			    	fetch("restservices/product_voorstel/save", { 
						method: 'POST', 
						body: JSON.stringify({productNaam, productPrijs, productCategorie, gebruikerId, response})})
					    .then(response => response.json())
					    .then(function(myJson) {
					    	//Empty the input fields
							$("#productNaam, #productPrijs, #productCategorie").val('');
							
							//Close the modal
							const modal = document.querySelector('.modal');
							const overlay = document.getElementById('overlay');
							
							modal.classList.remove('active');
							overlay.classList.remove('active');
							
							//And give a toast that everything went well
							var toastUp = document.getElementById("toastGoed");
							toastUp.innerHTML = "Product voorstel ingediend!";
							toastUp.className = "show";
							setTimeout(function(){ 
								toastUp.className = toastUp.className.replace("show", ""); 
							}, 3000);
					    })
		    	}
		    })
		} else {
			//Give a toast that it didn't go well
			var toastUp = document.getElementById("toastFout");
			toastUp.className = "show";
			setTimeout(function(){ 
				toastUp.className = toastUp.className.replace("show", ""); 
			}, 3000);
		}
	})
}

function loadGebruiker() {
	//Get users id
	var gebruikerId = sessionStorage.getItem("id");
	
	//Load the information of the user in question
	fetch('restservices/gebruiker/'+gebruikerId)
	.then(response => response.json())
	.then(function(gebruiker){
		gebruiker = gebruiker[0];
		var gebruikerId = document.querySelector(".gebruikerId");
		var gebruikerAfdeling = document.querySelector(".gebruikerAfdeling");
		var gebruikerNaam = document.querySelector(".gebruikerNaam");

		gebruikerId.innerHTML += '&nbsp;&nbsp;&nbsp;' + gebruiker.id;
		gebruikerAfdeling.innerHTML += '&nbsp;&nbsp;&nbsp;' + gebruiker.afdeling;
		gebruikerNaam.innerHTML += '&nbsp;&nbsp;&nbsp;' + gebruiker.naam;
	});
}

function saveAankoopVoorstel() {
	//Making an event for save a purchase proposal button
	var insturen = document.querySelector('.insturen-button');

	insturen.addEventListener("click", function(){
		//Get all the information needed
		var productAantal = (document.querySelector("#voorstelAantal")).value;
		var productId = $("#productSelect").val();
		var pNaam = $("#productSelect option:selected").text();
		var productNaam = productAantal + ' ' + pNaam;
		var gebruikerId = sessionStorage.getItem("id");;
		var aankoopReden = (document.querySelector("#productReden")).value;
		
		const checkAantal = /^\d+$/.test(productAantal);
		const checkReden = $("#productReden").val();
		
		//Check if everything is answered
		if(checkAantal !== false && productAantal.length !== 0 && checkReden.length !== 0 && productId !== null) {
			//First save a status in the approved proposals table
			fetch("restservices/gekeurde_voorstellen/save", { 
				method: 'POST', 
				body: JSON.stringify({productNaam, gebruikerId})})
		    .then(response => response.json())
		    .then(function(response) {	
		    	//The response gives back the id of the newly added approved proposal
		    	//Check if the response gives a good response
		    	if (response !== -1) {
		    		//Save the purchase proposal
			    	fetch("restservices/aankoop_voorstellen/save", { 
						method: 'POST', 
						body: JSON.stringify({productAantal, aankoopReden, productId, gebruikerId, response})
					})
					    .then(response => response.json())
					    .then(function(myJson) {
					    	//empty the input fields
					    	$("#productSelect, #voorstelAantal, #productReden").val(null).trigger('change');

							//And give a toast that everything went well
							var toastUp = document.getElementById("toastGoed");
							toastUp.innerHTML = "Aankoop voorstel ingediend!";
							toastUp.className = "show";
							setTimeout(function(){ 
								toastUp.className = toastUp.className.replace("show", ""); 
							}, 3000);
					    })
		    	}
		    })
		} else {
			//Give a toast that it didn't go well
			var toastUp = document.getElementById("toastFout");
			toastUp.className = "show";
			setTimeout(function(){ 
				toastUp.className = toastUp.className.replace("show", ""); 
			}, 3000);
		}
	})
}