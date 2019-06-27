package inkoop.gekeurdevoorstellen;

public class GekeurdeVoorstellen {
	private int id;
	private String product;
	private String status;
	private int gebruikers_id;
	
	public GekeurdeVoorstellen(int id, String product, String status, int gebruikers_id) {
		this.id = id;
		this.product = product;
		this.status = status;
		this.gebruikers_id = gebruikers_id;
	}
	
	public GekeurdeVoorstellen(String product, String status, int gebruikers_id) {
		this.product = product;
		this.status = status;
		this.gebruikers_id = gebruikers_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	
	
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getGebruikers_id() {
		return gebruikers_id;
	}

	public void setGebruikers_id(int gebruikers_id) {
		this.gebruikers_id = gebruikers_id;
	}
	
	
}
