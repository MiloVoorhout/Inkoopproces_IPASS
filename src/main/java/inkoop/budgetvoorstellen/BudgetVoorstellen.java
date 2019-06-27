package inkoop.budgetvoorstellen;

public class BudgetVoorstellen {
	private int id;
	private double vergroting;
	private int afdeling;
	private int gebruikers_id;
	private int budget_id;
	private String gebruikersNaam;
	private int gk_voorstel_id;
	
	public BudgetVoorstellen(int id, double vergroting, int afdeling, int gebruikers_id, int budget_id) {
		this.id = id;
		this.vergroting = vergroting;
		this.afdeling = afdeling;
		this.gebruikers_id = gebruikers_id;
		this.budget_id = budget_id;
	}
	
	public BudgetVoorstellen(int id, double vergroting, int afdeling, int gebruikers_id, int budget_id, String gebruikersNaam, int gk_voorstel_id) {
		this.id = id;
		this.vergroting = vergroting;
		this.afdeling = afdeling;
		this.gebruikers_id = gebruikers_id;
		this.budget_id = budget_id;
		this.gebruikersNaam = gebruikersNaam;
		this.gk_voorstel_id = gk_voorstel_id;
	}
	
	public BudgetVoorstellen(double vergroting, int afdeling, int gebruikers_id, int budget_id, int gk_voorstel_id) {
		this.vergroting = vergroting;
		this.afdeling = afdeling;
		this.gebruikers_id = gebruikers_id;
		this.budget_id = budget_id;
		this.gk_voorstel_id = gk_voorstel_id;
	}

	public double getVergroting() {
		return vergroting;
	}

	public void setVergroting(double vergroting) {
		this.vergroting = vergroting;
	}

	public int getAfdeling() {
		return afdeling;
	}

	public void setAfdeling(int afdeling) {
		this.afdeling = afdeling;
	}

	public int getGebruikers_id() {
		return gebruikers_id;
	}

	public void setGebruikers_id(int gebruikers_id) {
		this.gebruikers_id = gebruikers_id;
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

	public int getGk_voorstel_id() {
		return gk_voorstel_id;
	}

	public void setGk_voorstel_id(int gk_voorstel_id) {
		this.gk_voorstel_id = gk_voorstel_id;
	}

	public String getGebruikersNaam() {
		return gebruikersNaam;
	}

	public void setGebruikersNaam(String gebruikersNaam) {
		this.gebruikersNaam = gebruikersNaam;
	}
	
	

}
