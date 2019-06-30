function initPage() {
	const goToAankoop = document.querySelector('#aankoop');
	const goToStatus = document.querySelector('#status');
	
	goToAankoop.addEventListener('click', function() {
		console.log("aankoop");
		window.location.href = "/aankoop.html";
	})
	
	goToStatus.addEventListener('click', function() {
		window.location.href = "/status.html";
	})
	
	keurenButton();
	uitlogButton();
	
}

function keurenButton() {
	const menuButtonBody = document.querySelector('.menu-buttons');
	
	var role = sessionStorage.getItem("rol");
	if (role === "Voorstel manager") {
		menuButtonBody.innerHTML += '<div class="button-div">' +
    								'<input type="submit" class="menu-button" name="keuren" value="Keuren" id="keuren">';
    								'</div>';
    								
    	menuButtonBody.innerHTML += '<div class="button-div">' +
    								'<input type="submit" class="menu-button" name="product" value="Producten" id="producten">';
    								'</div>';
    	
    	$(".menu-content").css("margin", "6vh auto");
    	const goToKeuren = document.querySelector('#keuren');    	
    	goToKeuren.addEventListener('click', function() {
    		window.location.href = "/product_keuren.html";
    	})   
    	
    	const goToProducten = document.querySelector('#producten');   
    	goToProducten.addEventListener('click', function() {
    		window.location.href = "/products.html";
    	})   
    	
	} else if (role === "Budget manager") {
		menuButtonBody.innerHTML += '<div class="button-div">' +
									'<input type="submit" class="menu-button" name="keuren" value="Keuren" id="keuren">';
									'</div>';
		
		$(".menu-content").css("margin", "12vh auto");
		const goToKeuren = document.querySelector('#keuren');							
		goToKeuren.addEventListener('click', function() {
			window.location.href = "/budget_keuren.html";
		})
	}
}

function uitlogButton() {
	const uitloggen = document.querySelector(".uitlog-button");
	uitloggen.addEventListener('click', function() {
		sessionStorage.clear();		
		window.location.href = "/index.html";
	})
}