package inkoop.gebruiker;

public class Gebruiker {
	private int id;
	private String gebruikersnaam;
	private String wachtwoord;
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private int afdeling;
	private String rol;
	
	public Gebruiker (String gebruikersnaam, String wachtwoord, String voornaam, String tussenvoegsel, String achternaam, int afdeling, String rol ) {
		this.gebruikersnaam = gebruikersnaam;
		this.wachtwoord = wachtwoord;
		this.voornaam = voornaam;
		this.tussenvoegsel = tussenvoegsel;
		this.achternaam = achternaam;
		this.afdeling = afdeling;
		this.rol = rol;
	}
	
	public Gebruiker (String gebruikersnaam, String wachtwoord, String voornaam, String achternaam, int afdeling, String rol ) {
		this.gebruikersnaam = gebruikersnaam;
		this.wachtwoord = wachtwoord;
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.afdeling = afdeling;
		this.rol = rol;
	}
	
	public Gebruiker (int id, String voornaam, String achternaam, int afdeling) {
		this.id = id;
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.afdeling = afdeling;
	}
	
	public Gebruiker (int id, String rol ) {
		this.id = id;
		this.rol = rol;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getGebruikersnaam() {
		return gebruikersnaam;
	}
	public void setGebruikersnaam(String gebruikersnaam) {
		this.gebruikersnaam = gebruikersnaam;
	}

	public String getWachtwoord() {
		return wachtwoord;
	}
	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}

	public String getVoornaam() {
		return voornaam;
	}
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getTussenvoegsel() {
		return tussenvoegsel;
	}
	public void setTussenvoegsel(String tussenvoegsel) {
		this.tussenvoegsel = tussenvoegsel;
	}

	public String getAchternaam() {
		return achternaam;
	}
	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public int getAfdeling() {
		return afdeling;
	}
	public void setAfdeling(int afdeling) {
		this.afdeling = afdeling;
	}

	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	
}
