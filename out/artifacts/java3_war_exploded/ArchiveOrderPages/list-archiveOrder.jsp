<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="../OtherJSP/Error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            list-style: none;
            margin: 0 auto;
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
            padding: 1em 0;
            background: #ECDAD6;
        }
        .one a {
            padding: 1em;
            background: rgba(177, 152, 145, .3);
            barchiveOrder-right: 1px solid #b19891;
            color: #695753;
        }
        .one a:hover {background: #ffffff;}
        .one li {
            display: inline;
        }

        .table {
            width: 80%;
            barchiveOrder: none;
            margin-bottom: 20px;
        }
        .table thead th {
            font-weight: bold;
            text-align: left;
            barchiveOrder: none;
            padding: 10px 15px;
            background: #B19891FF;
            font-size: 14px;
        }
        .table thead tr th:first-child {
            barchiveOrder-radius: 8px 0 0 8px;
        }
        .table thead tr th:last-child {
            barchiveOrder-radius: 0 8px 8px 0;
        }
        .table tbody td {
            text-align: left;
            barchiveOrder: none;
            padding: 10px 15px;
            font-size: 14px;
            vertical-align: top;
        }
        .table tbody tr:nth-child(even){
            background: #ECDAD6FF;
        }
        .table tbody tr td:first-child {
            barchiveOrder-radius: 8px 0 0 8px;
        }
        .table tbody tr td:last-child {
            barchiveOrder-radius: 0 8px 8px 0;
        }
    </style>
    <title>ArchiveOrder Management Application</title>
</head>
<body>
<center>
    <h1 align="left">ArchiveOrder management</h1>
    <nav class="one" align="left">
        <ul>
            <li><a href="/">Home</a></li>
            <li><a href="listArchiveOrder">List all archiveOrders</a></li>
        </ul>
    </nav>
</center>
<div align="center">
    <table barchiveOrder="1" cellpadding="5" class="table">
        <caption><h2>List of ArchiveOrders</h2></caption>
        <thead>
        <tr>
            <th>archiveOrderId</th>
            <th>Prev id</th>
            <th>Date</th>
            <th>Customer</th>
            <th>Car</th>
            <th>Seller</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Availability</th>
            <th>Payment</th>
            <th>Confirmed</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="archiveOrder" items="${listArchiveOrder}">
            <tr>
                <td><c:out value="${archiveOrder.aorderId}" /></td>
                <td><c:out value="${archiveOrder.prevId}" /></td>
                <fmt:formatDate value="${archiveOrder.getDateOrd().getTime()}" pattern="yyyy-MM-dd" var="corrDate"/>
                <td><c:out value="${corrDate}" /></td>
                <td><c:out value="${archiveOrder.getCustomers().name}" /></td>
                <td><c:out value="${archiveOrder.getCars().name} ${archiveOrder.getCars().model} ${archiveOrder.getCars().year}" /></td>
                <td><c:out value="${archiveOrder.getSellers().name}" /></td>
                <td><c:out value="${archiveOrder.quantity}" /></td>
                <td><c:out value="${archiveOrder.price}" /></td>
                <td><c:out value="${archiveOrder.availability}" /></td>
                <td><c:out value="${archiveOrder.payment}" /></td>
                <td><c:out value="${archiveOrder.confirmed}" /></td>
                <td>
                    <a href="deleteArchiveOrder?aorderId=<c:out value='${archiveOrder.aorderId}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
