package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.FundDAO;
import model.Fund_Price_HistoryDAO;
import model.PositionDAO;
import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.BuyFundTable;
import databeans.Customer;
import databeans.Fund;
import databeans.Position;
import databeans.SellFundTable;
import databeans.Transaction;
import customerFormbeans.BuyFundForm;
import customerFormbeans.SellFundForm;

public class SellFundAction extends Action {
	private FormBeanFactory<SellFundForm> formBeanFactory = FormBeanFactory
			.getInstance(SellFundForm.class);

	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;
	private Fund_Price_HistoryDAO fundPriceHistoryDAO;

	public SellFundAction(Model model) {
		this.customerDAO = model.getCustomerDAO();
		this.fundDAO = model.getFundDAO();
		this.positionDAO = model.getPositionDAO();
		this.transactionDAO = model.getTransactionDAO();
		this.fundPriceHistoryDAO = model.getFund_Price_HistoryDAO();
	}

	public String getName() {
		return "sellFund.do";
	}

	// get customer's sale fund table

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		System.out.println("SellFund Action get performed");

		HttpSession session = request.getSession();
		SellFundForm sellFundForm;
		DecimalFormat latestPrice = new DecimalFormat("#,##0.00");
		DecimalFormat shares = new DecimalFormat("#,##0.000");


		if (session.getAttribute("user") == null) {

			return "login.do";
		}

		Customer customer = (Customer) session.getAttribute("user");

		// get sell fund table
		ArrayList<SellFundTable> sellFundTable = getSellFundTable(customer);
		System.out.println("75");
		request.setAttribute("sellFundTable", sellFundTable);
		if (sellFundTable.size() == 0) {
			return "sellFund.jsp";
		}
		System.out.println("79");
		try {
			// button
			sellFundForm = formBeanFactory.create(request);
			request.setAttribute("sellFundForm", sellFundForm);
			System.out.println("82");
			if (!sellFundForm.isPresent()) {
				return "sellFund.jsp";
			}
			System.out.println("86");
			errors.addAll(sellFundForm.getValidationErrors());
			if (errors.size() != 0) {
				return "sellFund.jsp";
			}
			System.out.println("91");
			double doubleSellShares = Double.valueOf(sellFundForm.getShares());
			System.out.println("96");
			System.out.println(customer.getCustomer_id());
			System.out.println(sellFundForm.getSymbol());
			
			Position pos = positionDAO.getCustomerShares(
					customer.getCustomer_id(), fundDAO.getFund(sellFundForm.getSymbol()).getFund_id());
			System.out.println(pos.getAvailableShares());
			double availableShares = Double.valueOf(pos.getAvailableShares());
			System.out.println("101");
			if (availableShares < doubleSellShares) {
				return "sellFund.jsp";
			} else {
				long newAvailableShares = (long) (availableShares - doubleSellShares);
				pos.setAvailableShares(newAvailableShares);

			}
			if(sellFundForm.getSymbol() == null) {
				errors.add("Please select one fund");
				return "buyFund.jsp";
			}
			
			if(sellFundForm.getShares() == null) {
				errors.add("Please input sell shares");
				return "buyFund.jsp";
			}
			System.out.println(pos.getCustomer_id());
			System.out.println(pos.getFund_id());
			System.out.println(pos.getAvailableShares());
			System.out.println(pos.getShares());
			// update position table
			positionDAO.update((long)doubleSellShares, pos);
			System.out.println("106");
			// insert into transaction table
			Transaction transaction = new Transaction();
			transaction.setCustomer_id(customer.getCustomer_id());
			transaction.setFund_id(pos.getFund_id());
			transaction.setExecute_date(null);
			transaction.setShares(sellFundForm.getShares());
<<<<<<< HEAD
			transaction.setTransaction_type("sell");
=======
			transaction.setTransaction_type("SellFund");
>>>>>>> 6e1539b78ee53ced185ef9d67ea8d1e2aa1c063f
			transaction.setAmount(0);
			try {
				transactionDAO.create(transaction);
			} catch (RollbackException e) {
				e.printStackTrace();
				return "sellFund.jsp";
			}
			
			sellFundTable = getSellFundTable(customer);
			request.setAttribute("sellFundTable", sellFundTable);
			return "buyFund.do";

		} catch (FormBeanException e) {
			return "error.jsp";

		} catch (RollbackException e1) {
			return "error.jsp";
		}

	}

	private ArrayList<SellFundTable> getSellFundTable(Customer customer) {
		ArrayList<SellFundTable> sellFundTable = new ArrayList<SellFundTable>();
		try {
			// get positions;

			Position[] position = positionDAO.getPositions(customer
					.getCustomer_id());
			if (position == null) {

				return sellFundTable;
			}
			//System.out.println(position[1].getAvailableShares());
			//System.out.println(position[0].getAvailableShares());
			for (Position p : position) {

				if (p.getShares() == 0) {
					continue;
				}
				//System.out.println("Position Share:" + p.getShares());
				Fund fund = fundDAO.getFund(p.getFund_id());
				//System.out.println("Fund id: " + fund.getFund_id());
				long priceTemp = fundPriceHistoryDAO.getCurrentPrice(fund.getFund_id());
				//System.out.println("PriceTemp: " + priceTemp);
				//System.out.println("marketValueTemp: " + marketValueTemp);
				Double priceShow = (double) (priceTemp / 100);

				SellFundTable tableRow = new SellFundTable();
				//System.out.println("165");
				tableRow.setFundName(fund.getName());
				tableRow.setSymbol(fund.getSymbol());
				tableRow.setLatestPrice(priceShow.toString());
				tableRow.setAvailableShares(String.valueOf(p.getAvailableShares()));
				//System.out.println("172");
				sellFundTable.add(tableRow);
				//System.out.println("Num: " + num);
			}
			//System.out.println("Num: " + num);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			//System.out.println("179");
			e.printStackTrace();
		}
		//System.out.println("181");
		return sellFundTable;
	}

}
