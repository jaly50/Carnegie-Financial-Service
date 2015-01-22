package employeeFormbeans;
import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;
public class DepositCheckForm   extends FormBean   {
	private String  username;
	private String amount;
	public double getAmountAsDouble() {
		try {
			return Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}

	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (username == null || username.length()==0)
			errors.add("Customer Username is required");
        if (amount ==null || amount.length()==0) 
        	errors.add("Amount is required");

		if (errors.size() > 0) {
			return errors;
		}


		return errors;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
