/*
 * Name: Charlotte Lin
 * Date: 01/17/2015
 */

/* Employee view customer's transaction history
 * 1. get customer attribute of current session
 * 2. use customer id and transaction type to get transactions
 * 3. set transaction attribute to request
 * 4. calculate some other values? Where????????
 */

package controller;

import java.util.ArrayList;
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
import employeeFormbeans.ViewCusAccForm;

public class ViewCusTransactionHistoryAction extends Action {
	private FormBeanFactory<ViewCusAccForm> formBeanFactory = FormBeanFactory
			.getInstance(ViewCusAccForm.class);
	
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	
	// constructor
	public ViewCusTransactionHistoryAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
	}
	
	// get action name
	public String getName() {
		return "ViewCusTransactionHistoryAction.do";
	}
	
	// return next page name
	public String perform(HttpServletRequest request) {
		// get session
		HttpSession session = request.getSession();
		
		// set error attribute
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		// get form, set form attribute
		try {
			ViewCusAccForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
						
			// if no parameters passed in, show jsp
			if (!form.isPresent()) {
				return "view-customer-transaction-history.jsp";
			}
							
			// check error, if has
			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0) {
				return "error.jsp";
			}
							
			// if no error, get the customer data bean
			Customer customer = customerDAO.read(form.getCustomer_idAsInt());
				
			// get transaction
			///////////////////////////////// don't know transaction type
			Transaction[] transaction1 = 
					transactionDAO.getTransactions(customer.getCustomer_id(), "transaction");
			Transaction[] transaction2 = 
					transactionDAO.getTransactions(customer.getCustomer_id(), "cash");	
				
			// set transaction attribute
			request.setAttribute("transaction", transaction1);
			request.setAttribute("transaction", transaction2);
					
			return "view-customer-transaction-history-detail.jsp";
		} catch (FormBeanException e) {
			return "errors.jsp";
		} catch (RollbackException e) {
			return "errors.jsp";
		} 		
	}
}

