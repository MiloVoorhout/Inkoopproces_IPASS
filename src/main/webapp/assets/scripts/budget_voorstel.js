function initPage() {
	if (window.sessionStorage.getItem("sessionToken") === null) {
		window.location.href = "/index.html";
	} else {
		const goBack = document.querySelector('.fa-arrow-alt-circle-left');
		
		goBack.addEventListener('click', function() {
			window.location.href = "/menu.html";
		})
		
		loadBudgetVoorstellen();
	}
}

function loadBudgetVoorstellen() {
	var budgetBody = document.querySelector(".budget-div");
	budgetBody.innerHTML = '';
	
	fetch('restservices/budget_voorstellen', {method: 'GET', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
	.then(response => response.json())
	.then(function(budgetVoorstellen){
		if (budgetVoorstellen.length === 0) {
        	budgetBody.innerHTML +=	'<div>' +
										'<label>Er zijn geen budget voorstellen</label>' +
									'</div>';
		} else {
	        for (const budgetVoorstel of budgetVoorstellen) {
	        	const vergrotingEuro = budgetVoorstel.vergroting;
	        	budgetBody.innerHTML += '<div class="budget-hole" budgetId="' + budgetVoorstel.budget_id +'" budgetVergroting="' + vergrotingEuro.toFixed(2) +'" gkVoorstelId="' + budgetVoorstel.gk_id +'">' +
		        	'<div class="budget-block">' +
		        		'<div>' +
		        			'<label>Werknemer:' + ' ' + budgetVoorstel.gebruikers_naam + '</label>' +
		        		'</div>' +
		        		'<div>' +
		        			'<label>Afdeling:' + ' ' + budgetVoorstel.afdeling + '</label>' +
		        		'</div>' +
		        		'<div>' +
							'<label>Vergroting:' + ' € ' + vergrotingEuro.toFixed(2) + '</label>' +
		        		'</div>' +
		        	'</div>' +
	    	
		    		'<div class="budget-buttons" nummer="123">' +
						'<i class="fas fa-check"></i>' +
						'<i class="fas fa-times"></i>' +
		    		'</div>' +
	        	'</div>';
	        }
	        
	        budgetVoorstelGoedkeuren();
	        budgetVoorstelAfkeuren();
		}
	})
}

function budgetVoorstelGoedkeuren() {
	var budgetVoorstellenGoedkeuren = document.querySelectorAll(".fa-check");
	
	for(const bVoorstel of budgetVoorstellenGoedkeuren) {
		bVoorstel.addEventListener("click", function(){
			gkVoorstelId = this.parentNode.parentNode.getAttribute("gkVoorstelId");
			updateStatus = "Goed gekeurd";
			
			type = "plus";
			budgetId = this.parentNode.parentNode.getAttribute("budgetId");
			budgetPrijs = this.parentNode.parentNode.getAttribute("budgetVergroting");
			
			console.log(budgetId);
			
			fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
			.then(response => response.json())
		    .then(function(response){
		    	if(response) {
		    		fetch("restservices/budget/update/budget_voorstel", {method: 'PUT', body: JSON.stringify({budgetId, budgetPrijs, type}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
					.then(response => response.json())
				    .then(function(response){
				    	if(response) {
				    		fetch("restservices/budget_voorstellen/delete/"+budgetId, {method: 'DELETE', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
				    		.then(function(response){
				    			console.log(response);
				    			loadBudgetVoorstellen();
						    })
				    	}
				    })
		    	}
		    })
		});
	}
}

function budgetVoorstelAfkeuren() {
	var budgetVoorstellenVerwijderen = document.querySelectorAll(".fa-times");
	
	for(const bVoorstel of budgetVoorstellenVerwijderen) {
		bVoorstel.addEventListener("click", function(){
			gkVoorstelId = this.parentNode.parentNode.getAttribute("gkVoorstelId");
			updateStatus = "Goed gekeurd";
			budgetId = this.parentNode.parentNode.getAttribute("budgetId");
			
			fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
			.then(response => response.json())
		    .then(function(response){
		    	if(response) {
		    		fetch("restservices/budget_voorstellen/delete/"+budgetId, {method: 'DELETE', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
		    		.then(function(response){
		    			console.log(response);
		    			loadBudgetVoorstellen();
				    })
		    	}
		    })
		});
	}
}
