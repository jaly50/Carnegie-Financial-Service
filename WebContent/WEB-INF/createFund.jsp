<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />

<div class="container">
<br />
 <br />
 <br />
<form method="post" action="createFund.do">
<h4 style = "margin-left:380px">   Fund Name: &nbsp;&nbsp;&nbsp;&nbsp;
     <input name="name" style="width:300px;" maxlength="30" type="text" placeholder="Only Alphanumeric Characters" value="${form.name}" />
 </h4>
 <br />
 
 <h4 style = "margin-left:380px">   Fund Symbol: &nbsp;
      <input type="text" name="symbol"style="width:300px;"  placeholder="Only Capital Letters"  value="${form.symbol}"/> 
 </h4>
 <br />
  <br />
 <h4 align = "center">
 <input  class="btn btn-default" type="submit" value="Create">
 </h4>
 
 <br />
 <br />
 <br />
</form>
</div>

  

<jsp:include page="template-bottom.jsp" />
