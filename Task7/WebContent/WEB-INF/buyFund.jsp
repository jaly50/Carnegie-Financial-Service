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
 <h2>Mutual Funds</h2>
<br />
<h5>Funds Information:</h5>
 
<form method="post" action="buyFund.do">

 <table class="table table-bordered">
    <thead>
      <tr>
        <th>Fund Name</th>
        <th>Fund Symbol</th>
        <th>Current Price</th>
        <th>Select to Buy</th>
      </tr>
    </thead>
    <tbody>
      
     <c:forEach var="f" items="${buyFundTable}">    
      <tr>
        <td>${f.name}</td>
        <td>${f.symbol}</td>
        <td>${f.latestPrice}</td>
        <td align="center"><input type="radio" name="symbol" value="${f.symbol}"/></td>
      </tr>
	</c:forEach>
    </tbody>
    
  </table>

 <br />
 <h4 align="center">   Amount to buy: 
      <input type="text" id="buyAmount" name="buyAmount"> 
      <input class="btn btn-default" type="submit" value="Buy!" onClick="return validataForm()">
 </h4>
 
 <br />
 <br />
 <br />
</form>
</div>

  

<jsp:include page="template-bottom.jsp" />
