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
			<td><h4 style = "margin-left:380px">Addressline 1</td>
			<td><input type="text" name="addr_line1" value="${form.addr_line1}" /></td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">Addressline 2</td>
			<td><input type="text" name="addr_line2"
				value="${form.addr_line2}" /></td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">City:</td>
			<td><input type="text" name="city" value="${form.city}" /></td>
		</tr>
		
		<tr>
			<td><h4 style = "margin-left:380px">State:</td>
			<td><input type="text" name="state" value="${form.state}" /></td>
		</tr>

		<tr>
			<td><h4 style = "margin-left:380px">Zip:</td>
			<td><input type="number" name="zip" value="${form.zip}" /></td>
		</tr>
         <tr>
			<td><h4 style = "margin-left:380px">Total Balance:</td>
			<td><input type="text" name="totalbalance" value="${form.totalbalance}" /></td>
		</tr>

		<tr>
			<td><h4 style = "margin-left:380px">Available Balance:</td>
			<td><input type="text" name="availablebalance" value="${form.availablebalance}" /></td>
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
			<td colspan="2" align="center"> <h6 align = "center">
			<input align="center" type="submit" class="btn btn-default"	name="button" value="Register" /></td>
	   </h6>
		</tr>
	</table>
</form>
</p>

<jsp:include page="template-bottom.jsp" />

