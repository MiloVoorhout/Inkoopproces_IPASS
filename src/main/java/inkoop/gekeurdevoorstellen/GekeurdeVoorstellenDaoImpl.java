package inkoop.gekeurdevoorstellen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import inkoop.connection.PostgresBaseDao;

public class GekeurdeVoorstellenDaoImpl extends PostgresBaseDao implements GekeurdeVoorstellenDao{
    private Connection conn = super.getConnection();	
	
	public List<GekeurdeVoorstellen> findAll(int id) {
		List<GekeurdeVoorstellen> gekeurde_voorstellen = new ArrayList<>();

        try {
            String getQuery = "SELECT * FROM gekeurde_voorstellen WHERE gebruikers_id = ? ORDER BY product";
            PreparedStatement getStatus = conn.prepareStatement(getQuery);
            getStatus.setInt(1,id);
            ResultSet results = getStatus.executeQuery();

            while (results.next()) {

            	gekeurde_voorstellen.add(new GekeurdeVoorstellen(
            			results.getInt("id"),
                        results.getString("product"),
                        results.getString("status"),
                        results.getInt("gebruikers_id")
                ));
            }

            results.close();
            getStatus.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gekeurde_voorstellen;
	}
	
    //Using prepared statements for safety
    public int save(GekeurdeVoorstellen gekeurdeVoorstellen) {
        try {
            String saveQuery = "INSERT INTO gekeurde_voorstellen(product, status, gebruikers_id) VALUES (?, ?, ?) RETURNING id";
            PreparedStatement save = conn.prepareStatement(saveQuery);
            save.setString(1, gekeurdeVoorstellen.getProduct());
            save.setString(2, gekeurdeVoorstellen.getStatus());
            save.setInt(3, gekeurdeVoorstellen.getGebruikers_id());
            ResultSet result = save.executeQuery();
            
            result.next();
            int voorstelId = result.getInt(1);
            
            return voorstelId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public Boolean update(int id, String status) {
        try {
            String updateQuery = "UPDATE gekeurde_voorstellen SET status = ? WHERE id = ?;";
            PreparedStatement update = conn.prepareStatement(updateQuery);
            update.setString(1, status);
            update.setInt(2, id);
            update.executeUpdate();
            
            update.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean delete(int id) {
        try {
            String deleteQuery = "DELETE FROM gekeurde_voorstellen WHERE id = ?";
            PreparedStatement delete = conn.prepareStatement(deleteQuery);
            delete.setInt(1, id);
            delete.executeUpdate();
            
            delete.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

