package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Transaction;
import employeeFormbeans.DepositCheckForm;

public class DepositCheckAction  extends Action  {
	private FormBeanFactory<DepositCheckForm> formBeanFactory = FormBeanFactory
			.getInstance(DepositCheckForm.class);
	
	private TransactionDAO transactionDAO;
	public DepositCheckAction(Model model) {
		transactionDAO = model.getTransactionDAO();
	}
	
	public String getName() {
		return "depositCheck.do";
	}
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			

			DepositCheckForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "depositCheck.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "depositCheck.jsp";
			}
            
			Transaction transaction;
			// Create the transaction bean
			transaction = new Transaction();
			transaction.setCustomer_id(form.getCustomer_idAsInt());
			transaction.setFund_id(-1); // means it is not a fund
			transaction.setExecute_date(null);
			transaction.setShares(-1);
			transaction.setTransaction_type("Check");
			transaction.setAmount(form.getAmountAsDouble());
			
			transactionDAO.create(transaction);
     

			return "viewCustomerList.do";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "depositCheck.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "depositCheck.jsp";
		}
	}
}
