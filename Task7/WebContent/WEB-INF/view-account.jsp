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
        <th>Data of Last Trading</th>
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
      <tr>
        <td>Ebiz Mutual Fund</td>
        <td>000960</td>
        <td>$6.66</td>
        <td>100</td>
        <td>$666</td>

      </tr>
      <tr>
        <td>Dow Jones Index Fund</td>
        <td>600000</td>
        <td>$2.10</td>
        <td>300</td>
        <td>$630</td>
      </tr>
      <tr>
        <td>Pimco Total Return</td>
        <td>630720</td>
        <td>$10.20</td>
        <td>150</td>
        <td>$1309.5</td>
      </tr>
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