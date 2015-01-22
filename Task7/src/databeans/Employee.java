package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("username")
public class Employee extends User {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
    public String getPassword()        { return password; }
    public String getUsername()        { return username; }

    public void setPassword(String s)  { password = s;    }
    public void setUsername(String s)  { username = s;    }
    public String getFirstname()        { return firstname; }
    public String getLastname()        { return lastname; }

    public void setFirstname(String s)  { firstname = s;    }
    public void setLastname(String s)  { lastname = s;    }

}