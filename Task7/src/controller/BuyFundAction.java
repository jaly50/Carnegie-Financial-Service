package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import databeans.Transaction;
import customerFormbeans.BuyFundForm;

public class BuyFundAction extends Action {
	private FormBeanFactory<BuyFundForm> formBeanFactory = FormBeanFactory
			.getInstance(BuyFundForm.class);

	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	private TransactionDAO transactionDAO;
	private Fund_Price_HistoryDAO fundPriceHisotryDAO;

	public BuyFundAction(Model model) {
		this.fundDAO = model.getFundDAO();
		this.transactionDAO = model.getTransactionDAO();
		this.customerDAO = model.getCustomerDAO();
		this.fundPriceHisotryDAO = model.getFund_Price_HistoryDAO();
	}

	public String getName() {
		return "buyFund.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
	


		DecimalFormat latestPrice = new DecimalFormat("#,##0.00");
		DecimalFormat shares = new DecimalFormat("#,##0.000");

		try {
			if (request.getSession().getAttribute("user") == null) {
				errors.add("Please Log in first");
				return "login.jsp";
			}

			Customer customer = customerDAO.getCustomer(((Customer) request
					.getSession().getAttribute("customer")).getUsername());
			BuyFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			// get fund name;
			Fund[] fundList = fundDAO.getFunds();

			// fundTable to show funds information
			List<BuyFundTable> fundTable = new ArrayList<BuyFundTable>();
			// add fund table rows
			if (fundDAO.getFunds() != null && fundDAO.getFunds().length > 0) {
				for (int i = 0; i < fundList.length; i++) {
					BuyFundTable tableRow = new BuyFundTable();
					tableRow.setName(fundList[i].getName());
					tableRow.setSymbol(fundList[i].getSymbol());
					double displayPrice = 0;
					if (fundPriceHisotryDAO.getLatestFundPrice(fundList[i]
							.getSymbol()) != null) {
						displayPrice = fundPriceHisotryDAO.getLatestFundPrice(
								fundList[i].getSymbol()).getPrice();
					}
					displayPrice = displayPrice / 100;
					tableRow.setLatestPrice(latestPrice.format(displayPrice));
					fundTable.add(tableRow);
				}
			}

			request.setAttribute("fundTable", fundTable);

			Transaction transaction = new Transaction();
			transaction.setCustomer_id(customer.getCustomer_id());
			transaction.setFund_id(form.getFund_id());
			transaction.setExecute_date(null);
			transaction.setShares(0);
			transaction.setTransaction_type("buy");
			transaction.setAmount(form.getAmountAsDouble());

			transactionDAO.create(transaction);

			return "transactionHistory.do";

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		}
	}

}
