<jsp:include page="template-top.jsp" />


<jsp:include page="error-list.jsp" />


<div class="container">
<form method="post" action="login.do">
	<h4 style = "margin-left:380px"> Username:
			<input type="text" name="username" value="${form.username}" />
  </h4>
   <br />
	<h4 style = "margin-left:380px">   Password:
		<input type="password" name="password" value="" />
	</h4>
 <br />
		<h4 style = "margin-left:380px"><input type="radio" name="userType" value="Customer">Customer&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
<input type="radio" name="userType" value="Employee">Employee
  </h4>
		<br />
<h4  style = "margin-left:380px"><input class="btn btn-default" type="submit"
				name="button" value="Login" />
		  </h4>
</form>
</div>

<jsp:include page="template-bottom.jsp" />