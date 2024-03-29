function loginButtonClick() {
    login();
    
    //If you press enter after entering your password start click function
    var input = document.getElementById("wachtwoord");
    input.addEventListener("keyup", function(event) {
        if(event.which == 13) {
        	document.querySelector(".login-button").click();
            return false;
        }
    });
}

function login() {
	var inputGebruikersNaam = document.querySelector(".gebruikersnaam");
	inputGebruikersNaam.value = '';
	
    document.querySelector(".login-button").addEventListener("click", function () {
    	//Get the entered data
        const formData = new FormData(document.querySelector("form"));
        const data = new URLSearchParams(formData.entries());

        fetch('restservices/authenticatie', {method: 'POST', body: data, headers : {
            'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")
        }})
            .then(function (response) {
                if (response.ok) {
                    return response.json();
                }
                else {
                    document.querySelector(".warning").innerText = "Ongeldig gebruikersnaam / wachtwoord";
                    return;
                }
            })
            .then(function(response) {
            	console.log(response);
                sessionStorage.setItem("sessionToken", response.JWT);
                sessionStorage.setItem("id", response.id);
                sessionStorage.setItem("rol", response.rol);
                window.location.href = "/menu.html";
            })
            .catch(error => console.log(error));
    });
}