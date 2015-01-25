<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="template-top.jsp" />

<<<<<<< HEAD
<jsp:include page="error-list.jsp" />

=======
>>>>>>> 6e1539b78ee53ced185ef9d67ea8d1e2aa1c063f
<div class="container">
	<br /> <br /> <br />
	<form method="post" action="resetCustomerPassword.do">
		<c:choose>
			<c:when test="${ (empty customer) }">
				<h4 style="margin-left: 380px">
					Customer Username: &nbsp;&nbsp;&nbsp; <input name="username"
						type="text" value="${form.username}" />
				</h4>
			</c:when>
			<c:otherwise>
			<h4 style="margin-left: 380px">Customer Name:
					&nbsp;&nbsp;&nbsp; ${customer.firstname}&nbsp; ${customer.lastname}</h4>
			<br />
				<h4 style="margin-left: 380px">Customer Username:
<<<<<<< HEAD
					&nbsp;&nbsp;&nbsp; <input type="hidden"  name = "username" value="${customer.username}" > ${customer.username}</h4>
=======
					&nbsp;&nbsp;&nbsp; ${customer.username}</h4>
>>>>>>> 6e1539b78ee53ced185ef9d67ea8d1e2aa1c063f
			</c:otherwise>
		</c:choose>
		<br />
		<h4 style="margin-left: 380px">
			New Password: &nbsp;&nbsp;&nbsp; <input name="newPassword"
				type="text" value="${form.newPassword}" />
		</h4>
		<br />

		<h4 style="margin-left: 380px">
			Confirm: &nbsp;&nbsp;&nbsp; <input type="text" name="confirm"
				value="${form.confirm}" />
		</h4>
		<br />
		<h6 align="center">
			<input align="center" class="btn btn-default" type="submit"
<<<<<<< HEAD
				value="Reset">
=======
				value="Create">
>>>>>>> 6e1539b78ee53ced185ef9d67ea8d1e2aa1c063f
		</h6>

		<br /> <br /> <br />
	</form>
</div>



<jsp:include page="template-bottom.jsp" />
