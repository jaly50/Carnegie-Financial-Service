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
	//private FormBeanFactory<UserIDForm> formBeanFactory = FormBeanFactory
		//	.getInstance(UserIDForm.class);

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

		try {
			// Set up user list for nav bar
			request.setAttribute("customerList", customerDAO.getCustomers());

			/*UserIDForm form = formBeanFactory.create(request);

			int userID = form.getUserIDAsInt();

			// Set up photo list
			User user = userDAO.read(userID);
			if (user == null) {
				errors.add("Invalid User: " + userID);
				return "error.jsp";
			}

			Customer[] customerList = customerDAO
					.getFavorites(user.getUserID());
			request.setAttribute("customerList", customerList);*/
			return "list.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} /*catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}*/
	}
}
