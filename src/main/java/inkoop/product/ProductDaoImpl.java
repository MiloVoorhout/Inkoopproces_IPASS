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
		List<Product> producten = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM product ORDER BY categorie, naam");

            while (results.next()) {

                producten.add(new Product(
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
        return producten;
	}
	
	public Boolean save(Product product) {
        try {
            String saveQuery = "INSERT INTO product(naam, prijs, categorie) VALUES (?, ?, ?)";
            PreparedStatement save = conn.prepareStatement(saveQuery);
            save.setString(1, product.getNaam());
            save.setDouble(2, product.getPrijs());
            save.setString(3, product.getCategorie());
            save.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
	}

	public Boolean update(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean delete(Product product) {
		// TODO Auto-generated method stub
		return null;
	}


}
