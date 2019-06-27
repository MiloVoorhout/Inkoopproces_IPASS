package inkoop.budget;

public class Budget {
	private int id;
	private double budget;
	private int afdeling;
	
	public Budget(int id, double budget, int afdeling) {
		this.setId(id);
		this.budget = budget;
		this.afdeling = afdeling;
	}
	
	public Budget(double budget, int afdeling) {
		this.budget = budget;
		this.afdeling = afdeling;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public int getAfdeling() {
		return afdeling;
	}

	public void setAfdeling(int afdeling) {
		this.afdeling = afdeling;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
