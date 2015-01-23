package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.FundDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Employee;
import databeans.Fund;
import databeans.User;
import employeeFormbeans.CreateFundForm;

// To make it work, I current use the content of create Fund
public class TransitionDayAction extends Action {
	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateFundForm.class);
	
	private FundDAO fundDAO;

	public TransitionDayAction(Model model) {
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "transitionDay.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		User user = (User) session.getAttribute("user");
			if (user==null) {
				errors.add("Please login first");
				return "login.jsp";
			}
			else if (!(user instanceof Employee)) {
				errors.add("You don't have permission to do the operation");
				return "login.jsp";
			}



			return "transitionDay.jsp";
		
	}


}
