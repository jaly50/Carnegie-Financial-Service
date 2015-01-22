<jsp:include page="template-top.jsp" />
<jsp:include page="error-list.jsp" />
<p style="font-size: medium">To register, enter the following
	information. (All fields required.)</p>

<p>
<form method="post">
	<input type="hidden" name="redirect" value="${redirect}" />
	<table>
	   
		<tr> 
			<td><h4 style = "margin-left:380px">Username:</td>
			<td><input type="text" name="username" value="${form.username}" /></td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">First Name:</td>
			<td><input type="text" name="firstName"
				value="${form.firstName}" /></td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">Last Name:</td>
			<td><input type="text" name="lastName" value="${form.lastName}" /></td>
		</tr>

		<tr>
			<td><h4 style = "margin-left:380px">Password:</td>
			<td><input type="password" name="password" value="" /></td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">Confirm Password:</td>
			<td><input type="password" name="confirm" value="" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><h6 align = "center">
 <input align = "center" class="btn btn-default" type="submit" name="button" value="Register" /></td>
		</h6>
		</tr>
	</table>
</form>
</p>

<jsp:include page="template-bottom.jsp" />

