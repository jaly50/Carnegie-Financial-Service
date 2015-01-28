<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="template-top.jsp" />
<jsp:include page="error-list.jsp" />
<jsp:include page="message.jsp" />


<script type="text/javascript"> 

function myfilter(e) {
	 var obj=e.srcElement || e.target;
	 var dot=obj.value.indexOf(".");//alert(e.which);
	 var  key=e.keyCode|| e.which;
	 if(key==8 || key==9 || key==46 || (key>=37  && key<=40))//to satisfy certain keys.
	  return true;
	 if (key<=57 && key>=48) { //number
	  if(dot==-1)//dot
	     return true;
	    else if(obj.value.length<=dot+3)//two decimal
	  return true;
	 } else if((key==46) && dot==-1){//dot number
	  return true;
	 }        
	    return false;
	}

function verification() {
	var text = document.getElementById("shares").value;
	  if (text == '') {
	     alert("Please Input Buying Amount");
	     return false;
	  }
 	
  
  return true;
}


function check(lbl){
    var txtval=lbl.value;        
    var key = event.keyCode;  
    if((key < 48||key > 57)&&key != 46){    
     event.keyCode = 0; 
     alert("Input number please"); 
    }else{  
     if(key == 46){  
      if(txtval.indexOf(".") != -1||txtval.length == 0)  
       event.keyCode = 0; 
       alert("Input number please"); 
     }  
   }  
}


</script>


<div class="container">
 <h2>Transition Day</h2>
<br>
 
<form method="post" action="transitionDay.do">
<h4 align="center">  New Date: 
      <input type="text" id="newDate" name="newDate" placeholder="YYYYMMDD" value=""/> 
      <input class="btn btn-default" type="submit" name="button" value="Submit">
      &nbsp;&nbsp;&nbsp;&nbsp;<span>Last Transition Day:&nbsp;</span><span> ${latestDate}</span>
 </h4>
 <br>
 

 <table class="table table-bordered">
    <thead>
      <tr>
        <th>Fund ID</th>
        <th>Fund Symbol</th>
        <th>Last Transition Day Price</th>
        <th>Set New Price</th>
      </tr>
    </thead>
    <tbody>
      
     <c:forEach var="f" items="${TransiFundTable}">    
      <tr>
        <td>${f.fund_id }</td>
        <td>${f.symbol}</td>
         <td align = "right">${f.latestPrice}</td>
        <td align="center"><input type="text" id="price" name="price" value=""/></td>
      </tr>
     </c:forEach>
    </tbody>
    
  </table>

 <br />
 
 
 <br />
 <br />
 <br />
</form>
</div>

 

<jsp:include page="template-bottom.jsp" />
