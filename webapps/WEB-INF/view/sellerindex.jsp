<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="../../OtherJSP/Error.jsp" %>
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
    <title>Seller main</title>
</head>
<body>
<nav class="one">
    <h1 align="center">Seller Page</h1>
    <ul>
        <li><a href="../../ShowroomSeller">Showrooms addresses</a></li>
        <li><a href="../../CarsSeller">Cars to order</a></li>
        <li><a href="../../OrderSeller">Orders to my phone</a></li>
        <li><a href="../../MessageCallSeller">Message..</a></li>
        <li><a href="../../ReqOrderPages">Help customers</a></li>
        <li><a href="/logout">Exit</a></li>
    </ul>
</nav>
<h1>Hello, <c:out value="${nameUser}" />!</h1>
<h2>Welcome to seller panel. There are you can<br>
    viev on your orders,cars in availability, and Write to older manager</h2>
</body>
</html>
