<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="../OtherJSP/Error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users Management</title>
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
            background-color: cadetblue;
            width: 90%;
            border-radius: 10px;
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
            width: 50%;
            margin-bottom: 20px;
            border: 5px solid #f8e6e1;
            border-top: 5px solid #f8e6e1;
            border-bottom: 3px solid #f8e6e1;
            border-collapse: collapse;
            outline: 3px solid #695753;
            font-size: 15px;
            background: #f8e6e1!important;
        }
        .table th {
            font-weight: bold;
            padding: 7px;
            background: #B19891FF;
            border: none;
            text-align: left;
            font-size: 15px;
            border-top: 3px solid #B19891FF;
            border-bottom: 3px solid #B19891FF;
        }
        .table td {
            padding: 7px;
            border: none;
            border-top: 3px solid #f8e6e1;
            border-bottom: 3px solid #f8e6e1;
            font-size: 15px;
        }
        .table tbody tr:nth-child(even){
            background: #ECDAD6FF !important;
        }

        input[type=submit] {
            background-color: #B19891FF;
            border: none;
            color: #fff;
            border-radius: 5px;
            padding: 6px 25px;
        }
        input[type=text] {
            border: none;
            border-radius: 5px;
        }
        select{
            border: none;
            border-radius: 2px;
        }
    </style>
</head>
<body>
<center>
    <h1 align="left">Users management</h1>
    <nav class="one" align="left">
        <ul>
            <li><a href="home">Home</a></li>
            <li><a href="newUsers">Add new users</a></li>
            <li><a href="listUsers">List all users</a></li>
        </ul>
    </nav>
</center>
<div align="center">
    <c:if test="${users != null}">
    <form action="updateUsers" method="post">
        </c:if>
        <c:if test="${users == null}">
        <form action="insertUsers" method="post">
            </c:if>
            <table border="1" cellpadding="5" class="table">
                <caption>
                    <h2>
                        <c:if test="${users.usersId != null}">
                            Edit Users
                        </c:if>
                        <c:if test="${users == null}">
                            Add New Users
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${users != null}">
                    <input type="hidden" name="usersId" value="<c:out value='${users.usersId}' />" />
                </c:if>
                <tr>
                    <th>Users name: </th>
                    <td>
                        <input type="text" name="name" size="45"
                               value="<c:out value='${users.name}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Users login: </th>
                    <td>
                        <input type="text" name="login" size="45"
                               value="<c:out value='${users.login}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Users password: </th>
                    <td>
                        <input type="text" name="password" size="15"
                               value="<c:out value='${users.password}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Users role: </th>
                    <td>
                        <select name="rolesId">
                            <c:forEach var="roles" items="${listRoles}">
                                <option <c:if test="${currentrolesId == roles.rolesId}">selected</c:if> value="<c:out value='${roles.rolesId}' />">${roles.role}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Users pass help: </th>
                    <td>
                        <input type="text" name="passHelp" size="15"
                               value="<c:out value='${users.passHelp}' />"
                        />
                    </td>
                </tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Save" />
                    </td>
                </tr>
            </table>
        </form>
</div>
</body>
</html>