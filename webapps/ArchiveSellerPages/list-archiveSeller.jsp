<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="../OtherJSP/Error.jsp"%>
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
            border-right: 1px solid #b19891;
            color: #695753;
        }
        .one a:hover {background: #ffffff;}
        .one li {
            display: inline;
        }

        .table {
            width: 80%;
            border: none;
            margin-bottom: 20px;
        }
        .table thead th {
            font-weight: bold;
            text-align: left;
            border: none;
            padding: 10px 15px;
            background: #B19891FF;
            font-size: 14px;
        }
        .table thead tr th:first-child {
            border-radius: 8px 0 0 8px;
        }
        .table thead tr th:last-child {
            border-radius: 0 8px 8px 0;
        }
        .table tbody td {
            text-align: left;
            border: none;
            padding: 10px 15px;
            font-size: 14px;
            vertical-align: top;
        }
        .table tbody tr:nth-child(even){
            background: #ECDAD6FF;
        }
        .table tbody tr td:first-child {
            border-radius: 8px 0 0 8px;
        }
        .table tbody tr td:last-child {
            border-radius: 0 8px 8px 0;
        }
    </style>
    <title>ArchiveSeller Management Application</title>
</head>
<body>
<center>
    <h1 align="left">ArchiveSeller management</h1>
    <nav class="one" align="left">
        <ul>
            <li><a href="/">Home</a></li>
            <li><a href="listArchiveSeller">List all archiveSellers</a></li>
        </ul>
    </nav>
</center>
<div align="center">
    <table border="1" cellpadding="5" class="table">
        <caption><h2>List of ArchiveSellers</h2></caption>
        <thead>
        <tr>
            <th>asellerId</th>
            <th>Prev id</th>
            <th>Name</th>
            <th>Position</th>
            <th>Phone</th>
            <th>Showroom</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="archiveSeller" items="${listArchiveSeller}">
            <tr>
                <td><c:out value="${archiveSeller.asellerId}" /></td>
                <td><c:out value="${archiveSeller.prevId}" /></td>
                <td><c:out value="${archiveSeller.name}" /></td>
                <td><c:out value="${archiveSeller.position}" /></td>
                <td><c:out value="${archiveSeller.phone}" /></td>
                <td><c:out value="${archiveSeller.getShowrooms().city} ${archiveSeller.getShowrooms().address}" /></td>
                <td>
                    <a href="deleteArchiveSeller?asellerId=<c:out value='${archiveSeller.asellerId}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>