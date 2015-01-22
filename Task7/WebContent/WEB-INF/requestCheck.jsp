<jsp:include page="template-top.jsp" />

<div class="container">
<br />
 <br />
 <br />
<form method="post" action="requestCheck.do">
<h4 style = "margin-left:380px">   Account Name: &nbsp;&nbsp;&nbsp;&nbsp;
     <span class="menu-head">${user.firstname}&ensp;${user.lastname}</span>
 </h4>
 <br />
 
  <h4 style = "margin-left:380px">   Available Balance: &nbsp;
      <span class="menu-head">${user.availablebalance}&ensp;</span>
 </h4>
 <br />
 
 <h4 style = "margin-left:380px">   Request Amount: &nbsp;
      <input type="text" name="amount"  value="${form.amount}"/> 
 </h4>
 <br />
 <h6 align = "center">
 <input  class="btn btn-default" type="submit" value="Submit">
 </h6>
 
 <br />
 <br />
 <br />
</form>
</div>

  

<jsp:include page="template-bottom.jsp" />
