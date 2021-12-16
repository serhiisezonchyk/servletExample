<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="Error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        body {
            margin:0;
            background-color: #f8e6e1;
        }
        h1 {
            font-size: 40px;
            font-family: 'Lora', serif;
            transition: .5s linear;
            color: #6c6363;
        }
        h2 {
            font-family: monospace;
            color: #606060;
            font-size: 25px;
        }
        ul {
            flex-basis: auto;
            list-style: none;
            margin: 0 auto;
            display: flex;
        }
        a {
            text-decoration: none;
            font-family: 'Lora', serif;
            transition: .5s linear;
        }
        i {
            margin-right: 10px;
        }
        nav {
            display: block;
            width: 100%;
            margin: 0 auto 30px;
        }
        .one ul {
            padding: 2px;
            background: #ECDAD6;
        }
        .one a {
            padding: 1em;
            background: rgba(177, 152, 145, .3);
            border-right: 1px solid #b19891;
            color: #695753;
        }
        .one a:hover {background: #b19891;}
        .one li {
            flex-grow: 1;
            display: grid;
            flex-basis: auto;
        }
    </style>
    <title>Main</title>
</head>
<body>
<nav class="one">
    <h1 align="center">Reports</h1>
    <ul>
        <li><a href="/">Home</a></li>
        <li><a href="ReportsCarStat">Car stattistic report</a></li>
        <li><a href="ReportSimpleOrder">Simple order report</a></li>
        <li><a href="ReportSellerStat">Seller statistic report</a></li>
        <li><a href="ReportSimpleCar">Simple Car Report</a></li>
    </ul>
    <h2>There are some types of available reports</h2>
    <ol>
        <li>1) Report of Car statistic - You can see some statistic about selles of models car</li>
        <li>2) Simple order statistic - shows to you some statistic about orders</li>
        <li>3) Seller statistic - shows statistic about salles of each seller who work in company</li>
        <li>4) Simple car statistic - shows simple report with cars</li>
    </ol>
</nav>
</body>
</html>
