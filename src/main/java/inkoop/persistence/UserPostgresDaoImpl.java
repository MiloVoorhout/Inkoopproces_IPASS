package inkoop.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import inkoop.connection.PostgresBaseDao;

public class UserPostgresDaoImpl extends PostgresBaseDao {
	private Connection conn = super.getConnection();
	
	public String findRoleForUser(String name, String pass) {
		String role = null;
		
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT role FROM useraccount WHERE username = ? AND password = ?";
			PreparedStatement select = conn.prepareStatement(query);
			select.setString(1, name);
			select.setString(2, pass);
			ResultSet results = select.executeQuery();
			
			while (results.next()) {
				role = results.getString("role");
			}
			
			results.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return role;
	}
}
