package databeans;

public class BuyFundTable {

	private String name;
	private String symbol;
	private String latestPrice;

	public BuyFundTable() {

	}

	public BuyFundTable(Fund f, String latestPrice) {
		this.name = f.getName();
		this.symbol = f.getSymbol();
		this.latestPrice = latestPrice;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setLatestPrice(String latestPrice) {
		this.latestPrice = latestPrice;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getLatestPrice() {
		return latestPrice;
	}

}
