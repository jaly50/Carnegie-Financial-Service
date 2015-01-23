<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />
<script type="text/javascript">	
</script>


<div class="container">
<br />
 <br />
 <br />
 <h2>My Fund Position</h2>
<br />
<h5>Funds position:</h5>
 
<form method="post" action="sellFund.do">

 <table class="table table-bordered">
    <thead>
      <tr>
        <th>Fund Name</th>
        <th>Fund Symbol</th>
        <th>Available Shares</th>
        <th>Select to Sell</th>
      </tr>
    </thead>
    <tbody>
      
     <c:forEach var="f" items="${sellFundTable}">    
      <tr>
        <td>${f.fundName }</td>
        <td>${f.symbol }</td>
        <td>${f.availableShares}</td>
        <td align="center"><input type="radio" name="symbol" value="${f.symbol}"/></td>
      </tr>
	</c:forEach>
    </tbody>
    
  </table>

 <br />
 <h4 align="center">  Shares to Sell: 
      <input type="text" id="shares" name="shares"> 
      <input class="btn btn-default" type="submit" value="Sell!">
 </h4>
 
 <br />
 <br />
 <br />
</form>
</div>

  

<jsp:include page="template-bottom.jsp" />
