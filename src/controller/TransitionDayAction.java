package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.FundDAO;
import model.Fund_Price_HistoryDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import customerFormbeans.BuyFundForm;
import databeans.Customer;
import databeans.Employee;
import databeans.Fund;
import databeans.Position;
import databeans.Transaction;
import databeans.TransiFundTable;
import databeans.User;
import employeeFormbeans.CreateFundForm;
import employeeFormbeans.TransitionDayForm;

// To make it work, I current use the content of create Fund
public class TransitionDayAction extends Action {
	private FormBeanFactory<TransitionDayForm> formBeanFactory = FormBeanFactory
			.getInstance(TransitionDayForm.class);

	private CustomerDAO customerDAO;
	private Fund_Price_HistoryDAO fundPriceHistoryDAO;
	private TransactionDAO transactionDAO;
	private PositionDAO positionDAO;
	private FundDAO fundDAO;

	public TransitionDayAction(Model model) {
		customerDAO = model.getCustomerDAO();
		fundPriceHistoryDAO = model.getFund_Price_HistoryDAO();
		transactionDAO = model.getTransactionDAO();
		positionDAO = model.getPositionDAO();
		fundDAO = model.getFundDAO();
	}

	public String getName() {
		return "transitionDay.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		Employee user = (Employee) session.getAttribute("user");

		try {

			if (user == null) {
				errors.add("Please login first");
				return "login.jsp";
			} else if (!(user instanceof Employee)) {
				errors.add("You don't have permission to do the operation");
				return "login.jsp";
			}

			TransitionDayForm transitionDayForm = formBeanFactory
					.create(request);
			request.setAttribute("transitionDayForm", transitionDayForm);

			Fund[] fundList = fundDAO.getFunds();
			for (Fund f : fundList) {
				System.out.print(f.getName() + " ");
			}
			request.setAttribute("fundList", fundDAO.getFunds());

			// TransitionTable to show funds information
			List<TransiFundTable> TransiFundTable = new ArrayList<TransiFundTable>();
			if (fundDAO.getFunds() != null && fundDAO.getFunds().length > 0) {
				for (int i = 0; i < fundList.length; i++) {
					TransiFundTable tableRow = new TransiFundTable();
					tableRow.setFund_id(fundList[i].getFund_id());
					tableRow.setSymbol(fundList[i].getSymbol());
					double displayPrice = 0;
					System.out.println("89");
					if (fundPriceHistoryDAO.getFundPrice(fundList[i]
							.getFund_id()) != null) {
						displayPrice = (double) fundPriceHistoryDAO
								.getCurrentPrice(fundList[i].getFund_id());
					}
					System.out.println("94");
					displayPrice = displayPrice / 100;
					tableRow.setLatestPrice(String.valueOf(fundPriceHistoryDAO
							.getCurrentPrice(fundList[i].getFund_id())));
					TransiFundTable.add(tableRow);
					System.out.println("96");
				}
			}

			request.setAttribute("TransiFundTable", TransiFundTable);
			System.out.println("119");

			if (!transitionDayForm.isPresent()) {
				return "transitionDay.jsp";
			}

			System.out.println("123");

			Integer[] fund_ids = new Integer[fundList.length];
			String[] symbols = new String[fundList.length];
			String[] prices = request.getParameterValues("price");
			HashMap<Integer, String> fidPriceMap = new HashMap<Integer, String>();
			for (int i = 0; i < fund_ids.length; i++) {
				fidPriceMap.put(fund_ids[i], prices[i]);
			}

			for (int i = 0; i < prices.length; i++) {
				symbols[i] = fundList[i].getSymbol();
				System.out.println(symbols[i] + "'s new price is : "
						+ prices[i]);
			}
			String[] date = request.getParameterValues("newDate");
			Date newDate = null;

			try {

				SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
				newDate = sdf.parse(date[0]);

			} catch (ParseException | java.text.ParseException e) {

			}

			System.out.println(String.valueOf(newDate));


			// deal with pending transaction table first;
			Transaction[] pendingTransactions = transactionDAO
					.getPendingTransactions();
			pendingTransactionsHandler(pendingTransactions, fidPriceMap, newDate);
			
			// deal with worked transaction table;
			Transaction[] workedTransactions = transactionDAO.getWorkedTransactions(newDate);
			

		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "transitionDay.jsp";

	}

	public void pendingTransactionsHandler(Transaction[] pendingTransactions,
			HashMap<Integer, String> fidPriceMap, Date newDate) {

		for (int i = 0; i < pendingTransactions.length; i++) {
			// buy transaction case
			if (pendingTransactions[i].getTransaction_type().equals("BuyFund")) {
				long shares = (long) (pendingTransactions[i].getAmount() / Double
						.valueOf(fidPriceMap.get(pendingTransactions[i]
								.getFund_id())));
				try {
					transactionDAO.transactionBuyUpdate(newDate, shares,
							pendingTransactions[i]);
				} catch (RollbackException e) {
					e.printStackTrace();
				}
			}
			// sell transaction case;
			else if (pendingTransactions[i].getTransaction_type().equals(
					"SellFund")) {
				long amount = (long) (pendingTransactions[i].getShares() * Double
						.valueOf(fidPriceMap.get(pendingTransactions[i]
								.getFund_id())));
				try {

					transactionDAO.transactionSellUpdate(newDate, amount,pendingTransactions[i]);
				} catch (RollbackException e) {
					e.printStackTrace();
				}
			}
			// deposit transaction case;
			else if (pendingTransactions[i].getTransaction_type().equals(
					"DepositCheck")) {
				try {
					transactionDAO.transactionDepositUpdate(newDate,
							pendingTransactions[i]);
				} catch (RollbackException e) {
					e.printStackTrace();
				}
			}
			// check transaction case;
			else if (pendingTransactions[i].getTransaction_type().equals(
					"RequestCheck")) {
				try {
					transactionDAO.transactionCheckUpdate(newDate,
							pendingTransactions[i]);
				} catch (RollbackException e) {
					e.printStackTrace();
				}

			}
		}

	}
	
	public void workedTransactionHandler(Transaction[] workedTransactions,Date newDate) {
		
		for (int i = 0; i < workedTransactions.length; i++) {
			// buy transaction case
			if (workedTransactions[i].getTransaction_type().equals("BuyFund")) {
				
				try {
					transactionDAO.transactionBuyUpdate(newDate, shares,
							pendingTransactions[i]);
				} catch (RollbackException e) {
					e.printStackTrace();
				}

			}
			// sell transaction case;
			else if (workedTransactions[i].getTransaction_type().equals(
					"SellFund")) {
				long amount = (long) (pendingTransactions[i].getShares() * Double
						.valueOf(fidPriceMap.get(pendingTransactions[i]
								.getFund_id())));
				try {
					transactionDAO.transactionSellUpdate(newDate, amount,
							pendingTransactions[i]);
				} catch (RollbackException e) {
					e.printStackTrace();
				}

			}
			// deposit transaction case;
			else if (workedTransactions[i].getTransaction_type().equals(
					"DepositCheck")) {
				try {
					transactionDAO.transactionDepositUpdate(newDate,
							pendingTransactions[i]);
				} catch (RollbackException e) {
					e.printStackTrace();
				}
			}
			// check transaction case;
			else if (workedTransactions[i].getTransaction_type().equals(
					"RequestCheck")) {
				try {
					transactionDAO.transactionCheckUpdate(newDate,
							pendingTransactions[i]);
				} catch (RollbackException e) {
					e.printStackTrace();
				}

			}
		}
		
		
	}
	

}
