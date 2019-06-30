package inkoop.aankoopvoorstellen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import inkoop.connection.PostgresBaseDao;

public class AankoopVoorstellenDaoImpl extends PostgresBaseDao implements AankoopVoorstellenDao {
    private Connection conn = super.getConnection();	


	public List<AankoopVoorstellen> findAll(int id) {
		List<AankoopVoorstellen> purchaseProposals = new ArrayList<>();
		
        try {
            String getQuery = "Select a.*, p.naam, p.prijs, g.afdeling FROM aankoop_voorstellen a JOIN product p ON p.id = a.product_id JOIN gebruiker g ON g.id = a.gebruikers_id WHERE a.gebruikers_id != ?";
            PreparedStatement getPurchaseProposals = conn.prepareStatement(getQuery);
            getPurchaseProposals.setInt(1,id);
            ResultSet results = getPurchaseProposals.executeQuery();
            
            while (results.next()) {

            	purchaseProposals.add(new AankoopVoorstellen(
                		results.getInt("id"),
                		results.getInt("aantal"),
                		results.getString("reden"),
                        results.getInt("product_id"),
                        results.getInt("gebruikers_id"),
                        results.getInt("gk_voorstel_id"), 
                        results.getString("naam"),
                        results.getDouble("prijs"),
                        results.getInt("afdeling")
                ));
            }

            results.close();
            getPurchaseProposals.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchaseProposals;
	}
	
	public List<AankoopVoorstellen> findByProductId(int id) {
		List<AankoopVoorstellen> purchaseProposals = new ArrayList<>();
		
        try {
            String getQuery = "Select a.*, p.naam FROM aankoop_voorstellen a JOIN product p ON p.id = a.product_id WHERE a.product_id = ?";
            PreparedStatement getPurchaseProposals = conn.prepareStatement(getQuery);
            getPurchaseProposals.setInt(1,id);
            ResultSet results = getPurchaseProposals.executeQuery();
            
            while (results.next()) {

            	purchaseProposals.add(new AankoopVoorstellen(
                		results.getInt("id"),
                		results.getInt("aantal"),
                        results.getInt("gk_voorstel_id"), 
                        results.getString("naam")
                ));
            }

            results.close();
            getPurchaseProposals.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchaseProposals;
	}


	public Boolean save(AankoopVoorstellen purchaseProposals) {
        try {
            String saveQuery = "INSERT INTO aankoop_voorstellen(aantal, reden, product_id, gebruikers_id, gk_voorstel_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement save = conn.prepareStatement(saveQuery);
            save.setInt(1, purchaseProposals.getAmount());
            save.setString(2, purchaseProposals.getReason());
            save.setInt(3, purchaseProposals.getProduct_id());
            save.setInt(4, purchaseProposals.getUser_id());
            save.setInt(5, purchaseProposals.getGk_proposal_id());
            save.executeUpdate();
            
            save.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
	}

	public Boolean delete(int id) {
    	try {
            String deleteQuery = "DELETE FROM aankoop_voorstellen WHERE id = ?";
            PreparedStatement delete = conn.prepareStatement(deleteQuery);
            delete.setInt(1, id);
            delete.executeUpdate();
            
            delete.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

}
