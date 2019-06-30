package inkoop.gekeurdevoorstellen;

public class GekeurdeVoorstellen {
	private int id;
	private String product;
	private String status;
	private int user_id;
	
	public GekeurdeVoorstellen(int id, String product, String status, int user_id) {
		this.id = id;
		this.product = product;
		this.status = status;
		this.user_id = user_id;
	}
	
	public GekeurdeVoorstellen(String product, String status, int user_id) {
		this.product = product;
		this.status = status;
		this.user_id = user_id;
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

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	
}
