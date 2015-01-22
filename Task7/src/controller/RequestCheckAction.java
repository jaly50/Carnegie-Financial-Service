package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Customer;
import databeans.Transaction;
import customerFormbeans.RequestCheckForm;

public class RequestCheckAction   extends Action  {
	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory
			.getInstance(RequestCheckForm.class);
	
	private TransactionDAO transactionDAO;
	public RequestCheckAction(Model model) {
		transactionDAO = model.getTransactionDAO();
	}
	
	public String getName() {
		return "requestCheck.do";
	}
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		// get session
				HttpSession session = request.getSession();

		try {
			

			RequestCheckForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "requestCheck.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "requestCheck.jsp";
			}
			Customer customer = (Customer) session.getAttribute("user");
			if (customer==null) {
				return "login.jsp";
			}
			
			Transaction transaction;
			// Create the transaction bean
			transaction = new Transaction();
			transaction.setCustomer_id(customer.getCustomer_id());
			transaction.setTransaction_type("Check");
			transaction.setAmount(-form.getAmountAsDouble()); // request money should be negative value
			
			transactionDAO.create(transaction);
     
            
			return "transactionHistory.do";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "requestCheck.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "requestCheck.jsp";
		}
	}

}
