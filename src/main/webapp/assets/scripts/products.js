//Check if user is logged in 
if (window.sessionStorage.getItem("sessionToken") === null) {
	//If not send user back to login screen
	window.location.href = "/index.html";
	return null;
}

function initPage() {
	//Make an event for the go back button
	const goBack = document.querySelector('.fa-arrow-alt-circle-left');
	
	goBack.addEventListener('click', function() {
		window.location.href = "/menu.html";
	})
	
	loadProducts();
}

function loadProducts() {	
	//Make product table empty before adding products
	document.getElementById("productTabel").innerHTML = "";
	
	//Get all products
	fetch('restservices/product')
	.then(response => response.json())
	.then(function(products){
		var table = document.querySelector("#productTabel");
		
		//Loop through every product
		for(product of products){
			//Add product to the table
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
			cel4.innerHTML = '<i class="fas fa-pencil-alt hidden"></i>';
			cel5.innerHTML = '<i class="fas fa-trash-alt hidden"></i>';
		}
		
		//If window width is bigger than 952 add toggle class hidden to every products
		if (($(window).width() > 952)) {
			$(".rowData").each(function(){
				$(this).hover(function(){
					$(this).find(".fa-pencil-alt").toggleClass('hidden');
					$(this).find(".fa-trash-alt").toggleClass('hidden');
				})
			})
		}
		
		//Start delete en edit button
		deleteButton();
		editButton();
	})
}

