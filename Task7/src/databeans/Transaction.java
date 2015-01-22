package databeans;

import java.util.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("transaction_id")

public class Transaction {
	private int transaction_id = -1;
	private int customer_id = -1;
	private int fund_id = -1;
	private Date execute_date = null;
	private long shares = 0;
	// There is two transaction type : Check and Fund
	private String transaction_type = null;
	private double amount = 0;
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public Date getExecute_date() {
		return execute_date;
	}
	public void setExecute_date(Date execute_date) {
		this.execute_date = execute_date;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public long getShares() {
		return shares;
	}
	public void setShares(long shares) {
		this.shares = shares;
	}


}
