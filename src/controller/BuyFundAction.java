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
import databeans.Position;
import databeans.Transaction;
import customerFormbeans.BuyFundForm;

public class BuyFundAction extends Action {
	private FormBeanFactory<BuyFundForm> formBeanFactory = FormBeanFactory
			.getInstance(BuyFundForm.class);
	static DecimalFormat displayMoney = new DecimalFormat("$#,##0.00"); 
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	private TransactionDAO transactionDAO;
	private Fund_Price_HistoryDAO fundPriceHistoryDAO;
	private PositionDAO positionDAO;

	public BuyFundAction(Model model) {
		this.fundDAO = model.getFundDAO();
		this.transactionDAO = model.getTransactionDAO();
		this.customerDAO = model.getCustomerDAO();
		this.fundPriceHistoryDAO = model.getFund_Price_HistoryDAO();
		this.positionDAO = model.getPositionDAO();
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
			int customer_id = customer.getCustomer_id();
			System.out.println(customer.getCustomer_id());
			BuyFundForm form = formBeanFactory.create(request);
			request.setAttribute("buyFundForm", form);

			// get fund name;
			Fund[] fundList = fundDAO.getFunds();
			if (fundList==null || fundList.length == 0) {
				return "buyFund.jsp";
			}

			// fundTable to show funds information
			List<BuyFundTable> buyFundTable = new ArrayList<BuyFundTable>();
			int num = 0;
			System.out.print(num++);
			// add fund table rows
			Fund fund;
			int fund_id;
			if (fundDAO.getFunds() != null && fundDAO.getFunds().length > 0) {
				for (int i = 0; i < fundList.length; i++) {
					fund = fundList[i];
					fund_id = fund.getFund_id();
					BuyFundTable tableRow = new BuyFundTable();
					tableRow.setName(fund.getName());
					tableRow.setSymbol(fund.getSymbol());
					tableRow.setFund_id(fund_id);
					double displayPrice = 0;
					if (fundPriceHistoryDAO.getFundPrice(fund_id) == null) {
						tableRow.setLatestPrice("N/A");
					}
					else {
						displayPrice = (double) fundPriceHistoryDAO
								.getCurrentPrice(fundList[i].getFund_id());
				
					displayPrice = displayPrice / 100;
					tableRow.setLatestPrice(displayMoney.format(displayPrice));
					}
					buyFundTable.add(tableRow);
				}
			}
		
			request.setAttribute("buyFundTable", buyFundTable);
			
			if (!form.isPresent()) {
				return "buyFund.jsp";
			}

			
			errors.addAll(form.getValidationErrors());
            if (errors.size() >0) {
            	return "buyFund.jsp";
            }
			
            // update customer available balance in database
			 long oldBalance = customer.getAvailablebalance();
			 long databaseAmount = form.getDatabaseAmount();
			 if (oldBalance < databaseAmount) {
				 errors.add("Your available balance is not enough");
					return "buyFund.jsp"; 
			 }
			 customer.setAvailablebalance(oldBalance - databaseAmount);
			customerDAO.update(customer);
             
			//update transaction in database
			Transaction transaction = new Transaction();
			transaction.setAmount(databaseAmount);
			transaction.setCustomer_id(customer_id);
			transaction.setFund_id(form.getFund_id());
			transaction.setExecute_date(null);
			transaction.setShares(-1);
			transaction.setTransaction_type("BuyFund");
			transactionDAO.create(transaction);
            
			System.out.println("Transaction created successfully");
			Position pos;
			pos = positionDAO.getPosition(customer_id, form.getFund_id());
			// No such position exists before
			if (pos==null) {
				pos = new Position();
				pos.setAvailableShares(0);
				pos.setCustomer_id(customer_id);
				pos.setFund_id(form.getFund_id());
			}
            positionDAO.update(pos);

			return "buyFund.jsp";

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			errors.add("Roll backException.");
			return "buyFund.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			errors.add("FormBean Exception.");
			return "buyFund.jsp";
		}
	}
}
