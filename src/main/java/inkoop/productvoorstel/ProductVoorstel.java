package inkoop.productvoorstel;

public class ProductVoorstel {
	private int id;
	private String naam;
	private double prijs;
	private String categorie;
	private int gebruikers_id;
	private int gk_voorstel_id;
	
	public ProductVoorstel(int id, String naam, double prijs, String categorie, int gebruikers_id, int gk_voorstel_id) {
		this.id = id;
		this.naam = naam;
		this.prijs = prijs;
		this.categorie = categorie;
		this.gebruikers_id = gebruikers_id;
		this.gk_voorstel_id = gk_voorstel_id;
	}
	
	public ProductVoorstel(String naam, double prijs, String categorie, int gebruikers_id) {
		this.naam = naam;
		this.prijs = prijs;
		this.categorie = categorie;
		this.gebruikers_id = gebruikers_id;
	}
	
	public ProductVoorstel(String naam, double prijs, String categorie, int gebruikers_id, int gk_voorstel_id) {
		this.naam = naam;
		this.prijs = prijs;
		this.categorie = categorie;
		this.gebruikers_id = gebruikers_id;
		this.gk_voorstel_id = gk_voorstel_id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public double getPrijs() {
		return prijs;
	}

	public void setPrijs(double prijs) {
		this.prijs = prijs;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public int getGebruikers_id() {
		return gebruikers_id;
	}

	public void setGebruikers_id(int gebruikers_id) {
		this.gebruikers_id = gebruikers_id;
	}

	public int getGk_voorstel_id() {
		return gk_voorstel_id;
	}

	public void setGk_voorstel_id(int gk_voorstel_id) {
		this.gk_voorstel_id = gk_voorstel_id;
	}

}
