<jsp:include page="template-top.jsp" />

<div class="container">
<br />
 <br />
 <br />
<form method="post" action="resetCustomerPassword.do">
<h4 style = "margin-left:380px">   Customer ID: &nbsp;&nbsp;&nbsp;
     <input name="customer_id" type="text" value="${form.customer_id}" />
 </h4>
 <br />
<h4 style = "margin-left:380px">   New Password: &nbsp;&nbsp;&nbsp;
     <input name="newPassword" type="text" value="${form.newPassword}" />
 </h4>
 <br />
 
 <h4 style = "margin-left:380px">   Confirm: &nbsp;&nbsp;&nbsp;
      <input type="text" name="confirm"  value="${form.confirm}"/> 
 </h4>
 <br />
 <h6 align = "center">
 <input align = "center" class="btn btn-default" type="submit" value="Create">
 </h6>
 
 <br />
 <br />
 <br />
</form>
</div>

  

<jsp:include page="template-bottom.jsp" />
