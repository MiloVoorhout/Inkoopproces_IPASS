function initPage() {
	const goBack = document.querySelector('.fa-arrow-alt-circle-left');
	const modal = document.querySelector('.modal');
	const openModalButton = document.querySelector('.fa-plus-circle');
	const closeModalButton = document.querySelector('[data-close-button]');
	const overlay = document.getElementById('overlay');
	$("#productSelect, #voorstelAantal, #productReden, #productNaam, #productPrijs, #productCategorie").val('');
	
	
	openModalButton.addEventListener('click', function(){
		modal.classList.add('active')
		overlay.classList.add('active')
	})

	overlay.addEventListener('click', function(){
		$("#productNaam, #productPrijs, #productCategorie").val('');
		modal.classList.remove('active');
		overlay.classList.remove('active');
	})

	closeModalButton.addEventListener('click', function() {
		$("#productNaam, #productPrijs, #productCategorie").val('');
		modal.classList.remove('active');
		overlay.classList.remove('active');
	})
	
	goBack.addEventListener('click', function() {
		window.location.href = "/inkoop/menu.html";
	})
	
	loadProducts();
	saveProductVoorstel();
	loadGebruiker();
	saveAankoopVoorstel();
}

function loadProducts() {	
	fetch('restservices/product')
	.then(response => response.json())
	.then(function(products){
		var productSelectBody = document.querySelector("#productSelect");
		var categories = [];

        for (const product of products) {
            if (!categories.includes(product.categorie)) {
                categories[product.categorie] = [];
                categories[product.categorie].push(product);
                categories.push(product.categorie);
            } else {
            	categories[product.categorie].push(product);
            }
        }
                
        for (const cat of categories) {
        	$("#productSelect").append('<optgroup label="' + cat + '" id="' + cat + '">');
        	for (const item of categories[cat]) {
            	$("#" + cat + "").append('<option value="' + item.id + '">' + item.naam + '</option>');
        	}
        	$("#" + cat + "").append('</optgroup>');
        }
        
        $("#productSelect").val('');
        
	})
}

function saveProductVoorstel() {
	var saveBtn = document.querySelector(".product-insturen");
	saveBtn.addEventListener("click", function(){
		var productNaam = document.getElementById('productNaam').value;
		var productPrijs = document.getElementById('productPrijs').value;
		var productCategorie = document.getElementById('productCategorie').value;
		var gebruikerId = sessionStorage.getItem("id");
		
		if(productPrijs.length !== 0 && productNaam.length !== 0 && productCategorie.length !== 0) {
			fetch("/inkoop/restservices/gekeurde_voorstellen/save", { 
				method: 'POST', 
				body: JSON.stringify({productNaam, gebruikerId})})
		    .then(response => response.json())
		    .then(function(response) {	
		    	if (response !== -1) {
			    	fetch("/inkoop/restservices/product_voorstel/save", { 
						method: 'POST', 
						body: JSON.stringify({productNaam, productPrijs, productCategorie, gebruikerId, response})})
					    .then(response => response.json())
					    .then(function(myJson) {
					    	console.log(myJson);
							$("#productNaam, #productPrijs, #productCategorie").val('');
														
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
			var toastUp = document.getElementById("toastFout");
			toastUp.className = "show";
			setTimeout(function(){ 
				toastUp.className = toastUp.className.replace("show", ""); 
			}, 3000);
		}
	})
}

function loadGebruiker() {
	var gebruikerId = sessionStorage.getItem("id");
	
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
	var insturen = document.querySelector('.insturen-button');

	insturen.addEventListener("click", function(){
		var productAantal = (document.querySelector("#voorstelAantal")).value;
		var productId = $("#productSelect").val();
		var pNaam = $("#productSelect option:selected").text();
		var productNaam = productAantal + ' ' + pNaam;
		var gebruikerId = sessionStorage.getItem("id");;
		var aankoopReden = (document.querySelector("#productReden")).value;
		
		const checkAantal = /^\d+$/.test(productAantal);
		const checkReden = $("#productReden").val();
		
		if(checkAantal !== false && productAantal.length !== 0 && checkReden.length !== 0 && productId !== null) {
			fetch("/inkoop/restservices/gekeurde_voorstellen/save", { 
				method: 'POST', 
				body: JSON.stringify({productNaam, gebruikerId})})
		    .then(response => response.json())
		    .then(function(response) {	
		    	if (response !== -1) {
			    	fetch("/inkoop/restservices/aankoop_voorstellen/save", { 
						method: 'POST', 
						body: JSON.stringify({productAantal, aankoopReden, productId, gebruikerId, response})
					})
					    .then(response => response.json())
					    .then(function(myJson) {
					    	console.log(myJson);
					    	$("#productSelect, #voorstelAantal, #productReden").val(null).trigger('change');

							
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
			var toastUp = document.getElementById("toastFout");
			toastUp.className = "show";
			setTimeout(function(){ 
				toastUp.className = toastUp.className.replace("show", ""); 
			}, 3000);
		}
	})
}