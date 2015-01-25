/*
 * ian
 * jan 23 2015
 */
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
import databeans.Transaction;
import customerFormbeans.BuyFundForm;

public class BuyFundAction extends Action {
	private FormBeanFactory<BuyFundForm> formBeanFactory = FormBeanFactory
			.getInstance(BuyFundForm.class);

	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	private TransactionDAO transactionDAO;
	private Fund_Price_HistoryDAO fundPriceHistoryDAO;

	public BuyFundAction(Model model) {
		this.fundDAO = model.getFundDAO();
		this.transactionDAO = model.getTransactionDAO();
		this.customerDAO = model.getCustomerDAO();
		this.fundPriceHistoryDAO = model.getFund_Price_HistoryDAO();
	}

	public String getName() {
		return "buyFund.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		HttpSession session = request.getSession();

		try {

			if (request.getSession().getAttribute("user") == null) {
				errors.add("Please Log in first");
				return "login.do";
			}

			Customer customer = (Customer) session.getAttribute("user");
			System.out.println(customer.getCustomer_id());
			BuyFundForm buyFundForm = formBeanFactory.create(request);
			request.setAttribute("buyFundForm", buyFundForm);

			// get fund name;
			Fund[] fundList = fundDAO.getFunds();
			for (Fund f : fundList) {
				System.out.print(f.getName() + " ");
			}

			// fundTable to show funds information
			List<BuyFundTable> buyFundTable = new ArrayList<BuyFundTable>();
			int num = 0;
			System.out.print(num++);
			// add fund table rows
			if (fundDAO.getFunds() != null && fundDAO.getFunds().length > 0) {
				for (int i = 0; i < fundList.length; i++) {
					BuyFundTable tableRow = new BuyFundTable();
					tableRow.setName(fundList[i].getName());
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
					buyFundTable.add(tableRow);
					System.out.println("96");
				}
			}
			System.out.println("101");
			request.setAttribute("buyFundTable", buyFundTable);
			System.out.println("103");
			if (!buyFundForm.isPresent()) {
				return "buyFund.jsp";
			}
			System.out.println("105");
			
			errors.addAll(buyFundForm.getValidationErrors());

			/*
			if (buyFundForm.getAmountAsDouble() == -1.0) {
				errors.add("Please input buy amount");
				return "buyFund.jsp";
			}
			*/
			if(buyFundForm.getSymbol()== null || (buyFundForm.getSymbol().length() == 0 ))
					{
				errors.add("ftff");
				return "buyFund.jsp";
					}
			
			if (buyFundForm.getAmountAsDouble() > customer
					.getAvailablebalance()) {
				errors.add("Your available balance is not enough");
				return "buyFund.jsp";
			}

			customerDAO.update((long) buyFundForm.getAmountAsDouble()*100, customer);

			Transaction transaction = new Transaction();
			transaction.setAmount((long)(buyFundForm.getAmountAsDouble()*100));
			transaction.setCustomer_id(customer.getCustomer_id());
			transaction.setFund_id(fundDAO.getFund_ID(buyFundForm.getSymbol()));
			transaction.setExecute_date(null);
			transaction.setShares(0);
			transaction.setTransaction_type("buy");
			System.out.println("113");

			transactionDAO.create(transaction);

			return "buyFund.do";

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		}
	}
}
