package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.Fund;

public class FundDAO extends GenericDAO<Fund> {
	public FundDAO(String tableName, ConnectionPool pool)
			throws DAOException {
		super(Fund.class, tableName, pool);

	}

	public void create(Fund newFund) throws RollbackException {
		createAutoIncrement(newFund);
	}


	public Fund[] getFunds() throws RollbackException {
		Fund[] funds = match();
		if(funds.length == 0) return null;
		return funds;
	}
	
	public Fund getFund(int fund_id) throws RollbackException {
		Fund[] fund = match(MatchArg.equals("fund_id", fund_id));
		return fund[0];
	}
	
	public Fund getFund(String symbol) throws RollbackException {
		Fund[] fund = match(MatchArg.equals("symbol", symbol));
		return fund[0];
	}
	
	public int getFund_ID(String symbol) throws RollbackException {
		Fund[] fund = match(MatchArg.equals("symbol", symbol));
		return fund[0].getFund_id();
	}

}
