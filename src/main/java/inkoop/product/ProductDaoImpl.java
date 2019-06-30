package inkoop.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import inkoop.connection.PostgresBaseDao;

public class ProductDaoImpl extends PostgresBaseDao implements ProductDao {
    private Connection conn = super.getConnection();	

	public List<Product> findAll() {
		List<Product> products = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM product ORDER BY categorie, naam");

            while (results.next()) {

            	products.add(new Product(
                		results.getInt("id"),
                        results.getString("naam"),
                        results.getDouble("prijs"),
                        results.getString("categorie")
                ));
            }

            results.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
	}
	
	public Boolean save(Product product) {
        try {
            String saveQuery = "INSERT INTO product(naam, prijs, categorie) VALUES (?, ?, ?)";
            PreparedStatement save = conn.prepareStatement(saveQuery);
            save.setString(1, product.getName());
            save.setDouble(2, product.getPrice());
            save.setString(3, product.getCategory());
            save.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
	}

    public Boolean update(int id, String name, double price, String category) {
        try {
            String updateQuery = "UPDATE product SET naam = ?, prijs = ?, categorie = ? WHERE id = ?;";
            PreparedStatement update = conn.prepareStatement(updateQuery);
            update.setString(1, name);
            update.setDouble(2, price);
            update.setString(3, category);
            update.setInt(4, id);
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
            String deleteQuery = "DELETE FROM product WHERE id = ?";
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
