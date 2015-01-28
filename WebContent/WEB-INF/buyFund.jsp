<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />
<jsp:include page="message.jsp" />
<script type="text/javascript">	


function verification() {
	var text = document.getElementById("buyAmount").value;
	  if (text == '') {
	     alert("Please Input Amount to buy");
	     return false;
	  }
 	
  
  return true;
}

</script>



<div class="container">
<h2>Buy Funds</h2>

<br />

 
<br />
<h5>Funds Information:</h5>
 
<form method="post" action="buyFund.do">

 <table class="table table-bordered">
    <thead>
      <tr>
        <th>Fund Name</th>
        <th>Fund Symbol</th>
        <th>Last Transition Day Price</th>
        <th>Select to Buy</th>
      </tr>
    </thead>
    <tbody>
      
     <c:forEach var="f" items="${buyFundTable}">    
      <tr>
        <td>${f.name}</td>
        <td>${f.symbol}</td>
         <td align = "right">${f.latestPrice}</td>
        <td align="center"><input type="radio" name="select" value="${f.fund_id}"/></td>
      </tr>
	</c:forEach>
    </tbody>
    
  </table>
  
<h4 style = "margin-left:380px"> Available Balance:&nbsp;
      <span class="menu-head"><fmt:formatNumber type="number" value="${user.availablebalance/100}" minFractionDigits="2" maxFractionDigits="2"/></span></h4>
	
 <br />
 <h4 align="center">   Amount to buy: 
      <input type="text" id="buyAmount" name="buyAmount" placeholder="1-1000000"
      style="ime-mode:disabled" onkeypress="return myfilter(event)"
      /> 
      <input class="btn btn-default" type="submit" value="Buy!" onClick="return verification()">
      
 </h4>
 
 <br />
 <br />
 <br />
</form>
</div>

  

<jsp:include page="template-bottom.jsp" />