function deleteButton() {
	//Make an event for every delete button
	var selectRows = document.querySelectorAll(".fa-trash-alt");
	for(const product of selectRows) {
		product.addEventListener("click", function(){
			var id = this.parentNode.parentNode.getAttribute("productId");
			
			//Get every purchase proposal with the deleted product id
			fetch('restservices/aankoop_voorstellen/products/'+id, {method : 'GET', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
			.then(response => response.json())
			.then(function(aankoopVoorstellen){
				//Check if there are purchase proposals with that product id 
				if(aankoopVoorstellen.length === 0) {
					//If there aren't any delete the product
					fetch("restservices/product/delete/"+id, {method: 'DELETE', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
				    .then(function(response){
				    	if(response) {
				    		//After deleting reload products
				    		loadProducts();
				    		
				    		//Show toast that delete is complete
					    	var toastUp = document.getElementById("toastDelete");
							toastUp.className = "show";
							setTimeout(function(){ 
								toastUp.className = toastUp.className.replace("show", ""); 
							}, 3000);
				    	} else {
				    		//If response is not ok show toast error
				    		var toastUp = document.getElementById("toastFout");
							toastUp.className = "show";
							setTimeout(function(){ 
								toastUp.className = toastUp.className.replace("show", ""); 
							}, 3000);
				    	}
				    })
				} else if (aankoopVoorstellen.length > 0) {
					//If there are purchase proposals
					for(voorstel of aankoopVoorstellen){
						//Get all the information needed
						var gkVoorstelId = voorstel.gk_id;
						var updateStatus = "Product verwijderd";
						var aankoopVoorstelId = voorstel.id;
	
						//Update the status of approved proposals that product is deleted
						fetch("restservices/gekeurde_voorstellen/update/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateStatus}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
						.then(response => response.json())
					    .then(function(response){
					    	if(response) {
					    		//Delete purchase proposals with product id x
							    fetch("restservices/aankoop_voorstellen/delete/"+aankoopVoorstelId, {method: 'DELETE', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
							    .then(function(response){
							    	//Delete the product
									fetch("restservices/product/delete/"+id, {method: 'DELETE', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
								    .then(function(response){
								    	if(response) {
								    		//If product deleted reload products
								    		loadProducts();

								    		//Show product deleted toast
									    	var toastUp = document.getElementById("toastDelete");
											toastUp.className = "show";
											setTimeout(function(){ 
												toastUp.className = toastUp.className.replace("show", ""); 
											}, 3000);
								    	} else {
								    		//If response not ok show error toast
								    		var toastUp = document.getElementById("toastFout");
											toastUp.className = "show";
											setTimeout(function(){ 
												toastUp.className = toastUp.className.replace("show", ""); 
											}, 3000);
								    	}
								    })
							    })
					    	}
					    })
					}
					
				} else {
					//If purchase proposals is nothing show error toast
					var toastUp = document.getElementById("toastFout");
					toastUp.className = "show";
					setTimeout(function(){ 
						toastUp.className = toastUp.className.replace("show", ""); 
					}, 3000);
				}
			})
		});
	}
}

function editButton() {
	//Make an event for every edit button
	var selectRows = document.querySelectorAll(".fa-pencil-alt");
	for(const product of selectRows) {
		product.addEventListener("click", function(){
			//Get information
			var id = this.parentNode.parentNode.getAttribute("productId");
			var tableRow = this.parentNode.parentNode.childNodes;
			
			//Make input fields of the spefic product row
			tableRow[0].innerHTML = '<input value="' + tableRow[0].innerText + '" id="productNaam" originalName="' + tableRow[0].innerText +'">';
			tableRow[1].innerHTML = '<input type="number" step="0.01" value="' + parseFloat(tableRow[1].innerText) + '" id="productPrijs" originalPrice="' + tableRow[1].innerText + '">';
			tableRow[2].innerHTML = '<input value="' + tableRow[2].innerText + '" id="productCategorie" originalCategorie="' + tableRow[2].innerText + '">';
			tableRow[3].innerHTML = "<i class='fas fa-check'></i>";
			
			//Make a event of the check button
			var editSave = document.querySelector(".fa-check");
			editSave.addEventListener("click", function(){
				//Get old information
				var editRow = this.parentNode.parentNode.childNodes;
				var name = editRow[0].childNodes[0].value;
				var price = editRow[1].childNodes[0].value;
				var categorie = editRow[2].childNodes[0].value;
				
				//Check if input fields are answered
				if(name.length === 0) {
					name = editRow[0].childNodes[0].getAttribute("originalName");
				}
				
				if(price.length === 0) {
					price = editRow[1].childNodes[0].getAttribute("originalPrice");
				}
				
				if(categorie.length === 0) {
					categorie = editRow[2].childNodes[0].getAttribute("originalCategorie");
				}
				
				//Get every purchase proposals with product id x
				fetch('restservices/aankoop_voorstellen/products/'+id, {method : 'GET', headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
				.then(response => response.json())
				.then(function(aankoopVoorstellen){
					//Check purchase proposals length
					//If length is 0 only edit product
					if(aankoopVoorstellen.length === 0) {
						fetch("restservices/product/update/", {method: 'PUT', body: JSON.stringify({id, name, price, categorie}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
						.then(response => response.json())
					    .then(function(response){
					    	if(response) {
					    		//If response is ok reload product
					    		loadProducts();
					    		
					    		//Show product updated toast
					    		var toastUp = document.getElementById("toastUpdate");
								toastUp.className = "show";
								setTimeout(function(){ 
									toastUp.className = toastUp.className.replace("show", ""); 
								}, 3000);
					    	} else {
					    		//If response not ok show error toast
					    		var toastUp = document.getElementById("toastFout");
								toastUp.className = "show";
								setTimeout(function(){ 
									toastUp.className = toastUp.className.replace("show", ""); 
								}, 3000);
					    	}
						})
					}
					else if (aankoopVoorstellen.length > 0) {
						//If there are purchase proposals
						for(voorstel of aankoopVoorstellen) {
							//Get all the information needed
							var gkVoorstelId = voorstel.gk_id;
							var aankoopVoorstelId = voorstel.id;
							updateName = voorstel.aantal + ' ' + name
							
							//Update the product name of approved proposals
							fetch("restservices/gekeurde_voorstellen/update_product/", {method: 'PUT', body: JSON.stringify({gkVoorstelId, updateName}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
							.then(response => response.json())
						    .then(function(response){
						    	if(response) {
							    	//Update the product
						    		fetch("restservices/product/update/", {method: 'PUT', body: JSON.stringify({id, name, price, categorie}), headers : {'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")}})
									.then(response => response.json())
								    .then(function(response){
								    	if(response) {
								    		//If updated reload products
								    		loadProducts();

								    		//Show product updated toast
								    		var toastUp = document.getElementById("toastUpdate");
											toastUp.className = "show";
											setTimeout(function(){ 
												toastUp.className = toastUp.className.replace("show", ""); 
											}, 3000);
								    	} else {
								    		//Show error toast
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
						//Show error toast
						var toastUp = document.getElementById("toastFout");
						toastUp.className = "show";
						setTimeout(function(){ 
							toastUp.className = toastUp.className.replace("show", ""); 
						}, 3000);
					}
				})
			});
		});
	}
}

function tableFilter() {
	//Get information after every keyup
	var productInput = document.querySelector(".productInput");
	var inputToUpperCase = productInput.value.toUpperCase();
	var productTable = document.getElementById("productTabel");
	var tr = productTable.getElementsByTagName("tr");

	//Go through every product an check if any letter matches
	for (i = 0; i < tr.length; i++) {
		var td = tr[i].getElementsByTagName("td")[0];
		if (td) {
			var tdValue = td.innerText;
			if (tdValue.toUpperCase().indexOf(inputToUpperCase) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}