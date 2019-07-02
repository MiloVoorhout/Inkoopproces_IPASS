//Check if user is logged in
if (window.sessionStorage.getItem("sessionToken") === null) {
	//If user isn't logged in then send to login page
	window.location.href = "/index.html";
	return null;
}

function initPage() {
	const goBack = document.querySelector('.fa-arrow-alt-circle-left');
	
	//Give the go back to menu button a event
	goBack.addEventListener('click', function() {
		window.location.href = "/menu.html";
	})
	
    loadStatus();
}

function loadStatus() {
	var statusBody = document.querySelector(".status-div");
	var userId = sessionStorage.getItem("id");
		
	fetch('restservices/gekeurde_voorstellen/'+userId)
	.then(response => response.json())
	.then(function(statussen){
		statusBody.innerHTML = '';
		
		//Check if the user has any statussen or not
		if (statussen.length === 0) {
			statusBody.innerHTML +=	'<div>' +
										'<label>U heeft geen voorstellen ingediend</label>' +
									'</div>';
		} else {
			for(const status of statussen) {
				//Check what the status of a status is to determine the color of the status text
				if(status.status === "Goed gekeurd") {
					//Green color
					statusCSS = "keuring-goed";
					//Option to delete status
					statusHeader = '<i class="fas fa-times status-verwijderen"></i>';
				} else if (status.status === "Afgekeurd" || status.status === "Product verwijderd") {
					//Red color
					statusCSS = "keuring-afgekeurd";
					//Option to delete status
					statusHeader = '<i class="fas fa-times status-verwijderen"></i>';
				} else {
					//Yellow color
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
		}
	})
}

function deleteStatus() {
	var statussen = document.querySelectorAll(".status-verwijderen");
	for(const status of statussen) {
		status.addEventListener("click", function(){
			//This gets the id that we added in the statusBody in the status-block div
			statusId = this.parentNode.parentNode.getAttribute("statusId");
			
			fetch("restservices/gekeurde_voorstellen/delete/"+statusId, {method: 'DELETE'})
		    .then(function(response){		    	
		    	loadStatus();
		    })
		});
	}
}