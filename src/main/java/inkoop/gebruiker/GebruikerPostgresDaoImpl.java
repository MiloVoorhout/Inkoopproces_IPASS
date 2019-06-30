package inkoop.gebruiker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import inkoop.connection.PostgresBaseDao;
import inkoop.gekeurdevoorstellen.GekeurdeVoorstellen;

public class GebruikerPostgresDaoImpl extends PostgresBaseDao {
	private Connection conn = super.getConnection();
	
	public Gebruiker findRoleForUser(String name, String pass) {
		Gebruiker user = null;

		
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT id, rol FROM gebruiker WHERE gebruikersnaam = ? AND wachtwoord = ?";
			PreparedStatement select = conn.prepareStatement(query);
			select.setString(1, name);
			select.setString(2, pass);
			ResultSet results = select.executeQuery();
			
			while (results.next()) {
				user = new Gebruiker(
						results.getInt("id"),
						results.getString("rol")
				);
			}
			
			results.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<Gebruiker> findById(int id) {
		List<Gebruiker> user = new ArrayList<>();

        try {
        	Statement stmt = conn.createStatement();
			String query = "SELECT * FROM gebruiker WHERE id = ?";
			PreparedStatement select = conn.prepareStatement(query);
			select.setInt(1, id);
			ResultSet results = select.executeQuery();

            while (results.next()) {

            	user.add(new Gebruiker(
            			results.getInt("id"),
                        results.getString("voornaam"),
                        results.getString("achternaam"),
                        results.getInt("afdeling")
                ));
            }

            results.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
	}
	
}
