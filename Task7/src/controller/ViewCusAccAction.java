/*
 * 08-600
 * Homework #9
 * Jiali Chen
 * andrewID: jialic
 * Dec 4, 2014
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.*;
import model.*;
import employeeFormbeans.*;
import employeeFormbeans.ChangePwdForm;
import customerFormbeans.*;

/*
 * Looks up the favorites for a given "user".
 * 
 * If successful:
 *   (1) Sets the "userList" request attribute in order to display
 *       the list of users on the navbar.
 *   (2) Sets the "favoriteList" request attribute in order to display
 *       the list of given user's favorites for selection.
 *   (3) Forwards to list.jsp.
 */
public class ViewCusAccAction extends Action {
	private FormBeanFactory<ViewCustomerForm> formBeanFactory = FormBeanFactory
			.getInstance(ViewCustomerForm.class);

	private EmployeeDAO employeeDAO;
	private CustomerDAO customerDAO;

	public ViewCusAccAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "viewCustomerList.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up the request attributes (the errors list and the form bean so
		// we can just return to the jsp with the form if the request isn't
		// correct)
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		// If no params were passed, return with no errors so that the form
		// will be
		// presented (we assume for the first time).
		

		try {
			// Set up user list for nav bar
			ViewCustomerForm form = formBeanFactory.create(request);
			if (!form.isPresent()) {
				return "list.jsp";
			}

			// Check for any validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "list.jsp";
			}
			String a=form.getUsername();
            System.out.println("In view"+a); 
			Customer b=customerDAO.getCustomer(a);
            
            if(b==null)
            {
            	errors.add("No such username exists");
            	return "list.jsp";
            }
            System.out.println("b user check"+b.getUsername());
			request.setAttribute("customerdetails", b);
            return "customerdetails.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} /*catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}*/ catch (FormBeanException e) {
			// TODO Auto-generated catch block
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
