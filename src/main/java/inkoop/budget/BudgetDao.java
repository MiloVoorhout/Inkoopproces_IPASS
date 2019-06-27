package inkoop.budget;

import java.util.List;

public interface BudgetDao {
    //Find functies
    //Find all the budgets
    public List<Budget> findAll();
    
    //Find by budget id
    public Budget findById(int id);
    
    //Update a specific budget
    public Boolean update(int nummer, double bedrag, String type);
}
