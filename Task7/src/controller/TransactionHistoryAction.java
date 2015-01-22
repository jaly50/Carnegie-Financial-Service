/*
 * Name: Charlotte Lin
 * Date: 01/17/2015
 */

/* Customer view his own transaction history
 * 1. get the input form, customer id
 * 2. use customer id to get the corresponding customer data bean
 * 3. ????????use what to get transaction?????? only customer id or id and type????????
 * 4. set transaction attribute to request
 * 5. calculate some other values? Where????????
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Model;
import model.FundDAO;
import model.PositionDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Customer;
import databeans.Position;
import databeans.Fund;
import databeans.Transaction;

public class TransactionHistoryAction extends Action {
	
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	
	// constructor
	public TransactionHistoryAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
	}
	
	// get action name
	public String getName() {
		return "TransactionHistoryAction.do";
	}
	
	// return next page name
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		// get session
		HttpSession session = request.getSession();
		
		// get customer
		Customer customer = (Customer) session.getAttribute("Customer");
		
		if (customer == null) return "login.jsp";
		
		// get transaction
		try {
			Transaction[] fundTransactions = 
					transactionDAO.getTransactions(customer.getCustomer_id(), "Fund");
			Transaction[] checkTransactions= 
					transactionDAO.getTransactions(customer.getCustomer_id(), "Check");	
			
			// set transaction attribute
			request.setAttribute("fundTransactions", fundTransactions);
			request.setAttribute("checkTransactions", checkTransactions);
		
			// set fund attribute
			HashMap<Integer, Fund> fundMap = new HashMap<Integer, Fund>();
			for (int i = 0; i < fundTransactions.length; i++) {
				int fund_id = fundTransactions[i].getFund_id();
				fundMap.put(fund_id, fundDAO.getFund(fund_id));
			}
 			request.setAttribute("fundMap", fundMap);
			
			return "transaction-history.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "transaction-history.jsp";
		}
	}
}

