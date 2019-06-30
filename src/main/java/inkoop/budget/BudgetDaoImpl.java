package inkoop.budget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import inkoop.connection.PostgresBaseDao;

public class BudgetDaoImpl extends PostgresBaseDao implements BudgetDao{
    private Connection conn = super.getConnection();	

	public List<Budget> findAll() {
		List<Budget> budgets = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM budget ORDER BY afdeling");

            while (results.next()) {

                budgets.add(new Budget(
                		results.getInt("id"),
                        results.getDouble("budget"),
                        results.getInt("afdeling")
                ));
            }

            results.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgets;
	}

	public Boolean update(int number, double budget, String type) {
        try {
        	if(type.equals("min")) {
                String updateQuery = "UPDATE budget SET budget = budget - ? WHERE afdeling = ?";
                PreparedStatement update = conn.prepareStatement(updateQuery);
                update.setDouble(1, budget);
                update.setInt(2, number);
                update.executeUpdate();

                return true;
        	} else if (type.equals("plus")) {
        		String updateQuery = "UPDATE budget SET budget = budget + ? WHERE id = ?";
                PreparedStatement update =  conn.prepareStatement(updateQuery);
                update.setDouble(1, budget);
                update.setInt(2, number);
                update.executeUpdate();

                return true;
        	}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
	}

}
