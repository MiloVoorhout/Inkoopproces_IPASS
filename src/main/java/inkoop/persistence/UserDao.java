package inkoop.persistence;

public interface UserDao {
	public String findRoleForUser(String name, String pass);
}
