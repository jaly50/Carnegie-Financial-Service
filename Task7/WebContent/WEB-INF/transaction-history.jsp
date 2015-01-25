<!-- Name: Charlotte Lin -->
<!-- Date: 01/18/2015 -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="databeans.Customer"%>
<%@ page import="databeans.TransactionHistory"%>

<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />

<!-- Page Content -->
<!-- This is a very simple parallax effect achieved by simple CSS 3 multiple backgrounds, made by http://twitter.com/msurguy -->

<div class="container">
<div class="container">
<div class="container">
  <h2>${user.firstname} ${user.lastname}'s Transaction History</h2>
    </br>
  
  <table class="table table-bordered">
    <thead> 
    <tr>
        <th>Date</th>
        <th>Operation</th>
        <th>Fund Name</th>
        <th>Fund Symbol</th>
        <th>Shares</th>
        <th>Price</th>
        <th>Amount</th>
      </tr>      
    </thead>
    
    <tbody>
    	<c:forEach var="transactionInfo" items="${transactionInfo}">    
    	<tr> 
	    <td><c:out value = '${transactionInfo.date}' escapeXml='true' /></td>
        <td><c:out value = '${transactionInfo.operation}' escapeXml='true' /></td>
        <td><c:out value = '${transactionInfo.name}' escapeXml='true' /></td>
        <td><c:out value = '${transactionInfo.symbol}' escapeXml='true' /></td>
        <td><c:out value = '${transactionInfo.shares}' escapeXml='true' /></td>
        <td><c:out value = '${transactionInfo.price}' escapeXml='true' /></td>
        <td><c:out value = '${transactionInfo.amount}' escapeXml='true' /></td>
	</tr>			    
	</c:forEach>   	 
    </tbody>
  </table>
</div>
</div>
   
    <br />
    <br />
    <br />
</div>

 
<jsp:include page="template-bottom.jsp" />