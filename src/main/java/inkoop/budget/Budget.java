package inkoop.budget;

public class Budget {
	private int id;
	private double budget;
	private int department;
	
	public Budget(int id, double budget, int department) {
		this.id = id;
		this.budget = budget;
		this.department = department;
	}
	
	public Budget(double budget, int department) {
		this.budget = budget;
		this.department = department;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
