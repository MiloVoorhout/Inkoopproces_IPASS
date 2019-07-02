function initPage() {
	//Check if user is logged in
	if (window.sessionStorage.getItem("sessionToken") === null) {
		//If not send him to login page
		window.location.href = "/index.html";
		return;
	} else {
		const goBack = document.querySelector('.fa-arrow-alt-circle-left');
		
		//Give the go back button a event
		goBack.addEventListener('click', function() {
			window.location.href = "/menu.html";
		})
		
		loadBudgetVoorstellen();
	}
}

function loadBudgetVoorstellen() {
	var budgetBody = document.querySelector(".budget-div");
	budgetBody.innerHTML = '';
	
	//Fetch all budget proposals
	fetch('restservices/budget_voorstellen', {method: 'GET', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
	.then(response => response.json())
	.then(function(budgetProposals){
		//Check if there are any budget proposals
		if (budgetProposals.length === 0) {
			//If not at the following text
        	budgetBody.innerHTML +=	'<div>' +
										'<label>Er zijn geen budget voorstellen</label>' +
									'</div>';
		} else {
			//If there are any budget proposals loop through them
	        for (const budgetP of budgetProposals) {
	        	const increasementEuro = budgetP.increasement;
	        	
	        	//Add to the budget body
	        	budgetBody.innerHTML += '<div class="budget-hole" budgetId="' + budgetP.budget_id +'" budgetIncreasement="' + increasementEuro.toFixed(2) +'" gkProposalId="' + budgetP.gk_id +'">' +
		        	'<div class="budget-block">' +
		        		'<div>' +
		        			'<label>Werknemer:' + ' ' + budgetP.user_id + '</label>' +
		        		'</div>' +
		        		'<div>' +
		        			'<label>Afdeling:' + ' ' + budgetP.department + '</label>' +
		        		'</div>' +
		        		'<div>' +
							'<label>Vergroting:' + ' € ' + increasementEuro.toFixed(2) + '</label>' +
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
	var budgetProposalsApprovement = document.querySelectorAll(".fa-check");
	
	//Give an event to all buttons that aproves a proposal
	for(const bProposal of budgetProposalsApprovement) {
		bProposal.addEventListener("click", function(){
			//Get all the information we need
			gkVoorstelId = this.parentNode.parentNode.getAttribute("gkProposalId");
			updateStatus = "Goed gekeurd";
			
			type = "plus";
			budgetId = this.parentNode.parentNode.getAttribute("budgetId");
			budgetPrijs = this.parentNode.parentNode.getAttribute("budgetIncreasement");
			
			//First fetch the information to update the approved proposals table status to approved
			fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
			.then(response => response.json())
		    .then(function(response){
		    	//If the response is ok update the budget with the increasement
		    	if(response) {
		    		fetch("restservices/budget/update/budget_voorstel", {method: 'PUT', body: JSON.stringify({budgetId, budgetPrijs, type}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
					.then(response => response.json())
				    .then(function(response){
				    	//If the budget update gives a ok response we will delete the proposal
				    	if(response) {
				    		fetch("restservices/budget_voorstellen/delete/"+budgetId, {method: 'DELETE', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
				    		.then(function(response){
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
	
	//Give an event to all buttons the disapprove a proposal
	for(const bVoorstel of budgetVoorstellenVerwijderen) {
		bVoorstel.addEventListener("click", function(){
			//Get all the information
			gkVoorstelId = this.parentNode.parentNode.getAttribute("gkVoorstelId");
			updateStatus = "Goed gekeurd";
			budgetId = this.parentNode.parentNode.getAttribute("budgetId");
			
			//First fetch the information to update the approved proposals table status to disapproved
			fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
			.then(response => response.json())
		    .then(function(response){
		    	//If the budget update gives a ok response we will delete the proposal
		    	if(response) {
		    		fetch("restservices/budget_voorstellen/delete/"+budgetId, {method: 'DELETE', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
		    		.then(function(response){
		    			loadBudgetVoorstellen();
				    })
		    	}
		    })
		});
	}
}
