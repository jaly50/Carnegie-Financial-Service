/*
 * Name: Charlotte Lin
 * Date: 01/17/2015
 */

/* Customer view his own account
 * 1. get customer attribute of current session
 * 2. use customer id to get position
 * 3. use position to get fund
 * 4. set customer attribute to request
 * 5. set fund attribute to request
 * 6. calculate some other values? Where????????
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Fund_Price_HistoryDAO;
import model.Model;
import model.FundDAO;
import model.PositionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Customer;
import databeans.Fund_Price_History;
import databeans.Position;
import databeans.Fund;

public class ViewAccAction extends Action {
	
	private CustomerDAO customerDAO;
	private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private Fund_Price_HistoryDAO fundPriceHistoryDAO;
	
	// constructor
	public ViewAccAction(Model model) {
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO();
		fundDAO = model.getFundDAO();
	}
	
	// get action name
	public String getName() {
		return "viewAccAction.do";
	}
	
	// return next page name
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		// get session
		HttpSession session = request.getSession();
		
		// get customer
		Customer customer = (Customer) session.getAttribute("customer");
		
		if (customer == null) return "login.jsp";
		
		// get position
		try {
			Position[] positions = positionDAO.getPositions(customer.getCustomer_id());
			
			// set position attribute
			request.setAttribute("positions", positions);
			
			// set fundMap attribute and priceMap attribute
			HashMap<Integer, Fund> fundMap = new HashMap<Integer, Fund>();
			HashMap<Integer, Fund_Price_History> priceMap = 
					new HashMap<Integer, Fund_Price_History>();
			for (int i = 0; i < positions.length; i++) {
				int fund_id = positions[i].getFund_id();
				fundMap.put(fund_id, fundDAO.getFund(fund_id));
				priceMap.put(fund_id, fundPriceHistoryDAO.getLatestFundPrice(fund_id));
			}
			request.setAttribute("fundMap", fundMap);
			request.setAttribute("priceMap", priceMap);
		
			return "view-account.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "view-account.jsp";
		}
	}
}

