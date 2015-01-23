package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.Transaction;

public class TransactionDAO  extends GenericDAO<Transaction>  {
	public TransactionDAO(String tableName, ConnectionPool pool)
			throws DAOException {
		super(Transaction.class, tableName, pool);

	}

	public void create(Transaction newTransaction) throws RollbackException {
		createAutoIncrement(newTransaction);
	}
	/*
	 * Given a type of transaction and a customer id
	 * return an array within the range of that type and the specific customer
	 * types includes: Fund, Check
	 */
	public Transaction[] getTransactions(int customer_id, String transaction_type) throws RollbackException {
		MatchArg matchArg1 = MatchArg.equals("customer_id", customer_id);
		MatchArg matchArg2 = MatchArg.equals("transaction_type", transaction_type);
		Transaction[] list =  match(MatchArg.and(matchArg1,matchArg2));
		return list;
	}
	
	public Transaction[] getTransactions(int customer_id) throws RollbackException {
		MatchArg matchArg = MatchArg.equals("customer_id", customer_id);
		Transaction[] list =  match(MatchArg.and(matchArg));
		return list;
	}

}