package inkoop.aankoopvoorstellen;

public class AankoopVoorstellen {
	private int id;
	private int aantal;
	private String reden;
	private int product_id;
	private int gebruikers_id;
	private int gk_voorstel_id;
	private String naam;
	private double prijs;
	private int afdeling;
	
	public AankoopVoorstellen(int id, int aantal, String reden, int product_id, int gebruikers_id, int gk_voorstel_id, String naam, double prijs, int afdeling) {
		this.id = id;
		this.aantal = aantal;
		this.reden = reden;
		this.product_id = product_id;
		this.gebruikers_id = gebruikers_id;
		this.gk_voorstel_id = gk_voorstel_id;
		this.naam = naam;
		this.prijs = prijs;
		this.afdeling = afdeling;
	}
	
	public AankoopVoorstellen(int aantal, String reden, int product_id, int gebruikers_id) {
		this.aantal = aantal;
		this.reden = reden;
		this.product_id = product_id;
		this.gebruikers_id = gebruikers_id;
	}
	
	public AankoopVoorstellen(int id, int aantal, int gk_voorstel_id, String naam) {
		this.id = id;
		this.aantal = aantal;
		this.gk_voorstel_id = gk_voorstel_id;
		this.naam = naam;
	}
	
	public AankoopVoorstellen(int aantal, String reden, int product_id, int gebruikers_id, int gk_voorstel_id) {
		this.aantal = aantal;
		this.reden = reden;
		this.product_id = product_id;
		this.gebruikers_id = gebruikers_id;
		this.gk_voorstel_id = gk_voorstel_id;
	}

	public int getAantal() {
		return aantal;
	}

	public void setAantal(int aantal) {
		this.aantal = aantal;
	}

	public String getReden() {
		return reden;
	}

	public void setReden(String reden) {
		this.reden = reden;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getGebruikers_id() {
		return gebruikers_id;
	}

	public void setGebruikers_id(int gebruikers_id) {
		this.gebruikers_id = gebruikers_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getGk_voorstel_id() {
		return gk_voorstel_id;
	}

	public void setGk_voorstel_id(int gk_voorstel_id) {
		this.gk_voorstel_id = gk_voorstel_id;
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

	public int getAfdeling() {
		return afdeling;
	}

	public void setAfdeling(int afdeling) {
		this.afdeling = afdeling;
	}

	
}
