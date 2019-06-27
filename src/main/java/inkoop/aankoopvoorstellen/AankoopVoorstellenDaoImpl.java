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
		List<AankoopVoorstellen> aankoopVoorstellen = new ArrayList<>();

        try {
            String getQuery = "Select a.*, p.naam, p.prijs, g.afdeling FROM aankoop_voorstellen a JOIN product p ON p.id = a.product_id JOIN gebruiker g ON g.id = a.gebruikers_id WHERE a.gebruikers_id != ?";
            PreparedStatement getAankoopVoorstellen = conn.prepareStatement(getQuery);
            getAankoopVoorstellen.setInt(1,id);
            ResultSet results = getAankoopVoorstellen.executeQuery();
            
            while (results.next()) {

            	aankoopVoorstellen.add(new AankoopVoorstellen(
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
            getAankoopVoorstellen.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aankoopVoorstellen;
	}


	public Boolean save(AankoopVoorstellen aankoopVoorstellen) {
        try {
            String saveQuery = "INSERT INTO aankoop_voorstellen(aantal, reden, product_id, gebruikers_id, gk_voorstel_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement save = conn.prepareStatement(saveQuery);
            save.setInt(1, aankoopVoorstellen.getAantal());
            save.setString(2, aankoopVoorstellen.getReden());
            save.setInt(3, aankoopVoorstellen.getProduct_id());
            save.setInt(4, aankoopVoorstellen.getGebruikers_id());
            save.setInt(5, aankoopVoorstellen.getGk_voorstel_id());
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
