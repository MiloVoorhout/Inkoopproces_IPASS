package inkoop.gebruiker;

import java.util.List;

public interface GebruikerDao {
	public Gebruiker findRoleForUser(String name, String pass);
	
	public List<Gebruiker> findById(int id);

}
