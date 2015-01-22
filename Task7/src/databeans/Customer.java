package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("customer_id")
public class Customer extends User {
	private int customer_id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String addr_line1;
    private String addr_line2;
    private String state;
    private String city;
    private int zip;
    private long availablebalance;
    private long totalbalance;
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
    public String getPassword()        { return password; }
    public String getFirstname()        { return firstname; }
    public String getLastname()        { return lastname; }
    public String getAddr_line1()        { return addr_line1; }
    public String getAddr_line2()        { return addr_line2; }
    public String getState()        { return state; }
    public String getCity()        { return city; }
    public int getCustomer_id(){return customer_id;}
    public int getZip(){return zip;}
    public long getAvailablebalance(){return availablebalance;}
    public long getTotalbalance(){return totalbalance;}
    
    public void setPassword(String s)  { password = s;    }
    public void setFirstname(String s)  { firstname = s;    }
    public void setLastname(String s)  { lastname = s;    }
    public void setAddr_line1(String s)        {  addr_line1=s; }
    public void setAddr_line2(String s)        {  addr_line2=s; }
    public void setState(String s)        {  state=s; }
    public void setCity(String s)        { city=s; }
    public void setCustomer_id(int s)        { customer_id=s; }
    public void setAvailablebalance(long s)        { availablebalance=s; }
    public void setTotalbalance(long s)        { totalbalance=s; }
    public void setzip(int s)        { zip=s; }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}