<!-- Name: Charlotte Lin -->
<!-- Date: 01/18/2015 -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />

<!-- Page Content -->
<!-- This is a very simple parallax effect achieved by simple CSS 3 multiple backgrounds, made by http://twitter.com/msurguy -->

<div class="container">
<div class="container">
<div class="container">
  <h2>Transaction History</h2>
    </br>

  <h5>Funds Transactions</h5>            
  <table class="table table-bordered">
    <thead> 
    <tr>
        <th>Date</th>
        <th>Fund Symbol</th>
        <th>Fund Name</th>
        <th>Operation</th>
        <th>Transaction Price</th>
        <th>Transaction Shares</th>
        <th>Amount</th>
        <th>Status</th>
      </tr>      
    </thead>
    
    <tbody>
    	<c:forEach var="transaction1" items="${transaction1}">    
    	<tr> 
	    <td><c:out value = '${transaction1.execute_date}' escapeXml='true' /></td>
        <td>000960</td>
        <td>Ebiz Mutual Fund</td>
        <td><c:out value = '${transaction1.transaction_type}' escapeXml='true' /></td>
        <td>$2.13</td>
        <td><c:out value = '${transaction1.shares}' escapeXml='true' /></td>
        <td><c:out value = '${transaction1.amount}' escapeXml='true' /></td>
        <td>Confirmed</td>
	</tr>			    
	</c:forEach>   	 
    </tbody>
  </table>
  <h5>Deposit and Check Transactions</h5>            
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>Date</th>
        <th>Operation</th>
        <th>Amount</th>
        <th>Balance</th>
        <th>Status</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="transaction1" items="${transaction2}">    
    	<tr> 
	    <td><c:out value = '${transaction2.execute_date}' escapeXml='true' /></td>
        
        <td><c:out value = '${transaction2.transaction_type}' escapeXml='true' /></td>
        <td><c:out value = '${transaction2.amount}' escapeXml='true' /></td>
        <td><c:out value = '${transaction1.shares}' escapeXml='true' /></td>
        <td><c:out value = '${transaction1.amount}' escapeXml='true' /></td>
        <td>Confirmed</td>
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