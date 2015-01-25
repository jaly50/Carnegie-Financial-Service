/*
 * Name: Charlotte Lin
 * Date: 01/22/2015
 */
package controller;

import java.sql.Date;
import java.text.DecimalFormat;
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
import databeans.Fund_Price_History;
import databeans.Position;
import databeans.Fund;
import databeans.Transaction;
import databeans.TransactionHistory;

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
	
	DecimalFormat priceFormat = new DecimalFormat("#,##0.00");
	DecimalFormat sharesFormat = new DecimalFormat("#,##0.000");
	
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
		Customer customer;
		// You might get customer attribute from page CustomerList
        customer = (Customer) session.getAttribute("customer");
        session.setAttribute("customer", null);
        if (customer == null) {
        	customer = (Customer) session.getAttribute("user");
        }
		
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
			
			ArrayList<TransactionHistory> transactionInfo = new ArrayList<TransactionHistory>();
			
			for (Transaction trans : buyFundTransactions) {
				TransactionHistory item = new TransactionHistory();
				
				int fund_id = trans.getFund_id();
				Fund fund = fundDAO.getFund(fund_id);
				
				// get price: Long to double 
				long priceOrigin = 0;
				Fund_Price_History fundPrice = fundPriceHistoryDAO.getLatestFundPrice(fund_id);
				if (fundPrice == null) item.setPrice("pending");
				else {
					priceOrigin = fundPrice.getPrice();
					String price = priceFormat.format((double) priceOrigin / 100);
					item.setPrice(price);
				}
				
				// get share : Long to double 
				long sharesOrigin = trans.getShares();
				String shares = sharesFormat.format((double) sharesOrigin / 1000);
				item.setShares(shares);
				
				// amount
				double amountOrigin = trans.getAmount();
				String amount = priceFormat.format(amountOrigin);
				item.setAmount(amount);
				
				// date
				String date = trans.getExecute_date().toString();
				item.setDate(date);
				
				// name
				String name = fund.getName();
				item.setName(name);
				
				// symbol
				String symbol = fund.getSymbol();
				item.setSymbol(symbol);
				
				// operation
				String operation = trans.getTransaction_type();
				item.setOperation(operation);	
				
				transactionInfo.add(item);
			}
			
			for (Transaction trans : sellFundTransactions) {
				TransactionHistory item = new TransactionHistory();
				
				int fund_id = trans.getFund_id();
				Fund fund = fundDAO.getFund(fund_id);
				
				// get price: Long to double 
				long priceOrigin = 0;
				Fund_Price_History fundPrice = fundPriceHistoryDAO.getLatestFundPrice(fund_id);
				if (fundPrice == null) item.setPrice("pending");
				else {
					priceOrigin = fundPrice.getPrice();
					String price = priceFormat.format((double) priceOrigin / 100);
					item.setPrice(price);
				}
				
				// get share : Long to double 
				long sharesOrigin = trans.getShares();
				String shares = sharesFormat.format((double) sharesOrigin / 1000);
				item.setShares(shares);
				
				// amount
				double amountOrigin = trans.getAmount();
				String amount = priceFormat.format(amountOrigin);
				item.setAmount(amount);
				
				// date
				String date = trans.getExecute_date().toString();
				item.setDate(date);
				
				// name
				String name = fund.getName();
				item.setName(name);
				
				// symbol
				String symbol = fund.getSymbol();
				item.setSymbol(symbol);
				
				// operation
				String operation = trans.getTransaction_type();
				item.setOperation(operation);	
				
				transactionInfo.add(item);
			}
			
			for (Transaction trans : requestCheckTransactions) {
				TransactionHistory item = new TransactionHistory();
				
				int fund_id = trans.getFund_id();
				Fund fund = fundDAO.getFund(fund_id);
				Fund_Price_History fundPriceHistory = fundPriceHistoryDAO.getLatestFundPrice(fund_id);
				
				// set price 
				item.setPrice("");
				
				// set share
				item.setShares("");
				
				// amount
				double amountOrigin = trans.getAmount();
				String amount = priceFormat.format(amountOrigin);
				item.setAmount(amount);
				
				// date
				String date = trans.getExecute_date().toString();
				item.setDate(date);
				
				// name
				item.setName("");
				
				// symbol
				item.setSymbol("");
				
				// operation
				String operation = trans.getTransaction_type();
				item.setOperation(operation);	
				
				transactionInfo.add(item);
			}
			
			for (Transaction trans : depositCheckTransactions) {
				TransactionHistory item = new TransactionHistory();
				
				int fund_id = trans.getFund_id();
				Fund fund = fundDAO.getFund(fund_id);
				Fund_Price_History fundPriceHistory = fundPriceHistoryDAO.getLatestFundPrice(fund_id);
				
				// set price 
				item.setPrice("");
				
				// set share
				item.setShares("");
				
				// amount
				double amountOrigin = trans.getAmount();
				String amount = priceFormat.format(amountOrigin);
				item.setAmount(amount);
				
				// date
				String date = trans.getExecute_date().toString();
				item.setDate(date);
				
				// name
				item.setName("");
				
				// symbol
				item.setSymbol("");
				
				// operation
				String operation = trans.getTransaction_type();
				item.setOperation(operation);	
				
				transactionInfo.add(item);
			}
			
 			request.setAttribute("transactionInfo", transactionInfo);
 			
			return "transaction-history.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "transaction-history.jsp";
		}
	}
}