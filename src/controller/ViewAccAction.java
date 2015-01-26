/*
 * Name: Charlotte Lin
 * Date: 01/17/2015
 */

/* Customer view his own account
 * Attribute: User user; Date date; tradeFund[] funds; HashMap pricesMap;
 * 				HashMap sharesMap; HashMap valuesMap;
 */
package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
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
import databeans.ViewAccountFund;

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
	
	DecimalFormat priceFormat = new DecimalFormat("#,##0.00");
	DecimalFormat sharesFormat = new DecimalFormat("#,##0.000");
	
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
		request.setAttribute("customer", customer);
		if (customer == null) return "login.jsp";
		
		// set date
		try {
			Transaction[] transactions = 
					transactionDAO.getTransactions(customer.getCustomer_id());
			Date date = null;
			if (transactions.length == 0) {
				session.setAttribute("date", "No transaction record");
			} else {
				date = (Date) transactions[transactions.length - 1].getExecute_date();
				session.setAttribute("date", date);
			}
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		
		// get positions
		try {
			Position[] positions = positionDAO.getPositions(customer.getCustomer_id());
			// check position null
			System.out.println("positions" + positions == null);
			
			if (positions.length == 0) return "view-account.jsp";			
			ArrayList<ViewAccountFund> fundInfo = new ArrayList<ViewAccountFund>();
			
			for (Position position : positions) {
				ViewAccountFund item = new ViewAccountFund();
				int fund_id = position.getFund_id();	
				
				System.out.println("fund_id: " + fund_id);
				
				Fund fund = fundDAO.getFund(fund_id);
				
				System.out.println("fund: " + (fund == null));
				if (fund == null) {
					errors.add("Invalid fund");
					return "view-account.jsp";
				}
				
				System.out.println("fund is null" + fundDAO.getFund(fund_id) == null);
				System.out.println("fund name" + fundDAO.getFund(fund_id).getName());
				
				item.setName(fundDAO.getFund(fund_id).getName());
				item.setSymbol(fundDAO.getFund(fund_id).getSymbol());
				
				// get share : Long to double 
				long sharesOrigin = position.getShares();
				String shares = sharesFormat.format((double) sharesOrigin / 1000);
				item.setShares(shares);
				
				// get price: Long to double 
				long priceOrigin = 0;
				Fund_Price_History fundPrice = fundPriceHistoryDAO.getLatestFundPrice(fund_id);
				if (fundPrice == null) item.setPrice("pending");
				else {
					priceOrigin = fundPrice.getPrice();;
					String price = priceFormat.format((double) priceOrigin / 100);
					item.setPrice(price);
				}
				
				// get value: double 
				double valueOrigin = (double) ((sharesOrigin / 1000) * (priceOrigin / 100));
				String value = priceFormat.format(valueOrigin);
				item.setValue(fundPrice == null ? "pending" : value);
				
				// add to arraylist
				fundInfo.add(item);
			}
			session.setAttribute("fundInfo", fundInfo);
			return "view-account.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "view-account.jsp";
		}
	}
}
