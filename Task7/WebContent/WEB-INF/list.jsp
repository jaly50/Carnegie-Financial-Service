
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="template-top.jsp" />


<table>
    
	<c:forEach var="cus" items="${customerList}">
		<tr>
			<td><h4 style = "margin-left:380px"><a href="viewcustomerdetails.do?customer_id=${cus.customer_id}">${cus.firstname} ${cus.firstname}</a></td>
		</tr>
		</h4>

	</c:forEach>
</table>

<jsp:include page="template-bottom.jsp" />
