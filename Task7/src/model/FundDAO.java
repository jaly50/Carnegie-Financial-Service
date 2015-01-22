package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.RollbackException;

import databeans.Customer;
import databeans.Fund;

public class FundDAO extends GenericDAO<Fund> {
	public FundDAO(String tableName, ConnectionPool pool)
			throws DAOException {
		super(Fund.class, tableName, pool);

	}

	public void create(Fund newFund) throws RollbackException {
		createAutoIncrement(newFund);
	}
	
	public Fund getFund(int fund_id)throws RollbackException{
		return read(fund_id);
	}

	public Fund[] getFunds() throws RollbackException {
		Fund[] funds = match();
		return funds;
	}

}
