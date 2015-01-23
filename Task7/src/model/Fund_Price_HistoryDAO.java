package model;

import java.util.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.Fund_Price_History;

public class Fund_Price_HistoryDAO extends GenericDAO<Fund_Price_History> {
	public Fund_Price_HistoryDAO(String tableName, ConnectionPool pool)
			throws DAOException, RollbackException {
		super(Fund_Price_History.class, tableName, pool);
	}

	public void create(Fund_Price_History fph) throws RollbackException {
		create(fph);
	}

	public long getCurrentPrice(int fund_id) throws RollbackException {

		long prices = 0;
		Fund_Price_History[] fundPriceHistorys = this.getFundPrice(fund_id);
		prices = fundPriceHistorys[fundPriceHistorys.length - 1].getPrice();
		return prices;
	}

	public Fund_Price_History[] getFundPrice(Date price_date)
			throws RollbackException {
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals(
				"price_date", price_date));
		return Fund_Price_Historys;
	}

	public Fund_Price_History[] getFundPrice(int fund_id)
			throws RollbackException {
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals(
				"fund_id", fund_id));
		return Fund_Price_Historys;
	}
}
