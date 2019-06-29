function initPage() {
	const goBack = document.querySelector('.fa-arrow-alt-circle-left');
	
	goBack.addEventListener('click', function() {
		window.location.href = "/inkoop/menu.html";
	})
	
	loadProducts();
}

function loadProducts() {	
	document.getElementById("productTabel").innerHTML = "";
	fetch('restservices/product')
	.then(response => response.json())
	.then(function(products){
		var table = document.querySelector("#productTabel");
		
		for(product of products){
			var newRow = table.insertRow(-1);
			
			newRow.setAttribute("class", "rowData");
			newRow.setAttribute("productId", product.id);
			
			var cel1 = newRow.insertCell(0);
			var cel2 = newRow.insertCell(1);
			var cel3 = newRow.insertCell(2);
			var cel4 = newRow.insertCell(3);
			var cel5 = newRow.insertCell(4);
			
			cel1.innerHTML = product.naam;
			cel2.innerHTML = (product.prijs).toFixed(2);
			cel3.innerHTML = product.categorie;
			cel4.innerHTML = '<i class="fas fa-pencil-alt"></i>';
			cel5.innerHTML = '<i class="fas fa-trash-alt"></i>';
		}
		
		deleteButton();
		editButton();
	})
}

function deleteButton() {
	var selectRows = document.querySelectorAll(".fa-trash-alt");
	for(const product of selectRows) {
		product.addEventListener("click", function(){
			var id = this.parentNode.parentNode.getAttribute("productId");
			
			fetch('restservices/aankoop_voorstellen/products/'+id)
			.then(response => response.json())
			.then(function(aankoopVoorstellen){
				for(voorstel of aankoopVoorstellen){
					var gkVoorstelId = voorstel.gk_id;
					var updateStatus = "Product verwijderd";
					var aankoopVoorstelId = voorstel.id;
					
					fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus})})
					.then(response => response.json())
				    .then(function(response){
				    	if(response) {
						    fetch("restservices/aankoop_voorstellen/delete/"+aankoopVoorstelId, {method: 'DELETE'})
						    .then(function(response){
						    	if(response) {
								    fetch("restservices/product/delete/"+id, {method: 'DELETE'})
								    .then(function(response){
								    	if(response) {
									    	console.log(response);
									    	loadProducts();
									    	var toastUp = document.getElementById("toastDelete");
											toastUp.className = "show";
											setTimeout(function(){ 
												toastUp.className = toastUp.className.replace("show", ""); 
											}, 3000);
								    	} else {
								    		var toastUp = document.getElementById("toastFout");
											toastUp.className = "show";
											setTimeout(function(){ 
												toastUp.className = toastUp.className.replace("show", ""); 
											}, 3000);
								    	}
								    })
						    	}
						    })
				    	}
				    })
					
				}	
			})
		});
	}
}

function editButton() {
	var selectRows = document.querySelectorAll(".fa-pencil-alt");
	for(const product of selectRows) {
		product.addEventListener("click", function(){
			var id = this.parentNode.parentNode.getAttribute("productId");
			var tableRow = this.parentNode.parentNode.childNodes;
			
			tableRow[0].innerHTML = '<input value="' + tableRow[0].innerText + '" id="productNaam" originalName="' + tableRow[0].innerText +'">'
			tableRow[1].innerHTML = '<input type="number" step="0.01" value="€ ' + parseFloat(tableRow[1].innerText) + '" id="productPrijs" originalPrice="' + tableRow[1].innerText + '">'
			tableRow[2].innerHTML = '<input value="' + tableRow[2].innerText + '" id="productCategorie" originalCategorie="' + tableRow[2].innerText + '">'
			tableRow[3].innerHTML = "<i class='fas fa-check'></i>"
			
			var editSave = document.querySelector(".fa-check");
			editSave.addEventListener("click", function(){
				var editRow = this.parentNode.parentNode.childNodes;
				var name = editRow[0].childNodes[0].value;
				var price = editRow[1].childNodes[0].value;
				var categorie = editRow[2].childNodes[0].value;
				
				if(name.length === 0) {
					name = editRow[0].childNodes[0].getAttribute("originalName");
				}
				
				if(price.length === 0) {
					price = editRow[1].childNodes[0].getAttribute("originalPrice");
				}
				
				if(categorie.length === 0) {
					categorie = editRow[2].childNodes[0].getAttribute("originalCategorie");
				}
				
				fetch('restservices/aankoop_voorstellen/products/'+id)
				.then(response => response.json())
				.then(function(aankoopVoorstellen){
					if(aankoopVoorstellen.length === 0) {
						fetch("restservices/product/update/", {method: 'PUT', body: JSON.stringify({id, name, price, categorie})})
						.then(response => response.json())
					    .then(function(response){
					    	if(response) {
								console.log(response);
								loadProducts();
					    		var toastUp = document.getElementById("toastUpdate");
								toastUp.className = "show";
								setTimeout(function(){ 
									toastUp.className = toastUp.className.replace("show", ""); 
								}, 3000);
					    	} else {
					    		var toastUp = document.getElementById("toastFout");
								toastUp.className = "show";
								setTimeout(function(){ 
									toastUp.className = toastUp.className.replace("show", ""); 
								}, 3000);
					    	}
						})
					}
					else if (aankoopVoorstellen.length > 0) {
						for(voorstel of aankoopVoorstellen) {
							var gkVoorstelId = voorstel.gk_id;
							var aankoopVoorstelId = voorstel.id;
							updateName = voorstel.aantal + ' ' + name
							
							fetch("restservices/gekeurde_voorstellen/update_product/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateName})})
							.then(response => response.json())
						    .then(function(response){
						    	if(response) {
						    		fetch("restservices/product/update/", {method: 'PUT', body: JSON.stringify({id, name, price, categorie})})
									.then(response => response.json())
								    .then(function(response){
								    	if(response) {
											console.log(response);
											loadProducts();
								    		var toastUp = document.getElementById("toastUpdate");
											toastUp.className = "show";
											setTimeout(function(){ 
												toastUp.className = toastUp.className.replace("show", ""); 
											}, 3000);
								    	} else {
								    		var toastUp = document.getElementById("toastFout");
											toastUp.className = "show";
											setTimeout(function(){ 
												toastUp.className = toastUp.className.replace("show", ""); 
											}, 3000);
								    	}
									})
								}
						    })
						}	
					} else {
						var toastUp = document.getElementById("toastFout");
						toastUp.className = "show";
						setTimeout(function(){ 
							toastUp.className = toastUp.className.replace("show", ""); 
						}, 3000);
						loadProducts();
					}
				})
			});
		});
	}
}