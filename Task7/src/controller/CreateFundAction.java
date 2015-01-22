package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Fund;
import employeeFormbeans.CreateFundForm;

public class CreateFundAction extends Action {
	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateFundForm.class);
	
	private FundDAO fundDAO;

	public CreateFundAction(Model model) {
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "createFund.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		System.out.println("CreateFund Action get performed");

		try {
			

			CreateFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "createFund.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "createFund.jsp";
			}

			Fund fund;
			// Create the fund bean
			fund = new Fund();
			fund.setName(form.getName());
			fund.setSymbol(form.getSymbol());
            fundDAO.create(fund);


			return "transitionDay.do";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "createFund.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "createFund.jsp";
		}
	}


}
