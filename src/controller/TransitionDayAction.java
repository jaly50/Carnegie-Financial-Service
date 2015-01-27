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

			if (fundList == null || fundList.length == 0) {
				return "transitionDay.jsp";
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

					if (fundPriceHistoryDAO.getFundPrice(fundList[i]
							.getFund_id()) != null) {
						displayPrice = (double) fundPriceHistoryDAO
								.getCurrentPrice(fundList[i].getFund_id());
					}

					displayPrice = displayPrice / 100;
					tableRow.setLatestPrice(String.valueOf(fundPriceHistoryDAO
							.getCurrentPrice(fundList[i].getFund_id())));
					TransiFundTable.add(tableRow);

				}
			}

			request.setAttribute("TransiFundTable", TransiFundTable);
			System.out.println("113");
			if (!transitionDayForm.isPresent()) {
				return "transitionDay.jsp";
			}

			Integer[] fund_ids = new Integer[fundList.length];
			String[] symbols = new String[fundList.length];
			String[] prices = request.getParameterValues("price");

			// validate prices
			List<String> pricesErrors;
			pricesErrors = transitionDayForm.validateFundPrice(prices);

			if (!pricesErrors.isEmpty()) {
				errors.addAll(transitionDayForm.validateFundPrice(prices));
				return "transitionDay.jsp";
			}

			HashMap<Integer, String> fidPriceMap = new HashMap<Integer, String>();

			// assign symbols and fund_ids;
			for (int i = 0; i < prices.length; i++) {
				symbols[i] = fundList[i].getSymbol();
				fund_ids[i] = fundList[i].getFund_id();
			}

			for (int i = 0; i < fund_ids.length; i++) {
				fidPriceMap.put(fund_ids[i], prices[i]);

			}

			// validate ifDate exist;
			String[] date = request.getParameterValues("newDate");

			if (date == null || date.length == 0) {
				errors.add("no date here");
				return "transitionDay.jsp";
			}
			// validate Date format;
			List<String> dateErrors;
			System.out.println("date[0] is" + date[0]);
			dateErrors = transitionDayForm.validateDate(date[0]);
			if (!dateErrors.isEmpty()) {

				errors.addAll(transitionDayForm.validateDate(date[0]));
				return "transitionDay.jsp";
			}

			Date newDate = null;
			Date oldDate = null;

			try {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				newDate = sdf.parse(date[0]);

			} catch (ParseException | java.text.ParseException e) {

			}

			// validate Date time
			oldDate = fundPriceHistoryDAO.getLatestDate();
			if (oldDate != null && !newDate.after(oldDate)) {
				errors.add("New Date must be after the latest Date " + oldDate);
				return "transitionDay.jsp";
			}

			// 1.deal with Fund_Price_History table;
			fund_Price_HistoryHandler(newDate, fidPriceMap);
			// 2.deal with pending transaction table first;
			Transaction[] pendingTransactions = transactionDAO
					.getPendingTransactions();
			pendingTransactionsHandler(pendingTransactions, fidPriceMap,
					newDate);

			// 3.deal with worked transaction table;
			Transaction[] workedTransactions = transactionDAO
					.getWorkedTransactions(newDate);
			Customer[] cusUpdate = customerDAO.getCustomers();
			workedTransactionHandler(cusUpdate, workedTransactions, newDate);
			System.out.println("success");

		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "transitionDay.do";

	}

	// 1.deal with pending transactions;
	public void pendingTransactionsHandler(Transaction[] pendingTransactions,
			HashMap<Integer, String> fidPriceMap, Date newDate) {
		Transaction t;
		for (int i = 0; i < pendingTransactions.length; i++) {
			t = pendingTransactions[i];
			String type = t.getTransaction_type();
			if (type.equals("BuyFund") || type.equals("SellFund")) {
				long amount = t.getAmount();
				int fund_id = t.getFund_id();
				String getPrice = fidPriceMap.get(fund_id);
				double realPrice = Double.valueOf(getPrice);
				long databasePrice = (long) realPrice * 100;
				long shares;

				// buy transaction case
				if (type.equals("BuyFund")) {
					shares = amount / databasePrice * 1000;
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
					amount = (long) (pendingTransactions[i].getShares() * Double
							.valueOf(fidPriceMap.get(pendingTransactions[i]
									.getFund_id())));
					try {

						transactionDAO.transactionSellUpdate(newDate, amount,
								pendingTransactions[i]);
					} catch (RollbackException e) {
						e.printStackTrace();
					}
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

	// 2.deal with worked transactions;
	public void workedTransactionHandler(Customer[] cusUpdate,
			Transaction[] workedTransactions, Date newDate) {
		for (int i = 0; i < cusUpdate.length; i++) {
			long balanceIncre = 0;
			// Integer is f_id, long is shareIncrease
			HashMap<Integer, Long> shareIncreMap = new HashMap<Integer, Long>();

			for (int j = 0; j < workedTransactions.length; j++) {
				if (cusUpdate[i].getCustomer_id() == workedTransactions[j]
						.getCustomer_id()) {

					// buy transaction case, edit shares.
					if (workedTransactions[j].getTransaction_type().equals(
							"BuyFund")) {
						long tempShareIncre = 0;
						if (shareIncreMap.get(workedTransactions[j]
								.getFund_id()) != null) {
							tempShareIncre = shareIncreMap
									.get(workedTransactions[j].getFund_id());
						} else {
							tempShareIncre += workedTransactions[j].getShares();
							shareIncreMap.put(
									workedTransactions[j].getFund_id(),
									tempShareIncre);
						}

					}
					// sell transaction case; edit amount
					else if (workedTransactions[j].getTransaction_type()
							.equals("SellFund")) {
						balanceIncre += workedTransactions[j].getAmount();

					}
					// deposit transaction case;
					else if (workedTransactions[j].getTransaction_type()
							.equals("DepositCheck")) {

					}
					// check transaction case;
					else if (workedTransactions[j].getTransaction_type()
							.equals("RequestCheck")) {
					}
				}
			}
			// update blanceIncre to customer table
			try {
				System.out.print("Customer : " + cusUpdate[i].getCustomer_id());
				System.out.println("'s balanceIncre is: " + balanceIncre);
				customerDAO.transiUpdate(balanceIncre, cusUpdate[i]);
			} catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// update shareIncre to position table
			// traverse hashmap shareIncreMap
			System.out.println("326");
			int num = 1;

			for (Entry<Integer, Long> entry : shareIncreMap.entrySet()) {
				System.out.println("buy: " + num++);
				int fund_id = entry.getKey();
				long shareIncre = entry.getValue();
				System.out.println(fund_id);
				System.out.println(shareIncre);
				Position position = new Position();
				try {
					if (positionDAO.getPosition(cusUpdate[i].getCustomer_id(),
							fund_id) == null) {
						position.setFund_id(fund_id);
						position.setCustomer_id(cusUpdate[i].getCustomer_id());
						position.setShares(shareIncre);
						position.setAvailableShares(shareIncre);
						positionDAO.create(position);
					}

					else {
						position = positionDAO.getPosition(
								cusUpdate[i].getCustomer_id(), fund_id);
					}
				} catch (RollbackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					System.out.println("Position CusId: "
							+ position.getCustomer_id());
					System.out.println("Position FundId: "
							+ position.getFund_id());
					System.out.println("Position AvailShares: "
							+ position.getAvailableShares());
					System.out.println("Position shareIncre : " + shareIncre);
					positionDAO.transiUpdate(position, shareIncre);
				} catch (RollbackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					System.out.println("371");
					System.out.println(position.getAvailableShares());
					System.out.println(position.getShares());
					positionDAO.totalShareUpdate(position);
					System.out.println(position.getShares());
				} catch (RollbackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// update customer[i] totalPosition in position table
			try {
				positionDAO.updatePosition(cusUpdate[i].getCustomer_id());
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.out.println("Line 382");

			// update totalBalance in cusUpdate[i] table
			try {
				customerDAO.totalBalanceUpdate(cusUpdate[i]);
			} catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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

			try {
				fundPriceHistoryDAO.create(fund_Price_History);
			} catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public boolean compareLatestDate(Date newDate) {
		Date LatestDate = null;
		try {
			LatestDate = fundPriceHistoryDAO.getLatestDate();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (LatestDate == null) {
			return true;
		}

		if (newDate.after(LatestDate))
			return true;

		return false;

	}

}
