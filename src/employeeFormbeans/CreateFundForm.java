package employeeFormbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateFundForm  extends FormBean  {
	private String name;
	private String symbol;
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
