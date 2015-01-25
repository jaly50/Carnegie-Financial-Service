package model;

import java.util.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.Fund_Price_History;
import databeans.Transaction;


public class Fund_Price_HistoryDAO extends GenericDAO<Fund_Price_History> {
	public Fund_Price_HistoryDAO(String tableName, ConnectionPool pool) throws DAOException,
	RollbackException {
		super(Fund_Price_History.class, tableName, pool);
	}
	public void create(Fund_Price_History fph) throws RollbackException {
			create(fph);
		}
	/*
	 * Given a date, return all fund price history in that day
	 */
	public Fund_Price_History[] getFundPrice(Date price_date)  throws RollbackException {
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals("price_date", price_date));
		return Fund_Price_Historys;
	}
	
	public Fund_Price_History getFundPrice(Date price_date, int fund_id)  throws RollbackException {
		MatchArg matchArg1 = MatchArg.equals("price_date", price_date);
		MatchArg matchArg2 = MatchArg.equals("fund_id", fund_id);
		Fund_Price_History Fund_Price_History[] = match(MatchArg.and(matchArg1,matchArg2));
		return Fund_Price_History[0];
	}
	
	public Fund_Price_History[] getFundPrice(String symbol)
			throws RollbackException {
<<<<<<< HEAD
		Fund_Price_History[] fund_Price_Historys = match(MatchArg.equals(
				"symbol", symbol));
		if(fund_Price_Historys.length == 0) return null;
		return fund_Price_Historys;
=======
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals(
				"symbol", symbol));
		if(Fund_Price_Historys.length == 0) return null;
		return Fund_Price_Historys;
>>>>>>> 6e1539b78ee53ced185ef9d67ea8d1e2aa1c063f
	}
	
	
	
	public long getCurrentPrice(String Symbol) throws RollbackException{
		
		long prices = 0;
		Fund_Price_History[] fundPriceHistorys = this.getFundPrice(Symbol);
<<<<<<< HEAD
		if (fundPriceHistorys==null || fundPriceHistorys.length<1) {
			return -1;
		}
=======
>>>>>>> 6e1539b78ee53ced185ef9d67ea8d1e2aa1c063f
		prices = fundPriceHistorys[fundPriceHistorys.length-1].getPrice();
		return prices;
	}
	
	public long getCurrentPrice(int fund_id) throws RollbackException {

		long prices = 0;
		Fund_Price_History[] fundPriceHistorys = this.getFundPrice(fund_id);
<<<<<<< HEAD
		if (fundPriceHistorys==null || fundPriceHistorys.length<1) {
			return -1;
		}
=======
>>>>>>> 6e1539b78ee53ced185ef9d67ea8d1e2aa1c063f
		prices = fundPriceHistorys[fundPriceHistorys.length - 1].getPrice();
		return prices;
	}
	
	
	public Fund_Price_History getLatestFundPrice(String symbol)
			throws RollbackException {
<<<<<<< HEAD
		Fund_Price_History[] fund_Price_Historys = match(MatchArg.equals(
				"symbol", symbol));
		if(fund_Price_Historys==null ||fund_Price_Historys.length == 0) 
			return null;
		return fund_Price_Historys[fund_Price_Historys.length-1];
=======
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals(
				"symbol", symbol));
		if(Fund_Price_Historys.length == 0) return null;
		return Fund_Price_Historys[Fund_Price_Historys.length-1];
>>>>>>> 6e1539b78ee53ced185ef9d67ea8d1e2aa1c063f
	}
	
	
	
	public Fund_Price_History getLatestFundPrice(int fund_id)
			throws RollbackException {
<<<<<<< HEAD
		Fund_Price_History[] fund_Price_Historys = match(MatchArg.equals(
				"fund_id", fund_id));
		if(fund_Price_Historys==null ||fund_Price_Historys.length == 0) 
			return null;
		return fund_Price_Historys[fund_Price_Historys.length-1];
	}
	
	public Fund_Price_History[] getFundPrice(int fund_id) throws RollbackException {
		Fund_Price_History[] fund_Price_Historys = match(MatchArg.equals("fund_id", fund_id));
		if(fund_Price_Historys==null ||fund_Price_Historys.length == 0) 
			return null;
		
		return fund_Price_Historys;
=======
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals(
				"fund_id", fund_id));
		if(Fund_Price_Historys.length == 0) return null;
		return Fund_Price_Historys[Fund_Price_Historys.length-1];
	}
	
	public Fund_Price_History[] getFundPrice(int fund_id) throws RollbackException {
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals("fund_id", fund_id));
		return Fund_Price_Historys;
>>>>>>> 6e1539b78ee53ced185ef9d67ea8d1e2aa1c063f
	}
}
