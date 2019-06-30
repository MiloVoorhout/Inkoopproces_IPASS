package inkoop.budget;

import java.util.List;

public interface BudgetDao {
    //Find functies
    //Find all the budgets
    public List<Budget> findAll();
    
    //Update a specific budget
    public Boolean update(int number, double budget, String type);
}
