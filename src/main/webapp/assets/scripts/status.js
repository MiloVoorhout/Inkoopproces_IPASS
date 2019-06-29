function initPage() {
	const goBack = document.querySelector('.fa-arrow-alt-circle-left');
	
	goBack.addEventListener('click', function() {
		window.location.href = "/inkoop/menu.html";
	})
	
    loadStatus();
}

function loadStatus() {
	var statusBody = document.querySelector(".status-div");
	var userId = sessionStorage.getItem("id");
	
//	, {method : 'GET', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}}
	
	fetch('restservices/gekeurde_voorstellen/'+userId)
	.then(response => response.json())
	.then(function(statussen){
		statusBody.innerHTML = '';
		for(const status of statussen) {
			if(status.status === "Goed gekeurd") {
				statusCSS = "keuring-goed";
				statusHeader = '<i class="fas fa-times status-verwijderen"></i>';
			} else if (status.status === "Afgekeurd" || status.status === "Product verwijderd") {
				statusCSS = "keuring-afgekeurd";
				statusHeader = '<i class="fas fa-times status-verwijderen"></i>';
			} else {
				statusCSS = "keuring-afwachting";
				statusHeader = '';
			}

			statusBody.innerHTML += '<div class="status-block" statusId="' + status.id + '">' +
		        	'<div class="status-header">' +
		        		'<label>Product:</label>' +
		        		statusHeader +
		        	'</div>' +
		        	'<div class="product">' +
		        		'<label>' + status.product + '</label>' +
		        	'</div>' +
		        	'<div class="' + statusCSS + '">' +
						'<label>' + status.status + '</label>' +
		        	'</div>' +
		    	'</div>';
		}
		
		deleteStatus();
	})
}

function deleteStatus() {
	var statussen = document.querySelectorAll(".status-verwijderen");
	for(const status of statussen) {
		status.addEventListener("click", function(){
			statusId = this.parentNode.parentNode.getAttribute("statusId");
			
			fetch("restservices/gekeurde_voorstellen/delete/"+statusId, {method: 'DELETE'})
		    .then(function(response){
		    	console.log(response);
		    	
		    	loadStatus();
		    })
		});
	}
}