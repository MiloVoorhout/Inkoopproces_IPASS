//Check if user is logged in
if (window.sessionStorage.getItem("sessionToken") === null) {
	//If not send him to login page
	window.location.href = "/index.html";
	return;
}

function initPage() {
	const goToAankoop = document.querySelector('#aankoop');
	const goToStatus = document.querySelector('#status');
		
	//Make a event for the static buttons
	goToAankoop.addEventListener('click', function() {
		window.location.href = "/aankoop.html";
	})
		
	goToStatus.addEventListener('click', function() {
		window.location.href = "/status.html";
	})
		
	keurenButton();
	uitlogButton();
}

//This function is for special users that have the right to approve things
function keurenButton() {
	const menuButtonBody = document.querySelector('.menu-buttons');
	
	var role = sessionStorage.getItem("rol");
	//If user is a Voorstel manager add 2 buttons for approving products and seeing all the products
	if (role === "Voorstel manager") {
		menuButtonBody.innerHTML += '<div class="button-div">' +
										'<a href="product_keuren.html"><button class="menu-button" name="keuren" value="Keuren" id="keuren">Keuren</button></a>' +
    								'</div>';
		
    	menuButtonBody.innerHTML += '<div class="button-div">' +
    									'<a href="products.html"><button class="menu-button" name="product" value="Producten" id="producten"">Producten</button></a>' +
    								'</div>';
    	
    	$(".menu-content").css("margin", "6vh auto");

    //If user is a Budget manager only add 1 button that leads to budget proposals
	} else if (role === "Budget manager") {
		menuButtonBody.innerHTML += '<a href="budget_keuren.html"><button class="menu-button" name="keuren" value="Keuren" id="keuren"">Keuren</button></a>';
		
		$(".menu-content").css("margin", "12vh auto");
	}
}

function uitlogButton() {
	//For users that want to logout there is this button
	const uitloggen = document.querySelector(".uitlog-button");
	uitloggen.addEventListener('click', function() {
		sessionStorage.clear();		
		window.location.href = "/index.html";
	})
}