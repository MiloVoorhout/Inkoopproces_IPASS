package inkoop.budgetvoorstellen;

import java.util.List;

public interface BudgetVoorstellenDao {
	public List<BudgetVoorstellen> findAll();
	
    public Boolean save(BudgetVoorstellen budgetProposal);
    
    public Boolean delete(int id);
}
