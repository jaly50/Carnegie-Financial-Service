<jsp:include page="template-top.jsp" />
<jsp:include page="error-list.jsp" />

<p>
<div class="container">

<form method="post">
	<input type="hidden" name="redirect" value="${redirect}" />
	<table>
	
		<tr>
			<td><h4 style = "margin-left:380px">Username:<span style="color:red;">*</span></td>
			<td><input type="text" name="username" value="${form.username}" /></td>
		</tr>
		<br>
		<tr>
			<td><h4 style = "margin-left:380px">First Name:<span style="color:red;">*</span></td>
			<td><input type="text" name="firstName"
				value="${form.firstName}" /></td>
		</tr>
		<br>
		<tr>
			<td><h4 style = "margin-left:380px">Last Name:<span style="color:red;">*</span></td>
			<td><input type="text" name="lastName" value="${form.lastName}" /></td>
		</tr>
		<br>
		<tr>
			<td><h4 style = "margin-left:380px">Addressline 1<span style="color:red;">*</span></td>
			<td><input type="text" name="addr_line1" value="${form.addr_line1}" /></td>
		</tr>
		<br>
		<tr>
			<td><h4 style = "margin-left:380px">Addressline 2</td>
			<td><input type="text" name="addr_line2"
				value="${form.addr_line2}" /></td>
		</tr>
		<br>
		<tr>
			<td><h4 style = "margin-left:380px">City:<span style="color:red;">*</span></td>
			<td><input type="text" name="city" value="${form.city}" /></td>
		</tr>
		<br>
		<tr>
			<td><h4 style = "margin-left:380px">State:<span style="color:red;">*</span></td>
			<td><input type="text" name="state" value="${form.state}" /></td>
		</tr>
<br>
		<tr>
			<td><h4 style = "margin-left:380px">Zip:<span style="color:red;">*</span></td>
			<td><input type="text" name="zip" value="${form.zip}" /></td>
		</tr>
    <br>    		
        <tr>
			<td><h4 style = "margin-left:380px">Cash:<span style="color:red;">*</span></td>
			<td><input type="text" name="cash" value="${form.cash}" /></td>
		</tr>
        <br>
		<tr>
			<td><h4 style = "margin-left:380px">Password:<span style="color:red;">*</span></td>
			<td><input type="password" name="password" value="" placeholder="6 to 15 characters long"/></td>
		</tr>
		<br>
		<tr>
			<td><h4 style = "margin-left:380px">Confirm Password:<span style="color:red;">*</span></td>
			<td><input type="password" name="confirm" value="" /></td>
		</tr>
		<br>
		<tr>
			<td colspan="2" align="center"> <h6 align = "center" style = "margin-left:380px">
			<input align="center" type="submit" class="btn btn-default"	name="button" value="Register" /></td>
	   </h6>
		</tr>
		<tr>
		<td><h6 style = "margin-left:380px"><span style="color:red;">*</span> indicates required</td>
		</tr>
		
	</table>
</form>
</p>
</div>
<jsp:include page="template-bottom.jsp" />

