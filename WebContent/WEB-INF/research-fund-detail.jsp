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
  <h2>Research Funds</h2>
    </br>
  <h5>Funds Name: <c:out value = '${user.name}' escapeXml='true' /></h5> 
  <h5>Funds Symbol: <c:out value = '${user.symbol}' escapeXml='true' /></h5>            
  <table class="table table-bordered">
    <thead>
      <tr>
        
        <th>Date</th>
        <th>Price</th>
      </tr>
    </thead>
    <tbody>
     <c:forEach var="fundPriceHistory" items="${fundPriceHistory}">    
    	<tr> 
        <td><c:out value = '${fundPriceHistory.price_date}' escapeXml='true' /></td>
        <td><c:out value = '${fundPriceHistory.price}' escapeXml='true' /></td>
	</tr>			    
	</c:forEach>   	
    </tbody>
  </table>
  </br>

  
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