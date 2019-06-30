package inkoop.gebruiker;

public class Gebruiker {
	private int id;
	private String username;
	private String password;
	private String firstname;
	private String insertion;
	private String lastname;
	private int department;
	private String role;
	
	public Gebruiker (String username, String password, String firstname, String insertion, String lastname, int department, String role ) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.insertion = insertion;
		this.lastname = lastname;
		this.department = department;
		this.role = role;
	}
	
	public Gebruiker (String username, String password, String firstname, String lastname, int department, String role ) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.department = department;
		this.role = role;
	}
	
	public Gebruiker (int id, String firstname, String lastname, int department) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.department = department;
	}
	
	public Gebruiker (int id, String role ) {
		this.id = id;
		this.role = role;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstname;
	}
	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	public String getInsertion() {
		return insertion;
	}
	public void setInsertion(String insertion) {
		this.insertion = insertion;
	}

	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getDepartment() {
		return department;
	}
	public void setDepartment(int department) {
		this.department = department;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
