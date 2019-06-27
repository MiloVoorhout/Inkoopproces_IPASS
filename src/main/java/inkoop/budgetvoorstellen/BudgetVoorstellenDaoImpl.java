package inkoop.budgetvoorstellen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import inkoop.connection.PostgresBaseDao;

public class BudgetVoorstellenDaoImpl extends PostgresBaseDao implements BudgetVoorstellenDao{
    private Connection conn = super.getConnection();	


	public List<BudgetVoorstellen> findAll() {
		List<BudgetVoorstellen> budgetVoorstellen = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT b.*, g.voornaam, g.achternaam FROM budget_voorstellen b JOIN gebruiker g ON b.gebruikers_id = g.id");

            while (results.next()) {

            	budgetVoorstellen.add(new BudgetVoorstellen(
                		results.getInt("id"),
                		results.getDouble("vergroting"),
                		results.getInt("afdeling"),
                        results.getInt("gebruikers_id"),
                        results.getInt("budget_id"),
                        (results.getString("voornaam") + " " + results.getString("achternaam")),
                        results.getInt("gk_voorstel_id")
                ));
            }

            results.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgetVoorstellen;
	}

	public Boolean save(BudgetVoorstellen budgetVoorstel) {
        try {
            String saveQuery = "INSERT INTO budget_voorstellen(vergroting, afdeling, gebruikers_id, budget_id, gk_voorstel_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement save = conn.prepareStatement(saveQuery);
            save.setDouble(1, budgetVoorstel.getVergroting());
            save.setInt(2, budgetVoorstel.getAfdeling());
            save.setInt(3, budgetVoorstel.getGebruikers_id());
            save.setInt(4, budgetVoorstel.getBudget_id());
            save.setInt(5, budgetVoorstel.getGk_voorstel_id());
            save.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
	}

	public Boolean delete(int id) {
    	try {
            String deleteQuery = "DELETE FROM budget_voorstellen WHERE id = ?";
            PreparedStatement delete = conn.prepareStatement(deleteQuery);
            delete.setInt(1, id);
            delete.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

}
