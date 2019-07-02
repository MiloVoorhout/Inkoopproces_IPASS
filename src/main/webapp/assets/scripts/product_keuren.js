function initPage() {
	//Check if user is logged in
	if (window.sessionStorage.getItem("sessionToken") === null) {
		//If not send him to login page
		window.location.href = "/index.html";
		return;
	} else {
		//Set the select value to product
		document.querySelector('#voorstellen').value = 'product';
		
		//Make an event for the go back button
		const goBack = document.querySelector('.fa-arrow-alt-circle-left');
		   
		goBack.addEventListener('click', function() {
			window.location.href = "/menu.html";
		})
		
		//Start load product proposals
		loadProductVoorstellen(); 
	}
}

function loadProductVoorstellen() {
	//Get needed information
	var productVoorstelBody = document.querySelector("#voorstel");
	var budget = document.querySelector(".budget-div");
	var userId = sessionStorage.getItem("id");
	
	//Make budget and product proposal empty
	if(budget != null) {
		budget.innerHTML = '';
	}
	productVoorstelBody.innerHTML = '';
	
	//Check window width
	if (!($(window).width() < 960)) {
		//Set max-width to 50%
		$(".keuren-content").css("max-width",  "50%");
	} else {
		//If window is smaller then 960 set the following css rules
		$(".voorstel-div").css({'width': '%', 'height' : '', 'overflow-y' : '', 'display' : 'inline-block', 'padding' : '4px 0px 4px 0px'});
	}
	//Turn the budget-div class off
	$( "div.tableClass" ).toggleClass("budget-div");
	
	//Get all product proposals that don't match the users id
	fetch('restservices/product_voorstel/' + userId, {method: 'GET', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
	.then(response => response.json())
	.then(function(pvoorstellen){
		//Check if there are any product proposals
		if (pvoorstellen.length === 0) {
			//If not set it in the html body
			productVoorstelBody.innerHTML +=	'<div>' +
										'<label>Er zijn geen product voorstellen</label>' +
									'</div>';
		} else {
			//If there are any product proposals add them
			for(const voorstel of pvoorstellen) {
				var voorstelDiv = document.querySelector(".voorstel-div");
				//Set div width
				voorstelDiv.style.width = "100%";
				
				//Add the product proposals to the html and give the necessary information width
				productVoorstelBody.innerHTML += 
					'<div class="voorstel-hole" productVoorstelId="' + voorstel.id +'" gkVoorstelId="' + voorstel.gk_id +'" productNaam="' + voorstel.naam +'" productPrijs="' + (voorstel.prijs).toFixed(2) +'" productCategorie="' + voorstel.categorie +'">' +
						'<div class="voorstel-block">' +
		    				'<div>' +
		    					'<label>Product: ' + voorstel.naam + '</label>' +
		    				'</div>' +
				    		'<div>' +
				    			'<label>Prijs: € ' + (voorstel.prijs).toFixed(2) + '</label>' +
				    		'</div>' +
					    	'<div>' +
								'<label>Categorie: ' + voorstel.categorie + '</label>' + 
				    		'</div>' +
						'</div>' +
						'<div class="voorstel-buttons">' +
							'<i class="fas fa-check"></i>' +
							'<i class="fas fa-times"></i>' +
						'</div>' +
					'</div>';
			}
			
			//Start product proposals buttons
			productVoorstelGoedKeuren();
			productVoorstelAfKeuren()
		}
	})
}

function productVoorstelGoedKeuren() {
	//Get every aprove button
	var productVoorstelToevoegen = document.querySelectorAll(".fa-check");
	
	for(const pVoorstel of productVoorstelToevoegen) {
		//Give all aprove buttons a click function
		pVoorstel.addEventListener("click", function(){
			//Get all the information needed for approval
			productVoorstelId = this.parentNode.parentNode.getAttribute("productVoorstelId");
			gkVoorstelId = this.parentNode.parentNode.getAttribute("gkVoorstelId");
			updateStatus = "Goed gekeurd";
			
			productNaam = this.parentNode.parentNode.getAttribute("productNaam");
			productPrijs = this.parentNode.parentNode.getAttribute("productPrijs");
			productCategorie = this.parentNode.parentNode.getAttribute("productCategorie");
			
			//First update the status of approved proposals to Approved
			fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
			.then(response => response.json())
		    .then(function(response){
		    	if(response) {
		    		//If response is ok save the product
		    		fetch("restservices/product/save/", {
		    			method: 'POST',
		    			body: JSON.stringify({productNaam, productPrijs, productCategorie}),
		    			headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}
		    		})
		    		.then(response => response.json())
				    .then(function(response){
				    	if(response) {
				    		//If response is ok then delete the product in question
							fetch("restservices/product_voorstel/delete/"+productVoorstelId, {method: 'DELETE', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
						    .then(function(response){		
						    	//Turn on budget-div to turn it off when loading product proposals
						    	$( "div.tableClass" ).toggleClass("budget-div");
						    	//Load product proposals again
						    	loadProductVoorstellen();
						    })
				    	}
				    })
		    	}
		    })
		});
	}
}

function productVoorstelAfKeuren() {
	//Get all product proposal disapprove buttons
	var productVoorstelVerwijderen = document.querySelectorAll(".fa-times");
	
	for(const pVoorstel of productVoorstelVerwijderen) {
		//Make for every button a click event
		pVoorstel.addEventListener("click", function(){
			//Get all the information needed
			productVoorstelId = this.parentNode.parentNode.getAttribute("productVoorstelId");
			gkVoorstelId = this.parentNode.parentNode.getAttribute("gkVoorstelId");
			updateStatus = "Afgekeurd";
			
			//Change the approved proposals status to disapproved
			fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
			.then(response => response.json())
		    .then(function(response){
		    	if(response) {
		    		//If the response is ok delete the product proposal in question
		    		fetch("restservices/product_voorstel/delete/"+productVoorstelId, {method: 'DELETE', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
		    		.then(function(response){		
		    			//Turn on the budget-div to turn of again when loading products
		    			$( "div.tableClass" ).toggleClass("budget-div");
		    			//Load product proposals
		    			loadProductVoorstellen();
				    })
		    	}
		    })
		});
	}
}

function loadAankoopVoorstellen() {
	//Get all the information needed
	var budget = document.querySelector(".budget-div");
	budget.innerHTML = '';
	
	var aankoopVoorstelBody = document.querySelector("#voorstel");
	aankoopVoorstelBody.innerHTML = '';
	
	var userId = sessionStorage.getItem("id");
	
	if (!($(window).width() < 960)) {
		//If window with bigger then 960px then change the max width to 65%
		$(".keuren-content").css("max-width", "65%");
	} else {
		//If it is not bigger then 960px turn on the following css rules
		$(".voorstel-div").css({'width': '80%', 'height' : '32vh', 'overflow-y' : 'auto', 'display' : 'inline-block', 'padding' : '4px 0px 4px 0px'});
	}
	
	//Get every purchase proposal that doesn't match the users id
	fetch('restservices/aankoop_voorstellen/'+userId, {method: 'GET', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
	.then(response => response.json())
	.then(function(aankoopVoorstellen){
		if (aankoopVoorstellen.length === 0) {
			//If there are not purchase proposals then show that there aren't any
			aankoopVoorstelBody.innerHTML +=	'<div>' +
											'<label>Er zijn geen aankoop voorstellen</label>' +
										'</div>';
		} else {
			for(const aVoorstel of aankoopVoorstellen) {
				//Add every purchase proposal to the html body
				var voorstelDiv = document.querySelector(".voorstel-div");
				voorstelDiv.style.width = "80%";
				
				//Also give the necessary information with every purchase proposal
				aankoopVoorstelBody.innerHTML += 
					'<div class="voorstel-hole" aankoopVoorstelId="' + aVoorstel.id +'" gkVoorstelId="' + aVoorstel.gk_id +'" afdelingGebruiker="' + aVoorstel.afdeling +'" totaalPrijs="' + (aVoorstel.totaalPrijs).toFixed(2) +'">' +
				 		'<div class="voorstel-block">' +
				 			'<div>' +
				 				'<label>Product: ' + aVoorstel.naam + '</label>' +
				 			'</div>' +
				 			'<div>' + 
				 				'<label>Aantal: ' + aVoorstel.aantal + '</label>' +
				 			'</div>' +
				 			'<div>' +
				 				'<label>Totaal prijs: € ' + (aVoorstel.totaalPrijs).toFixed(2) + '</label>' +
				 			'</div>' +
				 			'<div>' +
			 				'<label>Afdeling: ' + aVoorstel.afdeling + '</label>' +
			 			'</div>' +
				 			'<div>' +
				 				'<label>Reden: ' + aVoorstel.reden + '</label>' +
				 			'</div>' +
				 		'</div>' +
				
				 		'<div class="voorstel-buttons">' +
				 			'<i class="fas fa-check"></i>' +
				 			'<i class="fas fa-times"></i>' +
				 		'</div>' + 
				 	'</div>';
			}
			
			//Start the following function
			aankoopVoorstelGoedKeuren();
			aankoopVoorstelAfKeuren();
			loadBudget();
		}
	})
}

function aankoopVoorstelGoedKeuren() {
	//Get every aprove button for the purchase proposals
	var aankoopVoorstellenGoedkeuren = document.querySelectorAll(".fa-check");
	
	for(const aVoorstel of aankoopVoorstellenGoedkeuren) {
		//Add for every button a click event
		aVoorstel.addEventListener("click", function(){
			//Get all the necessary information
			aankoopVoorstelId = this.parentNode.parentNode.getAttribute("aankoopVoorstelId");
			gkVoorstelId = this.parentNode.parentNode.getAttribute("gkVoorstelId");
			updateStatus = "Goed gekeurd";
			
			type = "min";
			budgetAfdeling = this.parentNode.parentNode.getAttribute("afdelingGebruiker");
			budgetPrijs = this.parentNode.parentNode.getAttribute("totaalPrijs");
			
			//Change the approved proposals status to approved
			fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
			.then(response => response.json())
		    .then(function(response){
		    	if(response) {
		    		//If response ok then update the budget by decreasing the budget with the total price
		    		fetch("restservices/budget/update/aankoop_voorstel", {method: 'PUT', body: JSON.stringify({budgetAfdeling, budgetPrijs, type}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
					.then(response => response.json())
				    .then(function(response){
				    	if(response) {
				    		//If response is ok then delete the purchase proposal in question
				    		fetch("restservices/aankoop_voorstellen/delete/"+aankoopVoorstelId, {method: 'DELETE', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
				    		.then(function(response){				    			
				    			//Reload purchase proposals
				    			loadAankoopVoorstellen();
						    })
				    	}
				    })
		    	}
		    })
		});
	}
}

function aankoopVoorstelAfKeuren() {
	//Get every disapprove button for the purchase proposals
	var productVoorstellenVerwijderen = document.querySelectorAll(".fa-times");
	
	for(const aVoorstel of productVoorstellenVerwijderen) {
		//Give to every button a click function
		aVoorstel.addEventListener("click", function(){
			//Get all the necessary information
			aankoopVoorstelId = this.parentNode.parentNode.getAttribute("aankoopVoorstelId");
			gkVoorstelId = this.parentNode.parentNode.getAttribute("gkVoorstelId");
			updateStatus = "Afgekeurd";
			
			//Change the approved proposals status to disapproved
			fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
			.then(response => response.json())
		    .then(function(response){
		    	if(response) {
		    		//If the response is ok delete the purchase proposal in question
		    		fetch("restservices/aankoop_voorstellen/delete/"+aankoopVoorstelId, {method: 'DELETE', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
		    		.then(function(response){		    			
		    			//Reload purchase proposals
		    			loadAankoopVoorstellen();
				    })
		    	}
		    })
		});
	}
}


function loadBudget() {
	//Make the budget div needed
	var budgetAfdeling = document.querySelector(".budget-afdelingen");
	var budgetDiv = document.querySelector(".budget-div");
	
	budgetAfdelingen.innerHTML = '';
	budgetDiv.innerHTML = '';
	
	budgetDiv.innerHTML += '<table boder="1" class="budget-table">' +
								'<thead>' +
									'<tr>' +
										'<th>Afdeling</th>' +
										'<th>Budget</th>' +
									'</tr>' +
								'</thead>' +
								'<div>' +
									'<tbody id="budgetTabel">' +
									'</tbody>' +
								'</div>' +
							'</table>' +
							'<input type="submit" class="budget-button" name="budget-voorstel" value="Budget +">';
		
		

	//Get every budget that there is
	fetch('restservices/budget', {method : 'GET', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
	.then(response => response.json())
	.then(function(budgets){
		var table = document.querySelector("#budgetTabel");

		for(budget of budgets) {
			//Add every budget to a table
			var newRow = table.insertRow(-1);
			
			var cel1 = newRow.insertCell(0);
			var cel2 = newRow.insertCell(1);
			
			cel1.innerHTML = budget.afdeling;
			cel2.innerHTML = '€ ' + (budget.budget).toFixed(2);
			
			budgetAfdeling.innerHTML += '<option value="' + budget.id + '">' + budget.afdeling + '</option>';
		}
		
		//Start the following function
		budgetVoorstel();
		makeModal();
		loadGebruiker();
	});	
}

function select(selectOption) {
	//Start the function based on selected option
	if(selectOption.value == "product") {
		loadProductVoorstellen();
	} else {
		//Toggle on the budget-div
		$( "div.tableClass" ).toggleClass("budget-div");
		loadAankoopVoorstellen();
   }
}

function budgetVoorstel() {
	//Get the budget send in button
	var voorstelButton = document.querySelector(".product-insturen");
	
	voorstelButton.addEventListener("click", function () {
		//Get all the necessary information
		var budgetAfdelingSelector = (document.querySelector(".budget-afdelingen"));
		var budgetId = budgetAfdelingSelector.options[budgetAfdelingSelector.selectedIndex].value;
		var budgetAfdeling = budgetAfdelingSelector.options[budgetAfdelingSelector.selectedIndex].text;
		
		var budgetVergroting = (document.querySelector(".budget-vergroting")).value;
		var gebruikerId = sessionStorage.getItem("id");;

		var productNaam = "Afdeling: " + budgetAfdeling + ' - ' + '€ ' + budgetVergroting;
		
		const warning = document.querySelector(".warningModal");
		
		
		if(budgetVergroting.length !== 0) {
			//If all the fields have a input start the following fetch
			//Make a approved proposal 
			fetch("restservices/gekeurde_voorstellen/save", { 
				method: 'POST', 
				body: JSON.stringify({productNaam, gebruikerId}),
				headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
		    .then(response => response.json())
		    .then(function(response) {	
		    	if (response !== -1) {
		    		//If response is ok save the budget proposal
			    	fetch("restservices/budget_voorstellen/save", { 
						method: 'POST', 
						body: JSON.stringify({budgetVergroting, budgetAfdeling, gebruikerId, budgetId, response}),
						headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}
					})
					    .then(response => response.json())
					    .then(function(myJson) {
					    	//Close the modal
					    	modal.classList.remove('active');
							overlay.classList.remove('active');
							
							warning.innerHTML = '';
							
							//Show a toast that says the budget proposal is made
							var toastUp = document.getElementById("toastGoed");
							toastUp.className = "show";
							setTimeout(function(){ 
								toastUp.className = toastUp.className.replace("show", ""); 
							}, 3000);
					    })
		    	}
		    })
		} else {
			warning.innerHTML = "Vul alle velden correct in!";
			
			//Show error toast
			var toastUp = document.getElementById("toastFout");
			toastUp.className = "show";
			setTimeout(function(){ 
				toastUp.className = toastUp.className.replace("show", ""); 
			}, 3000);
		}
	})
}

function makeModal() {
	//Get all the information needed
	const modal = document.querySelector('.modal');
	const openModalButton = document.querySelector('.budget-button');
	const closeModalButton = document.querySelector('[data-close-button]');
	const overlay = document.getElementById('overlay')
	const budgetVergroting = document.querySelector('.budget-vergroting');
	
	//Make click functions for every button
	openModalButton.addEventListener('click', function(){
		if (($(window).width() < 960)) {
			$(".voorstel-div").css('overflow-y' , '');
		}
		
		budgetVergroting.value = '';
		modal.classList.add('active')
		overlay.classList.add('active')
	})

	//Make a function for if you click outside the modal
	overlay.addEventListener('click', function(){
		modal.classList.remove('active');
		overlay.classList.remove('active');
		
		if (($(window).width() < 960)) {
			$(".voorstel-div").css('overflow-y' , 'auto');
		}
	})

	//Make click functions for every button
	closeModalButton.addEventListener('click', function() {
		modal.classList.remove('active');
		overlay.classList.remove('active');
		
		if (($(window).width() < 960)) {
			$(".voorstel-div").css('overflow-y' , 'auto');
		}
	})
}

function loadGebruiker() {
	var gebruikerId = sessionStorage.getItem("id");
	
	//Load user information
	fetch('restservices/gebruiker/'+gebruikerId)
	.then(response => response.json())
	.then(function(gebruiker){
		gebruiker = gebruiker[0];
		var gebruikerId = document.querySelector(".gebruiker-id");		
		var gebruikerNaam = document.querySelector(".gebruiker-naam");
		
		gebruikerId.innerHTML = '';
		gebruikerNaam.innerHTML = '';
		
		gebruikerId.innerHTML += gebruiker.id;
		gebruikerNaam.innerHTML += gebruiker.naam;
	});
}
