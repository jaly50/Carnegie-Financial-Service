package customerFormbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;


public class SellFundForm extends FormBean {

	private String shares;
	private String action;
	private String symbol;
	private int    fund_id;

	public void setShares(String shares) {
		this.shares = shares;
	}
	public String getShares() {
		return shares;
	}


	public void setAction(String action) {
		this.action = action;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	
	public String getAction() {
		return action;
	}

	public String getSymbol() {
		return symbol;
	}
	
	public int getFun_id() {
		return fund_id;
	}

	
}
