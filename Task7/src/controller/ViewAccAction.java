/*
 * Name: Charlotte Lin
 * Date: 01/17/2015
 */

/* Customer view his own account
 * Attribute: User user; Date date; tradeFund[] funds; HashMap pricesMap;
 * 				HashMap sharesMap; HashMap valuesMap;
 */
package controller;

import java.sql.Date;
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
import model.TransactionDAO;

import org.genericdao.RollbackException;

import databeans.Customer;
import databeans.Fund_Price_History;
import databeans.Position;
import databeans.Fund;
import databeans.Transaction;

public class ViewAccAction extends Action {
	
	private CustomerDAO customerDAO;
	private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private Fund_Price_HistoryDAO fundPriceHistoryDAO;
	private TransactionDAO transactionDAO;
	
	// constructor
	public ViewAccAction(Model model) {
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO();
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFund_Price_HistoryDAO();
		transactionDAO = model.getTransactionDAO();
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
		Customer customer = (Customer) session.getAttribute("user");
		
		if (customer == null) return "login.jsp";
		
		// get positions
		try {
			Position[] positions = positionDAO.getPositions(customer.getCustomer_id());
			
			// check position null
			if (positions.length == 0) return "view-account.jsp";
			
			Fund[] funds = new Fund[positions.length];
			
			// set sharesMap attribute, pricesMap attribute, valuesMap attribute
			HashMap<Integer, String> sharesMap = new HashMap<Integer, String>();
			HashMap<Integer, String> pricesMap = new HashMap<Integer, String>();
			HashMap<Integer, String> valuesMap = new HashMap<Integer, String>();
					
			// get funds and shares, set funds, sharesMap, priceMap attribute
			for (int i = 0; i < positions.length; i++) {
				int fund_id = positions[i].getFund_id();
				
				// get fund: Fund object
				funds[i] = fundDAO.getFund(fund_id);
				
				// check fund null
				if (funds[i] == null) {
					errors.add("Invalid fund");
					return "view-account.jsp";
				}
				
				// get share : Long to double 
				long sharesOrigin = positions[i].getShares();
				double shares = (double) sharesOrigin / 1000;
				String sharesStr = Double.toString(shares);
				sharesMap.put(fund_id, sharesOrigin == 0 ? "pending" : sharesStr);
		
				// get price: Long to double 
				Fund_Price_History fundPrice = fundPriceHistoryDAO.getLatestFundPrice(fund_id);
				
				long priceOrigin = 0;
				double price = 0;
				String priceStr = "";
				
				if (fundPrice != null) {
					priceOrigin =  fundPrice.getPrice();
					price = (double) priceOrigin / 1000;
					priceStr = Double.toString(price);
				}
			
				pricesMap.put(fund_id, fundPrice == null ? "pending" : priceStr);		
			
				// get value: double 
				double value = price * shares;
				String valueStr = Double.toString(value);
				valuesMap.put(fund_id, valueStr.equals("0") ? "pending" : valueStr);
			}

			request.setAttribute("funds", funds);
			request.setAttribute("sharesMap", sharesMap);
			request.setAttribute("pricesMap", pricesMap);
			request.setAttribute("valuesMap", valuesMap);
			
			// set last trade date attribute: Date
			Transaction[] transactions = 
					transactionDAO.getTransactions(customer.getCustomer_id());
			Date date = null;
			if (transactions.length != 0) {
				date = (Date) transactions[transactions.length - 1].getExecute_date();
			}
			request.setAttribute("date", date);
			
			return "view-account.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "view-account.jsp";
		}
	}
}
