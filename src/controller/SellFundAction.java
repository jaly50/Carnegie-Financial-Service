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
		SellFundForm form;
		DecimalFormat latestPrice = new DecimalFormat("#,##0.00");
		DecimalFormat shares = new DecimalFormat("#,##0.000");


		if (session.getAttribute("user") == null) {

			return "login.do";
		}

		Customer customer = (Customer) session.getAttribute("user");
		int customer_id = customer.getCustomer_id();

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
			form = formBeanFactory.create(request);
			request.setAttribute("sellFundForm", form);
			if (!form.isPresent()) {
				return "sellFund.jsp";
			}
			
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "sellFund.jsp";
			}
            long sellShares = form.getDatabaseShares();
			Transaction transaction;
			// Create the transaction bean
			transaction = new Transaction();
			transaction.setCustomer_id(customer_id);
			transaction.setFund_id(form.getFun_id());
			transaction.setExecute_date(null);
			transaction.setTransaction_type("SellFund");
			transaction.setShares(sellShares);
			transaction.setAmount(-1);
			
			transactionDAO.create(transaction);
			Position pos;
			pos =positionDAO.read(customer_id,form.getFun_id());
			System.out.println(pos);
			long oldShares = pos.getAvailableShares();
			if (sellShares > oldShares) {
				System.out.println(sellShares +" "+oldShares);
				errors.add("You don't have enough shares ");
				return "sellFund.jsp";
			}
			pos.setAvailableShares(oldShares - sellShares);
            positionDAO.update(pos);
			
			
			sellFundTable = getSellFundTable(customer);
			request.setAttribute("sellFundTable", sellFundTable);
			

		} catch (FormBeanException e) {
			errors.add("Formbean Exception, please contact the administrator.");

		} catch (RollbackException e1) {
			errors.add(e1.getMessage());
		}
		return "sellFund.do";

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
			for (Position p : position) {

				if (p.getShares() == 0) {
					continue;
				}
				long databaseShares = p.getAvailableShares();
				double realShares = (double)databaseShares / 1000;
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
				tableRow.setAvailableShares(String.valueOf(realShares));
				tableRow.setFund_id(fund.getFund_id());
				//System.out.println("172");
				sellFundTable.add(tableRow);
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
