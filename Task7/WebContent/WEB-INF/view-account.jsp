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
  <h2>Account Overview</h2>
    </br>
  <h5>Personal Information:</h5>            
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>Firstname</th>
        <th>Lastname</th>
        <th>UserName</th>
        <th>Address</th>
        <th>State</th>
        <th>City</th>
        <th>Zip Code</th>
        <th>Date of Last Trading</th>
        <th>Cash Balance</th>

      </tr>
    </thead>
    <tbody>
      <tr>
        <td><c:out value = '${customer.firstname}' escapeXml='true' /></td>
        <td><c:out value = '${customer.lastname}' escapeXml='true' /></td>
        <td><c:out value = '${customer.addr_line1}' escapeXml='true' /></td>
        <td><c:out value = '${customer.addr_line1}' escapeXml='true' /></td>
        <td><c:out value = '${customer.addr_line2}' escapeXml='true' /></td>
        <td><c:out value = '${customer.addr_line2}' escapeXml='true' /></td>
        <td><c:out value = '${customer.zip}' escapeXml='true' /></td>
        <td><c:out value = '${customer.availablebalance}' escapeXml='true' /></td>
        <td><c:out value = '${customer.totalbalance}' escapeXml='true' /></td>
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
        <th>Total Value</th>
        
      </tr>
    </thead>
    <tbody>
      <c:forEach var="fund" items="${funds}">    
    	<tr> 
	    <td><c:out value = '${fund.name}' escapeXml='true' /></td>
        <td><c:out value = '${fund.symbol}' escapeXml='true' /></td>
        <td><c:out value = '${fund.name}' escapeXml='true' /></td>
        <td><c:out value = '${fund.name}' escapeXml='true' /></td>
        <td><c:out value = '${fund.name}' escapeXml='true' /></td>
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