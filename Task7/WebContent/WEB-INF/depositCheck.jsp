<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />

<div class="container">
<br />
 <br />
 <br />
<form method="post" action="depositCheck.do">
<h4 style = "margin-left:380px">   Customer ID: &nbsp;&nbsp;&nbsp;&nbsp;
     <input name="customer_id" type="text" value="${form.customer_id}" />
 </h4>
 <br />
 
 <h4 style = "margin-left:380px">   Deposit Check: &nbsp;
      <input type="text" name="amount"  value="${form.amount}"/> 
 </h4>
 <br />
 <h6 align = "center">
 <input  class="btn btn-default" type="submit" value="Deposit">
 </h6>
 
 <br />
 <br />
 <br />
</form>
</div>

  

<jsp:include page="template-bottom.jsp" />
