package inkoop.productvoorstel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import inkoop.connection.PostgresBaseDao;

public class ProductVoorstelDaoImpl extends PostgresBaseDao implements ProductVoorstelDao{
    private Connection conn = super.getConnection();	
	
	public List<ProductVoorstel> findAll(int id) {
		List<ProductVoorstel> product_voorstellen = new ArrayList<>();

        try {
            String getQuery = "SELECT * FROM product_voorstel WHERE gebruikers_id != ? ORDER BY naam";
            PreparedStatement getProductVoorstellen = conn.prepareStatement(getQuery);
            getProductVoorstellen.setInt(1,id);
            ResultSet results = getProductVoorstellen.executeQuery();
            
            while (results.next()) {

            	product_voorstellen.add(new ProductVoorstel(
                        results.getInt("id"),
                        results.getString("naam"),
                        results.getDouble("prijs"),
                        results.getString("categorie"),
                        results.getInt("gebruikers_id"),
                        results.getInt("gk_voorstel_id")
                ));
            }

            results.close();
            getProductVoorstellen.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product_voorstellen;
	}
	
    //Using prepared statements for safety
    public Boolean save(ProductVoorstel productVoorstel) {
        try {
            String saveQuery = "INSERT INTO product_voorstel(naam, prijs, categorie, gebruikers_id, gk_voorstel_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement save = conn.prepareStatement(saveQuery);
            save.setString(1, productVoorstel.getNaam());
            save.setDouble(2, productVoorstel.getPrijs());
            save.setString(3, productVoorstel.getCategorie());
            save.setInt(4, productVoorstel.getGebruikers_id());
            save.setInt(5, productVoorstel.getGk_voorstel_id());
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
            String deleteQuery = "DELETE FROM product_voorstel WHERE id = ?";
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
