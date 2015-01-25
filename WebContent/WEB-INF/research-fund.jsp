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
  <form method="post" action="researchFundAction.do">          
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>Fund Name</th>
        <th>Fund Symbol</th>
  
      </tr>
    </thead>
    <tbody>
    <c:forEach var="fund" items="${fundList}">    
    	<tr> 
	    <td><c:out value = '${fund.name}' escapeXml='true' /></td>       
        <td><c:out value = '${fund.symbol}' escapeXml='true' /></td>
	</tr>			    
	</c:forEach>   	
    </tbody>
  </table>
  </form>

  
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