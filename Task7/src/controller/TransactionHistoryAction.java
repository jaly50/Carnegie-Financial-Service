/*
 * Name: Charlotte Lin
 * Date: 01/22/2015
 */

/* 
 * Attribute: buyFundTransactions; sellFundTransactions;
 * 				 requestCheckTransactions; depositFundTransactions;
 * 				 buyFundMap; buyFundPriceMap;
 * 				 sellFundMap; sellFundPriceMap;
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
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Customer;
import databeans.Position;
import databeans.Fund;
import databeans.Transaction;

public class TransactionHistoryAction extends Action {
	
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private Fund_Price_HistoryDAO fundPriceHistoryDAO;
	
	// constructor
	public TransactionHistoryAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFund_Price_HistoryDAO();
	}
	
	// get action name
	public String getName() {
		return "TransactionHistoryAction.do";
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
		
		// get transaction
		try {
			Transaction[] buyFundTransactions = 
					transactionDAO.getTransactions(customer.getCustomer_id(), "BuyFund");
			Transaction[] sellFundTransactions= 
					transactionDAO.getTransactions(customer.getCustomer_id(), "SellFund");
			Transaction[] requestCheckTransactions= 
					transactionDAO.getTransactions(customer.getCustomer_id(), "RequestCheck");
			Transaction[] depositCheckTransactions= 
					transactionDAO.getTransactions(customer.getCustomer_id(), "DepositCheck");
			
			// set transaction attribute
			request.setAttribute("buyFundTransactions", buyFundTransactions);
			request.setAttribute("sellFundTransactions", sellFundTransactions);
			request.setAttribute("requestCheckTransactions", requestCheckTransactions);
			request.setAttribute("depositCheckTransactions", depositCheckTransactions);
		
			// set buyFund attribute (fund name); set buyFundPriceMap
			HashMap<Integer, Fund> buyFundMap = new HashMap<Integer, Fund>();
			HashMap<Integer, Double> buyFundPriceMap = new HashMap<Integer, Double>();
			for (int i = 0; i < buyFundTransactions.length; i++) {
				int transaction_id = buyFundTransactions[i].getTransaction_id();
				int fund_id = buyFundTransactions[i].getFund_id();
				buyFundMap.put(transaction_id, fundDAO.getFund(fund_id));
							
				Date date = (Date) buyFundTransactions[i].getExecute_date();
				long priceOrigin = fundPriceHistoryDAO.getFundPrice(date, fund_id).getPrice();
				double price = (double) priceOrigin / 1000;
				buyFundPriceMap.put(transaction_id, price);
			}
 			request.setAttribute("buyFundMap", buyFundMap);
 			request.setAttribute("buyFundPriceMap", buyFundPriceMap);
			
 			// set sell fund attribute (fund name); set sellFundPriceMap
 			HashMap<Integer, Fund> sellFundMap = new HashMap<Integer, Fund>();
 			HashMap<Integer, Double> sellFundPriceMap = new HashMap<Integer, Double>();
 			for (int i = 0; i < sellFundTransactions.length; i++) {
 				int transaction_id = sellFundTransactions[i].getTransaction_id();
 				int fund_id = sellFundTransactions[i].getFund_id();
 				buyFundMap.put(transaction_id, fundDAO.getFund(fund_id));
 				
 				Date date = (Date) sellFundTransactions[i].getExecute_date();
				long priceOrigin = fundPriceHistoryDAO.getFundPrice(date, fund_id).getPrice();
				double price = (double) priceOrigin / 1000;
				sellFundPriceMap.put(transaction_id, price);
 			}
 		 	
 			request.setAttribute("sellFundMap", sellFundMap);
 			request.setAttribute("sellFundPriceMap", sellFundPriceMap);
 			
			return "transaction-history.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "transaction-history.jsp";
		}
	}
}

