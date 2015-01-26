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
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>Fund Name</th>
        <th>Fund Symbol</th>
  
      </tr>
    </thead>
    <tbody>
    <c:forEach var="fundList" items="${fundList}">    
    	<tr> 
	    <td>
	    <form action="researchFundAction.do" method="POST">             		
       		<a href= "researchFundAction.do?id=${fundList.fund_id}">${fundList.name}</a>
	   </form>   
        <td><c:out value = '${fundList.symbol}' escapeXml='true' /></td>
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