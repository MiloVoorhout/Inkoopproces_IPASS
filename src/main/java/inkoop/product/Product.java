package inkoop.product;

public class Product {
	private int id;
	private String naam;
	private double prijs;
	private String categorie;
	
	public Product(int id, String naam, double prijs, String categorie) {
		this.setId(id);
		this.naam = naam;
		this.prijs = prijs;
		this.categorie = categorie;
	}
	
	public Product(String naam, double prijs, String categorie) {
		this.naam = naam;
		this.prijs = prijs;
		this.categorie = categorie;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
