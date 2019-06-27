function initPage() {
	document.querySelector('#voorstellen').value = 'product';
   
	const goBack = document.querySelector('.fa-arrow-alt-circle-left');
   
	goBack.addEventListener('click', function() {
		window.location.href = "/inkoop/menu.html";
	})
   
	loadProductVoorstellen(); 
}

function loadProductVoorstellen() {
	var productVoorstelBody = document.querySelector("#voorstel");
	var budget = document.querySelector(".budget-div");
	
	
	budget.innerHTML = '';
	productVoorstelBody.innerHTML = '';
	
	fetch('restservices/product_voorstel')
	.then(response => response.json())
	.then(function(pvoorstellen){
		for(const voorstel of pvoorstellen) {
			var voorstelDiv = document.querySelector(".voorstel-div");
			voorstelDiv.style.width = "100%";
			
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
		
		productVoorstelGoedKeuren();
		productVoorstelAfKeuren()
	})
}

function productVoorstelGoedKeuren() {
	var productVoorstelToevoegen = document.querySelectorAll(".fa-check");
	
	for(const pVoorstel of productVoorstelToevoegen) {
		pVoorstel.addEventListener("click", function(){
			productVoorstelId = this.parentNode.parentNode.getAttribute("productVoorstelId");
			gkVoorstelId = this.parentNode.parentNode.getAttribute("gkVoorstelId");
			updateStatus = "Goed gekeurd";
			
			productNaam = this.parentNode.parentNode.getAttribute("productNaam");
			productPrijs = this.parentNode.parentNode.getAttribute("productPrijs");
			productCategorie = this.parentNode.parentNode.getAttribute("productCategorie");
			
			fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus})})
			.then(response => response.json())
		    .then(function(response){
		    	if(response) {
		    		fetch("restservices/product/save/", {
		    			method: 'POST',
		    			body: JSON.stringify({productNaam, productPrijs, productCategorie})
		    		})
		    		.then(response => response.json())
				    .then(function(response){
				    	if(response) {
							fetch("restservices/product_voorstel/delete/"+productVoorstelId, {method: 'DELETE'})
						    .then(function(response){						    	
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
	var productVoorstelVerwijderen = document.querySelectorAll(".fa-times");
	
	for(const pVoorstel of productVoorstelVerwijderen) {
		pVoorstel.addEventListener("click", function(){
			productVoorstelId = this.parentNode.parentNode.getAttribute("productVoorstelId");
			gkVoorstelId = this.parentNode.parentNode.getAttribute("gkVoorstelId");
			updateStatus = "Afgekeurd";
			
			fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus})})
			.then(response => response.json())
		    .then(function(response){
		    	if(response) {
		    		fetch("restservices/product_voorstel/delete/"+productVoorstelId, {method: 'DELETE'})
		    		.then(function(response){						    	
		    			loadProductVoorstellen();
				    })
		    	}
		    })
		});
	}
}

function loadAankoopVoorstellen() {
	var budget = document.querySelector(".budget-div");
	budget.innerHTML = '';
	
	var aankoopVoorstelBody = document.querySelector("#voorstel");
	aankoopVoorstelBody.innerHTML = '';
	
	var userId = sessionStorage.getItem("id");
	
	fetch('restservices/aankoop_voorstellen/'+userId)
	.then(response => response.json())
	.then(function(aankoopVoorstellen){
		for(const aVoorstel of aankoopVoorstellen) {
			var voorstelDiv = document.querySelector(".voorstel-div");
			voorstelDiv.style.width = "80%";
			
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
			 				'<label>Reden: ' + aVoorstel.reden + '</label>' +
			 			'</div>' +
			 		'</div>' +
			
			 		'<div class="voorstel-buttons">' +
			 			'<i class="fas fa-check"></i>' +
			 			'<i class="fas fa-times"></i>' +
			 		'</div>' + 
			 	'</div>';
		}
		
		aankoopVoorstelGoedKeuren();
		aankoopVoorstelAfKeuren();
		loadBudget();
	})
}

function aankoopVoorstelGoedKeuren() {
	var aankoopVoorstellenGoedkeuren = document.querySelectorAll(".fa-check");
	
	for(const aVoorstel of aankoopVoorstellenGoedkeuren) {
		aVoorstel.addEventListener("click", function(){
			aankoopVoorstelId = this.parentNode.parentNode.getAttribute("aankoopVoorstelId");
			gkVoorstelId = this.parentNode.parentNode.getAttribute("gkVoorstelId");
			updateStatus = "Goed gekeurd";
			
			type = "min";
			budgetAfdeling = this.parentNode.parentNode.getAttribute("afdelingGebruiker");
			budgetPrijs = this.parentNode.parentNode.getAttribute("totaalPrijs");
			
			fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus})})
			.then(response => response.json())
		    .then(function(response){
		    	if(response) {
		    		fetch("restservices/budget/update/aankoop_voorstel", {method: 'PUT', body: JSON.stringify({budgetAfdeling, budgetPrijs, type})})
					.then(response => response.json())
				    .then(function(response){
				    	if(response) {
				    		fetch("restservices/aankoop_voorstellen/delete/"+aankoopVoorstelId, {method: 'DELETE'})
				    		.then(function(response){
				    			console.log(response);
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
	var productVoorstellenVerwijderen = document.querySelectorAll(".fa-times");
	
	for(const aVoorstel of productVoorstellenVerwijderen) {
		aVoorstel.addEventListener("click", function(){
			aankoopVoorstelId = this.parentNode.parentNode.getAttribute("aankoopVoorstelId");
			gkVoorstelId = this.parentNode.parentNode.getAttribute("gkVoorstelId");
			updateStatus = "Afgekeurd";
			
			fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus})})
			.then(response => response.json())
		    .then(function(response){
		    	if(response) {
		    		fetch("restservices/aankoop_voorstellen/delete/"+aankoopVoorstelId, {method: 'DELETE'})
		    		.then(function(response){
		    			console.log(response);
		    			loadAankoopVoorstellen();
				    })
		    	}
		    })
		});
	}
}


function loadBudget() {
	var budgetAfdeling = document.querySelector(".budget-afdelingen");
	var budgetDiv = document.querySelector(".budget-div");
	
	budgetDiv.innerHTML += '<div>Budget:</div>' +
							 '<div class="all-budgets"></div>' +
							 '<input type="submit" class="budget-button" name="budget-voorstel" value="Budget +">';
	
	var budgetLijst = document.querySelector(".all-budgets");
	
	fetch('restservices/budget')
	.then(response => response.json())
	.then(function(budgets){
		for(budget of budgets) {
			budgetLijst.innerHTML += '<div class="one-budget">' + budget.budget + '</div>';
			
			budgetAfdeling.innerHTML +=  '<option value="' + budget.id + '">' + budget.afdeling + '</option>';
		}
		
		budgetVoorstel();
		makeModal();
		loadGebruiker();
	});	
}

function select(selectOption) {
	if(selectOption.value == "product") {
		console.log("product");
		loadProductVoorstellen();
	} else {
		console.log("aankoop");
		loadAankoopVoorstellen();
   }
}

function budgetVoorstel() {
	var voorstelButton = document.querySelector(".product-insturen");
	
	voorstelButton.addEventListener("click", function () {
		
		var budgetAfdelingSelector = (document.querySelector(".budget-afdelingen"));
		var budgetId = budgetAfdelingSelector.options[budgetAfdelingSelector.selectedIndex].value;
		var budgetAfdeling = budgetAfdelingSelector.options[budgetAfdelingSelector.selectedIndex].text;
		
		var budgetVergroting = (document.querySelector(".budget-vergroting")).value;
		var gebruikerId = sessionStorage.getItem("id");;

		var productNaam = "Afdeling: " + budgetAfdeling + ' - ' + '€ ' + budgetVergroting;
		
		const warning = document.querySelector(".warningModal");
		
		if(budgetVergroting.length !== 0) {
			fetch("/inkoop/restservices/gekeurde_voorstellen/save", { 
				method: 'POST', 
				body: JSON.stringify({productNaam, gebruikerId})})
		    .then(response => response.json())
		    .then(function(response) {	
		    	if (response !== -1) {
			    	fetch("/inkoop/restservices/budget_voorstellen/save", { 
						method: 'POST', 
						body: JSON.stringify({budgetVergroting, budgetAfdeling, gebruikerId, budgetId, response})
					})
					    .then(response => response.json())
					    .then(function(myJson) {
					    	console.log(myJson);
					    	modal.classList.remove('active');
							overlay.classList.remove('active');
							
							warning.innerHTML = '';
							
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
			
			var toastUp = document.getElementById("toastFout");
			toastUp.className = "show";
			setTimeout(function(){ 
				toastUp.className = toastUp.className.replace("show", ""); 
			}, 3000);
		}
	})
}

function makeModal() {
	const modal = document.querySelector('.modal');
	const openModalButton = document.querySelector('.budget-button');
	const closeModalButton = document.querySelector('[data-close-button]');
	const overlay = document.getElementById('overlay')
	const budgetVergroting = document.querySelector('.budget-vergroting');
	
	openModalButton.addEventListener('click', function(){
		budgetVergroting.value = '';
		modal.classList.add('active')
		overlay.classList.add('active')
	})

	overlay.addEventListener('click', function(){
		modal.classList.remove('active');
		overlay.classList.remove('active');
	})

	closeModalButton.addEventListener('click', function() {
		modal.classList.remove('active');
		overlay.classList.remove('active');
	})
}

function loadGebruiker() {
	var gebruikerId = sessionStorage.getItem("id");
	
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
