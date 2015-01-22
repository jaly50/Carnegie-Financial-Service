
package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.*;

public class CustomerDAO extends GenericDAO<Customer> {

	public CustomerDAO(String tableName, ConnectionPool pool) throws DAOException,
			RollbackException {
		super(Customer.class, tableName, pool);

	}
	public boolean checkPassword(String username,String oldpass)
	{    
		Customer emp=null;
		try {
			emp = read(username);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       	
	    if(emp.getPassword().equals(oldpass))
	    	return true;
	    else 
	    	return false;
	}
	
	public void createCustomer(Customer newCust) throws RollbackException {
		createAutoIncrement(newCust);
	}
	public Customer viewCustomer(int cust_id)throws RollbackException{
		return read(cust_id);
	}
	public Customer getCustomer(int cust_id)throws RollbackException{
		return read(cust_id);
	}
	public Customer[] getCustomers() throws RollbackException {
		Customer[] customers = match();
		return customers;
	}
	public Customer getCustomer(String userName) throws RollbackException {
		Customer[] customer = match(MatchArg.equals("userName", userName));
		if (customer.length == 0)
			return null;
		return customer[0];
	}

    public void setPassword(Customer cus) throws RollbackException {
    	System.out.println("check 1"+cus.getCustomer_id());
    	update(cus);
    }
	


}
