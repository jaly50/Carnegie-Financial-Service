package employeeFormbeans;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.mybeans.form.FormBean;

public class TransitionDayForm extends FormBean {
	private String price;
	private String name;
	private String symbol;
	private String newDate;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
	public String getNewDate() {
		return newDate;
	}
	public void get(String newDate) {
		this.newDate = newDate;
	}
	
	
	
	

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (name == null || name.length() == 0) {
			errors.add("Fund Name is required");
		}

		if (symbol == null || symbol.length() == 0) {
			errors.add("Fund symbol is required");
		}

		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}

}
