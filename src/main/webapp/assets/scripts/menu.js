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
										'<a href="product_keuren.html"><button class="menu-button" name="keuren" value="Keuren" id="keuren">Keuren</button></a>' +
    								'</div>';
		
    	menuButtonBody.innerHTML += '<div class="button-div">' +
    									'<a href="products.html"><button class="menu-button" name="product" value="Producten" id="producten"">Producten</button></a>' +
    								'</div>';
    	
    	$(".menu-content").css("margin", "6vh auto");

    	
	} else if (role === "Budget manager") {
		menuButtonBody.innerHTML += '<a href="budget_keuren.html"><button class="menu-button" name="product" value="Producten" id="producten"">Producten</button></a>';
		
		$(".menu-content").css("margin", "12vh auto");
	}
}

function uitlogButton() {
	const uitloggen = document.querySelector(".uitlog-button");
	uitloggen.addEventListener('click', function() {
		sessionStorage.clear();		
		window.location.href = "/index.html";
	})
}