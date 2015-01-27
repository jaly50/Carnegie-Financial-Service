<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />
<jsp:include page="message.jsp" />

<div class="container">
<br />
 <br />
 <br />
<form method="post" action="depositCheck.do">
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

					&nbsp;&nbsp;&nbsp;<input type="hidden"  name = "username" value="${customer.username}" > ${customer.username}</h4>

			</c:otherwise>
		</c:choose>
 <br />
 
 <h4 style = "margin-left:380px">   Deposit Amount: &nbsp;
      <input type="text" name="amount"  value="${form.amount}"/> 
 </h4>
 <br />
 <h6 align = "center">
 <input  class="btn btn-default" type="submit" value="Deposit">
 </h6>
 
 <br />
 <br />
 <br />
</form>
</div>

  

<jsp:include page="template-bottom.jsp" />
