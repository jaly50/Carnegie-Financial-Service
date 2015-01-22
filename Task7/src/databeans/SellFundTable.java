package databeans;

public class SellFundTable {
	private String fundName;
	private String symbol;
	private String latestPrice;
	private String shares;
	private String marketValue;

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setLatestPrice(String latestPrice) {
		this.latestPrice = latestPrice;
	}

	public void setShares(String shares) {
		this.shares = shares;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	public String getFundName() {
		return fundName;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getLatestPrice() {
		return latestPrice;
	}

	public String getShares() {
		return shares;
	}

	public String getMarketValue() {
		return marketValue;
	}

}
