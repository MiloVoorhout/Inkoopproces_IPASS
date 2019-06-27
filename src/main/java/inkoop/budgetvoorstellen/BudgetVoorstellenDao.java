package inkoop.budgetvoorstellen;

import java.util.List;

public interface BudgetVoorstellenDao {
	public List<BudgetVoorstellen> findAll();
	
    public Boolean save(BudgetVoorstellen budgetVoorstel);
    
    public Boolean delete(int id);
}
