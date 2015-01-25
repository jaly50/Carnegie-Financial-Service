package databeans;

public class SellFundTable {
	private String fundName;
	private String symbol;
	private String latestPrice;
	private String availableShares;

	
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getFundName() {
		return fundName;
	}

	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getSymbol() {
		return symbol;
	}
	
	

	public void setLatestPrice(String latestPrice) {
		this.latestPrice = latestPrice;
	}
	public String getLatestPrice() {
		return latestPrice;
	}
	
	
	public String getAvailableShares() {
		return availableShares;
	}
	public void setAvailableShares(String availableShares) {
		this.availableShares = availableShares;
	}

	

}
