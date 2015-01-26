<!-- Name: Charlotte Lin -->
<!-- Date: 01/18/2015 -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        <th>Available Balance</th>
        <th>Total Balance</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td><c:out value = '${user.firstname}' escapeXml='true' /></td>
        <td><c:out value = '${user.lastname}' escapeXml='true' /></td>
        <td><c:out value = '${user.username}' escapeXml='true' /></td>
        <td><c:out value = '${user.addr_line1}${user.addr_line2}${user.zip}${user.state}' escapeXml='true' /></td>
        <td><c:out value = '${date}' escapeXml='true' /></td>
        <td><c:out value = '${user.availablebalance}' escapeXml='true' /></td>
        <td><c:out value = '${user.totalbalance}' escapeXml='true' /></td>
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