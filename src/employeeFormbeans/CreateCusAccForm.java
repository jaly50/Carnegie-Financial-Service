package employeeFormbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateCusAccForm extends FormBean {
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String confirm;
	private String addr_line1;
	private String addr_line2;
	private String state;
	private String city;
	private String zip;
	private String cash;
	//private String totalbalance;
	
	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirm() {
		return confirm;
	}

	public String getAddr_line1() {
		return addr_line1;
	}

	public String getAddr_line2() {
		return addr_line2;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}
	public String getZip()
	{
		return zip;
	}

	public void setUsername(String s) {
		username = trimAndConvert(s, "<>\"");
	}

	public void setFirstName(String s) {
		firstName = trimAndConvert(s, "<>\"");
	}

	public void setLastName(String s) {
		lastName = trimAndConvert(s, "<>\"");
	}
	public void setState(String s) {
		state = trimAndConvert(s, "<>\"");
	}

	
	public void setZip(String s) {
		zip = s;
	}

	public void setCity(String s) {
		city = trimAndConvert(s, "<>\"");
	}

	public void setAddr_line1(String s) {
		addr_line1 = trimAndConvert(s, "<>\"");
	}

	public void setAddr_line2(String s) {
		addr_line2 = trimAndConvert(s, "<>\"");
	}

	public void setPassword(String s) {
		password = s.trim();
	}

	public void setConfirm(String s) {
		confirm = s.trim();
	}


	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (username == null || username.length() == 0) {
			errors.add("Username is required");
		}
		if(username.substring(0, 1).matches("[0-9]"))
		{
			errors.add("The first character of the username can't be a number");
		}

		if (firstName == null || firstName.length() == 0) {
			errors.add("First Name is required");
		}

		if (lastName == null || lastName.length() == 0) {
			errors.add("Last Name is required");
		}
		if (addr_line1 == null || addr_line1.length() == 0) {
			errors.add("Address Line 1 is required");
		}

		if (city == null || city.length() == 0) {
			errors.add("City is required");
		}

		if (state == null || state.length() == 0) {
			errors.add("State is required");
		}

		if (password == null || password.length() == 0) {
			errors.add("Password is required");
		}
		if(password.length()<6)
		{
			errors.add("Password is too short");
		}
		if(password.length()>15)
		{
			errors.add("Password can't be more than 15 characters");
		}

		if (confirm == null || confirm.length() == 0) {
			errors.add("Confirm Password is required");
		}

		if (errors.size() > 0) {
			return errors;
		}

		if (!password.equals(confirm)) {
			errors.add("Passwords are not the same");
		}
		if(!(firstName.matches("[a-zA-Z]+")))
		{
			errors.add("There should be no other characters than alphabets in first name");
		}
		if(!(lastName.matches("[a-zA-Z]+")))
		{
			errors.add("There should be no other characters than alphabets in last name");
		}
		if(!(city.matches("[a-zA-Z]+")))
		{
			errors.add("There should be no other characters than alphabets in city");
		}
		if(!(state.matches("[a-zA-Z]+")))
		{
			errors.add("There should be no other characters than alphabets in state");
		}
		if(!(zip.matches("[0-9]+")))
		{
			errors.add("Zip should only contain numbers");
		}
		if(!(cash.matches("^\\d+\\.\\d{1,2}$")))
		{
			errors.add("Should be decimal");
		}
			if(zip.length()>5)
		{
			errors.add("Zip can't ba more than 5 characters");
		}
		return errors;
	}
    
    
	public String getCash() {
		return cash;
	}
	public long getCashAsLong() {
<<<<<<< HEAD
		long cashLong = (long) (100*(Double.parseDouble(cash)));
=======
		long cashLong = 100 * Long.parseLong(cash);
>>>>>>> origin/master
		return cashLong;
		
	}

	public void setcash(String cash) {
		this.cash = cash;
	}


}