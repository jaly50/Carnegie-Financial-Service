<jsp:include page="template-top.jsp" />


<jsp:include page="error-list.jsp" />
<!-- Page Content -->
<!-- This is a very simple parallax effect achieved by simple CSS 3 multiple backgrounds, made by http://twitter.com/msurguy -->

<div class="container">
<div class="container">
<div class="container">
  <h2>Transition Day</h2>
    </br>
  <h5>Set Funds Price:</h5>            
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>Fund Name</th>
        <th>Fund ID</th>
        <th>Last Trading Day</th>
        <th>Set New Price</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>Ebiz Mutual Fund</td>
        <td>000960</td>
        <td>01/15/2015</td>
        <td>
            <input class="col-sm-12" placeholder="$new Price" />
        </td>
     </tr>

      <tr>
        <td>Dow Jones Index Fund</td>
        <td>600000</td>
        <td>01/15/2015</td>
        <td>
            <input class="col-sm-12" placeholder="$new Price" />
        </td>
     </tr>
   
      <tr>
        <td>Pimco Total Return</td>
        <td>630720</td>
        <td>01/15/2015</td>
        <td>
            <input class="col-sm-12" placeholder="$new Price" />
        </td>
     </tr>

    </tbody>
  </table>
  </br>

  <h4 align="center">   New Date: 
      <input type="text" name="buyAmount"> <input class="btn btn-default" type="submit" value="Set New Price">
 </h4>
  
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