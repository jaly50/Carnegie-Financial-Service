package employeeFormbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ResetCusPwdForm  extends FormBean  {
	private String customer_id;
	private String newPassword;
	private String confirm;
	public String getCustomer_id() {
		return customer_id;
	}
	public int getCustomer_idAsNumber() {
		int id;
		id = Integer.valueOf(customer_id);
		return id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (customer_id == null || customer_id.length() == 0) {
			errors.add("Customer ID is required");
		}

		if (newPassword == null || newPassword.length() == 0) {
			errors.add("New Password is required");
		}
		if (confirm == null || confirm.length() == 0) {
			errors.add("The confirm password is required");
		}

		if (errors.size() > 0) {
			return errors;
		}


		return errors;
	}

	
}
