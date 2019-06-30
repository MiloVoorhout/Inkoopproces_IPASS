package inkoop.aankoopvoorstellen;

public class AankoopVoorstellen {
	private int id;
	private int amount;
	private String reason;
	private int product_id;
	private int user_id;
	private int gk_proposal_id;
	private String name;
	private double price;
	private int department;
	
	public AankoopVoorstellen(int id, int amount, String reason, int product_id, int user_id, int gk_proposal_id, String name, double price, int department) {
		this.id = id;
		this.amount = amount;
		this.reason = reason;
		this.product_id = product_id;
		this.user_id = user_id;
		this.gk_proposal_id = gk_proposal_id;
		this.name = name;
		this.price = price;
		this.department = department;
	}
	
	public AankoopVoorstellen(int amount, String reason, int product_id, int user_id) {
		this.amount = amount;
		this.reason = reason;
		this.product_id = product_id;
		this.user_id = user_id;
	}
	
	public AankoopVoorstellen(int id, int amount, int gk_proposal_id, String name) {
		this.id = id;
		this.amount = amount;
		this.gk_proposal_id = gk_proposal_id;
		this.name = name;
	}
	
	public AankoopVoorstellen(int amount, String reason, int product_id, int user_id, int gk_proposal_id) {
		this.amount = amount;
		this.reason = reason;
		this.product_id = product_id;
		this.user_id = user_id;
		this.gk_proposal_id = gk_proposal_id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getGk_proposal_id() {
		return gk_proposal_id;
	}

	public void setGk_proposal_id(int gk_proposal_id) {
		this.gk_proposal_id = gk_proposal_id;
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

	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	
}
