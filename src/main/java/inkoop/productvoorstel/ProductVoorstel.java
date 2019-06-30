package inkoop.productvoorstel;

public class ProductVoorstel {
	private int id;
	private String name;
	private double price;
	private String category;
	private int user_id;
	private int gk_proposal_id;
	
	public ProductVoorstel(int id, String name, double price, String category, int user_id, int gk_proposal_id) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.user_id = user_id;
		this.gk_proposal_id = gk_proposal_id;
	}
	
	public ProductVoorstel(String name, double price, String category, int user_id) {
		this.name = name;
		this.price = price;
		this.category = category;
		this.user_id = user_id;
	}
	
	public ProductVoorstel(String name, double price, String category, int user_id, int gk_proposal_id) {
		this.name = name;
		this.price = price;
		this.category = category;
		this.user_id = user_id;
		this.gk_proposal_id = gk_proposal_id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getGk_proposal_id() {
		return gk_proposal_id;
	}

	public void setGk_proposal_id(int gk_proposal_id) {
		this.gk_proposal_id = gk_proposal_id;
	}

}
