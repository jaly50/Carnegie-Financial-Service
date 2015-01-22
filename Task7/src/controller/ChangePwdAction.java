package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.*;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.*;
import employeeFormbeans.*;

public class ChangePwdAction extends Action {
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ChangePwdForm.class);

	private EmployeeDAO employeeDAO;
	private CustomerDAO customerDAO;

	public ChangePwdAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "change-pwd.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up error list
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// Set up user list for nav bar
			//request.setAttribute("userList", userDAO.getUsers());

			// Load the form parameters into a form bean
			ChangePwdForm form = formBeanFactory.create(request);

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "change-pwd.jsp";
			}

			// Check for any validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "change-pwd.jsp";
			}

			User user = (User) request.getSession().getAttribute("user");
             System.out.println("Attribute test" + user);
			// Change the password
             
             user.setPassword(form.getNewPassword());
             if (user instanceof Customer) {

 				 String oldpass=request.getParameter("oldPassword");
            	 if(customerDAO.checkPassword(user.getUsername(), oldpass))
            	 { customerDAO.setPassword((Customer) user);
 				return "viewAccAction.do";
             }
            	 else
  	            {
  	            	errors.add("You entered the wrong password! Please enter your correct current password");
  	                return "change-pwd.jsp";
  	            }
 			} else if (user instanceof Employee) {
 				 String oldpass=request.getParameter("oldPassword");
 	            System.out.println("Username and old pass" + user.getUsername()+oldpass);
 	            if(employeeDAO.checkPassword(user.getUsername(), oldpass))
 	            {//employeeDAO.setPassword(user.getUsername(), form.getNewPassword());
 				employeeDAO.setPassword((Employee)user);
 				return "createFund.do";
 	            }
 				else
 	            {
 	            	errors.add("You entered the wrong password! Please enter your correct current password");
 	                return "change-pwd.jsp";
 	            }
 				
 			} else {
 				errors.add("No such a user type");
 				return "login.jsp";
 			}
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "error.jsp";
		}
	}
}
