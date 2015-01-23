
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
	
	 <h4 style = "margin-left:380px">  Username: &nbsp;
	     <input name="username" type="text"  />
	 </h4><br>
	 <h4 align = "center">
	 <input align="center"class="btn btn-default" type="submit" value="View  Customer  Account ">
	 </h4><br>
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
