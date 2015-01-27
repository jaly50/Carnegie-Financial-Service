package employeeFormbeans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
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

	public List<String> validateFundPrice(String[] prices) {
		List<String> errors = new ArrayList<String>();
		if (prices == null || prices.length < 1) {
			errors.add("No price available");
			return errors;
		}
		double realAmount;
		long databaseAmount;
		for (String amount : prices) {
			amount = amount.trim();
			if (amount == null || amount.length() == 0) {
				errors.add("Please fill in price of every fund.");
				return errors;
			}
			try {
				realAmount = Double.parseDouble(amount);
			} catch (NumberFormatException e) {
				errors.add("Fund price should be a number");
				return errors;
			}
			if (realAmount < 0.01) {
				errors.add("The minimum deposit number is $0.01");
			}
			databaseAmount = (long) (realAmount * 100);
			if (databaseAmount - realAmount * 100 != 0) {
				errors.add("Fund price should be x.xx(tracked to two decimal places)");
			}

		}

		return errors;

	}

	public List<String> validateDate(String sdate) {
		List<String> errors = new ArrayList<String>();
		if (sdate == null) {
			errors.add("Please add a date");
		}
		if (sdate.length() < 8) {
			errors.add("Please input correct date format. It should be like yyyymmdd");
			return errors;
		}
		Date date;
		DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
		try {
			date = format.parse(sdate);
		} catch (java.text.ParseException e) {
			errors.add("Please input correct date");
			e.printStackTrace();
		}
		return errors;
	}

}
