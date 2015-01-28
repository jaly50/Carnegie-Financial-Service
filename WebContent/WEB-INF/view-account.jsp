<!-- Name: Charlotte Lin -->
<!-- Date: 01/18/2015 -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page import="databeans.Customer"%>
<%@ page import="databeans.ViewAccountFund"%>

<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />

<!-- Page Content -->
<!-- This is a very simple parallax effect achieved by simple CSS 3 multiple backgrounds, made by http://twitter.com/msurguy -->

<div class="container">
<div class="container">
<div class="container">
  <h2>Account Overview</h2>
    </br>
  <h5>Personal Information:</h5>            
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>User Name</th>
        <th>Address</th>
        <th>Date of Last Trading</th>
        <th>Current Balance</th>
        <th>Available Balance</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td><c:out value = '${customer.firstname}' escapeXml='true' /></td>
        <td><c:out value = '${customer.lastname}' escapeXml='true' /></td>
        <td><c:out value = '${customer.username}' escapeXml='true' /></td>
        <td><c:out value = '${customer.addr_line1}' escapeXml='true' />
        <c:out value = '${customer.addr_line2}' escapeXml='true' /></td>
        <td><c:out value = '${date}' escapeXml='true' />
        <td> <fmt:formatNumber value="${customer.totalbalance / 100}" pattern = "#,##0.00" type="number"/></td>
        <td> <fmt:formatNumber value="${customer.availablebalance / 100}" pattern = "#,##0.00" type="number"/></td>
      </tr>
    </tbody>
  </table>
  </br>
  <h5>Funds Position:</h5>  
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>Fund Name</th>
        <th>Fund Symbol</th>
        <th>Price</th>
        <th>Shares</th>
        <th>Value</th>    
      </tr>
    </thead>
    <tbody>
      <c:forEach var="fundInfo" items="${fundInfo}">    
    	<tr> 
	    <td><c:out value = '${fundInfo.name}' escapeXml='true' /></td>
        <td><c:out value = '${fundInfo.symbol}' escapeXml='true' /></td>
        <td><c:out value = '${fundInfo.price}' escapeXml='true' /></td>
        <td><c:out value = '${fundInfo.shares}' escapeXml='true' /></td>
        <td><c:out value = '${fundInfo.value}' escapeXml='true' /></td>
	</tr>			    
	</c:forEach>   	
    </tbody>
  </table>
<h5>Instruction: For your convenience, we also show position with 0.000 shares on this page to let your know all of the position related to the account.
There are two cases may cause the 0.000 shares situation. One is the fund shares have been totally sold out. Another is the price of the fund is well 
larger than the amount of bought fund, and shares less than 0.001 will also show 0.000. For more information, please turn to the Transaction History page. Thank you.</h5>    

</div>
</div>
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
</div>
<jsp:include page="template-bottom.jsp" />