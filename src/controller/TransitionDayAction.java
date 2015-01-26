package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
import databeans.Fund_Price_History;
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
			
			if (fundList == null || fundList.length ==0) {
				
			}
			
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
			// validate prices;
			
			HashMap<Integer, String> fidPriceMap = new HashMap<Integer, String>();
			
			// assign symbols and fund_ids;
			for (int i = 0; i < prices.length; i++) {
				symbols[i] = fundList[i].getSymbol();
				fund_ids[i] = fundList[i].getFund_id();
			}
			/*
			for (Entry<Integer, String> entry : fidPriceMap.entrySet()) {
				int fund_id = entry.getKey();
				String price = entry.getValue();
				Position position = null;
				System.out.println(fund_id + "'s price is " + price);

			}*/
			
			for (int i = 0; i < fund_ids.length; i++) {
				System.out.print("No" + i + "  ");
				System.out.print("fund name: " + fund_ids[i] + "  ");
				System.out.println("fund price: " + prices[i]);
				fidPriceMap.put(fund_ids[i], prices[i]);
				System.out.println("hash" + i);
			}
			

			String[] date = request.getParameterValues("newDate");
			if (date==null || date.length <1) {
				errors.add("no date here");
				return "transitionDay.jsp";
			}
			List<String> dateErrors;
			dateErrors = transitionDayForm.validateDate(date[0]);
			if (dateErrors!=null)
			// validate Date format;
			errors.addAll(transitionDayForm.validateDate(date[0]));
			
			Date newDate = null;
			System.out.println("152 " + date[0]);
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
				newDate = sdf.parse(date[0]);

			} catch (ParseException | java.text.ParseException e) {

			}

			System.out.println("162 " + newDate);

			// validate Date time
			/*if (!compareLatestDate(newDate)) {
				errors.add("New Date must be after the latest Date");
				return "transitionDay.jsp";
			}*/

			// deal with Fund_Price_History table;
			System.out.println("185");
			fund_Price_HistoryHandler(newDate, fidPriceMap);

			// deal with pending transaction table first;
			Transaction[] pendingTransactions = transactionDAO
					.getPendingTransactions();
			pendingTransactionsHandler(pendingTransactions, fidPriceMap,
					newDate);

			// deal with worked transaction table;
			Transaction[] workedTransactions = transactionDAO
					.getWorkedTransactions(newDate);
			Customer[] cusUpdate = customerDAO.getCustomers();
			workedTransactionHandler(cusUpdate, workedTransactions, newDate);

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

					transactionDAO.transactionSellUpdate(newDate, amount,
							pendingTransactions[i]);
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

	public void workedTransactionHandler(Customer[] cusUpdate,
			Transaction[] workedTransactions, Date newDate) {
		for (int i = 0; i < cusUpdate.length; i++) {
			long balanceIncre = 0;
			HashMap<Integer, Long> shareIncreMap = new HashMap<Integer, Long>();
			for (int j = 0; j < workedTransactions.length; j++) {
				if (cusUpdate[i].getCustomer_id() == workedTransactions[i]
						.getCustomer_id()) {

					// buy transaction case, edit shares.
					if (workedTransactions[j].getTransaction_type().equals(
							"BuyFund")) {
						long tempShareIncre = shareIncreMap
								.get(workedTransactions[j].getFund_id());
						tempShareIncre += workedTransactions[j].getShares();
						shareIncreMap.put(workedTransactions[j].getFund_id(),
								tempShareIncre);

					}
					// sell transaction case; edit amount
					else if (workedTransactions[i].getTransaction_type()
							.equals("SellFund")) {
						balanceIncre += workedTransactions[j].getAmount();

					}
					// deposit transaction case;
					else if (workedTransactions[i].getTransaction_type()
							.equals("DepositCheck")) {

					}
					// check transaction case;
					else if (workedTransactions[i].getTransaction_type()
							.equals("RequestCheck")) {
					}
				}
			}
			// update blanceIncre to customer table
			try {
				customerDAO.transiUpdate(balanceIncre, cusUpdate[i]);
			} catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// update shareIncre to position table
			// traverse hashmap shareIncreMap
			for (Entry<Integer, Long> entry : shareIncreMap.entrySet()) {
				int fund_id = entry.getKey();
				long shareIncre = entry.getValue();
				Position position = null;
				try {
					position = positionDAO.getPosition(
							cusUpdate[i].getCustomer_id(), fund_id);
				} catch (RollbackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					positionDAO.transiUpdate(position, shareIncre);
				} catch (RollbackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					positionDAO.totalShareUpdate(position);
				} catch (RollbackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			cusUpdate[i].setTotalbalance(cusUpdate[i].getAvailablebalance());

		}

	}

	@SuppressWarnings("null")
	public void fund_Price_HistoryHandler(Date newDate,
			HashMap<Integer, String> fidPriceMap) {
			int fund_id = 0;
			Long fundPrice = (long) 0;
		for (Entry<Integer, String> entry : fidPriceMap.entrySet()) {
			fund_id = entry.getKey();
			fundPrice = Long.parseLong(entry.getValue());
			Fund_Price_History fund_Price_History = new Fund_Price_History();
			fund_Price_History.setFund_id(fund_id);
			fund_Price_History.setPrice(fundPrice * 100);
			fund_Price_History.setPrice_date(newDate);
			System.out.println(fund_Price_History.getFund_id());
			System.out.println(fund_Price_History.getPrice());
			System.out.println(fund_Price_History.getPrice_date());
			try {
				fundPriceHistoryDAO.create(fund_Price_History);
			} catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public boolean compareLatestDate(Date newDate) {
		String tempLatestDate = null;
		Date LatestDate = null;
		try {
			tempLatestDate = fundPriceHistoryDAO.getLatestDate();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (tempLatestDate == null)
			return true;

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
			LatestDate = sdf.parse(tempLatestDate);

		} catch (ParseException | java.text.ParseException e) {

		}

		if (newDate.after(LatestDate))
			return true;

		return false;

	}

}
