package employeeFormbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateCusAccForm extends FormBean {
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private String confirm;
	private String addr_line1;
	private String addr_line2;
	private String state;
	private String city;
	private String zip;
	private String availablebalance;
	private String totalbalance;
	
	public String getUsername() {
		return username;
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
		firstname = trimAndConvert(s, "<>\"");
	}

	public void setLastName(String s) {
		lastname = trimAndConvert(s, "<>\"");
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
			errors.add("Email is required");
		}

		if (firstname == null || firstname.length() == 0) {
			errors.add("First Name is required");
		}

		if (lastname == null || lastname.length() == 0) {
			errors.add("Last Name is required");
		}

		if (password == null || password.length() == 0) {
			errors.add("Password is required");
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

		return errors;
	}
    
	public long getAvailablebalanceAsLong() {
		long availableLong = Long.parseLong(availablebalance);
		return availableLong;
	}


	public String getTotalbalance() {
		return totalbalance;
	}
	public long getTotalBalanceAsLong() {
		long totalLong = Long.parseLong(totalbalance);
		return totalLong;
		
	}

	public void setTotalbalance(String totalbalance) {
		this.totalbalance = totalbalance;
	}

	public String getAvailablebalance() {
		return availablebalance;
	}

	public void setAvailablebalance(String availablebalance) {
		this.availablebalance = availablebalance;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
