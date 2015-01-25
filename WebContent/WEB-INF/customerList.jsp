<!--View Customer Account-->
<<<<<<< HEAD
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
=======
>>>>>>> 6e1539b78ee53ced185ef9d67ea8d1e2aa1c063f
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />
<div class="container">
	<div class="container">
	<div class="container">
	  <h2>Account Management</h2>
	    <br>
	    <br>
	    <form method="POST" action="viewCustomerList.do">
	
	
	 
 <h5>Customer List:</h5>     
    <h4 align="right" style="margin-right: 55px">  
       	<input class="btn btn-default" type="submit" name="operation"  value="View Account">
       <input class="btn btn-default" type="submit" name="operation"  value="Deposit Check">
       <input class="btn btn-default" type="submit" name="operation"  value="Reset Password">
       <input class="btn btn-default" type="submit" name="operation"  value="View Transaction History">
 </h4>       
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>Full Name</th>
        <th>User Name</th>
        <th>Current Balance</th>
        <th>Available Blance</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="c" items="${customerList}">
		<tr>
			<td valign="top">
					<input type="hidden" name="customer_id"
						value="${c.customer_id}" /> ${c.firstname}&nbsp;${c.lastname}
			</td>
			 <td>${c.username}</td>
<<<<<<< HEAD
        <td><fmt:formatNumber type="number" 
            pattern="#,###.00" value="${c.totalbalance/100}" /></td>
        <td><fmt:formatNumber type="number" 
            pattern="#,###.00" value="${c.availablebalance/100}" /></td>
=======
        <td>${c.totalbalance}</td>
        <td>${c.availablebalance}</td>
>>>>>>> 6e1539b78ee53ced185ef9d67ea8d1e2aa1c063f
        <td align="center"><input type="radio" name="select" value="${c.customer_id}"></td>
		</tr>
	</c:forEach>
    </tbody>
  </table>
  <br/>
  </form>

	</div>
	</div>
	    <br />
	    <br />
	    <br />
	    <br />
	    <br />
	    <br />
	    <br />
	</div>



<jsp:include page="template-bottom.jsp" />
