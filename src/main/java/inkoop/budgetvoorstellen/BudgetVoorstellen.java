package inkoop.budgetvoorstellen;

public class BudgetVoorstellen {
	private int id;
	private double increase;
	private int department;
	private int user_id;
	private int budget_id;
	private String userName;
	private int gk_proposal_id;
	
	public BudgetVoorstellen(int id, double increase, int department, int user_id, int budget_id) {
		this.id = id;
		this.increase = increase;
		this.department = department;
		this.user_id = user_id;
		this.budget_id = budget_id;
	}
	
	public BudgetVoorstellen(int id, double increase, int department, int user_id, int budget_id, String userName, int gk_proposal_id) {
		this.id = id;
		this.increase = increase;
		this.department = department;
		this.user_id = user_id;
		this.budget_id = budget_id;
		this.userName = userName;
		this.gk_proposal_id = gk_proposal_id;
	}
	
	public BudgetVoorstellen(double increase, int department, int user_id, int budget_id, int gk_proposal_id) {
		this.increase = increase;
		this.department = department;
		this.user_id = user_id;
		this.budget_id = budget_id;
		this.gk_proposal_id = gk_proposal_id;
	}

	public double getIncrease() {
		return increase;
	}

	public void setIncrease(double increase) {
		this.increase = increase;
	}

	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getBudget_id() {
		return budget_id;
	}

	public void setBudget_id(int budget_id) {
		this.budget_id = budget_id;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
