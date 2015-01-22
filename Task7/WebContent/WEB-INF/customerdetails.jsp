
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="template-top.jsp" />


<table>

        <h4 style = "margin-left:380px">	
		<tr>
			<td><h4 style = "margin-left:380px">${customerdetails.customer_id}</td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">${customerdetails.firstname}</td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">${customerdetails.lastname}</td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">${customerdetails.addr_line1}</td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">${customerdetails.addr_line2}</td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">${customerdetails.city}</td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">${customerdetails.state}</td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">${customerdetails.zip}</td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">${customerdetails.availablebalance}</td>
		</tr>
		<tr>
			<td><h4 style = "margin-left:380px">${customerdetails.totalbalance}</td>
		</tr>
		
</table>

<jsp:include page="template-bottom.jsp" />
