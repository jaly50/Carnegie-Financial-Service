
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Carnegie Financial Services</title>


    <!-- Bootstrap Core CSS -->
   
    <link href="css/style.css" rel='stylesheet' type='text/css' />
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/shop-homepage.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
</head>

<body>
     <!--header-->
         <div class="container">
            <div class="top-header">
                <div class="logo">
                    <a href="index.html"><img src="images/c1.png" title="barndlogo" /></a>
                </div>
                <div class="top-header-info">
                    <div class="top-contact-info">
                        <ul class="unstyled-list list-inline">
                            <li><span class="phone"> </span>412 - 5555555</li>
                            <li><span class="mail"> </span><a href="#">Help@cfs.com</a></li>
                            <div class="clearfix"> </div>
                        </ul>
                    </div>
                    <div class="cart-details">
                        <div class="login-rigister">
                            <ul class="unstyled-list list-inline">
                                <li><a class="login" href="register.html">Register</a></li>
                                <li><a class="rigister" href="login.html">LOGIN <span> </span></a></li>
                                <div class="clearfix"> </div>
                            </ul>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                </div>
                <div class="clearfix"> </div>
            </div>
           <!----//top-header---->
              <!---top-header-nav---->
            <div class="top-header-nav"> 
            <!----start-top-nav---->
             <nav class="top-nav main-menu">
                    <ul class="top-nav">
                      <li><a href="ViewAccount.html">View Account</a><span> </span></li>
                      <li><a href="BuyFund.html">BUY FUND</a><span> </span></li>
                      <li><a href="SellFund.html">SELL FUND</a><span> </span></li>
                      <li><a href="RequestCheck.html">Request Check</a><span> </span></li>
                      <li><a href="TransactionHistory.html">Transaction History</a><span> </span></li>
                      <li><a href="ResearchFund.html">Research Fund</a><span> </span></li>
                      <li><a href="ChangePassword.html">Change Password</a><span> </span></li>
                        <div class="clearfix"> </div>
                    </ul>
                    <a href="#" id="pull"><img src="images/nav-icon.png" title="menu" /></a>
              </nav>
        </div>
   
	<%@ page import="databeans.User"%>

