<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="../OtherJSP/Error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Delivery Management</title>
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
    <h1 align="left">Delivery management</h1>
    <nav class="one" align="left">
        <ul>
            <li><a href="/">Home</a></li>
            <li><a href="newDelivery">Add new delivery</a></li>
            <li><a href="listDelivery">List all deliverys</a></li>
        </ul>
    </nav>
</center>
<div align="center">
    <c:if test="${delivery != null}">
    <form action="updateDelivery" method="post">
        </c:if>
        <c:if test="${delivery == null}">
        <form action="insertDelivery" method="post">
            </c:if>
            <table border="1" cellpadding="5" class="table">
                <caption>
                    <h2>
                        <c:if test="${delivery.deliveryId != null}">
                            Edit Delivery
                        </c:if>
                        <c:if test="${delivery == null}">
                            Add New Delivery
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${delivery != null}">
                    <input type="hidden" name="deliveryId" value="<c:out value='${delivery.deliveryId}' />" />
                </c:if>
                <tr>
                    <th>Address from: </th>
                    <td>
                        <input type="text" name="addressFrom" size="45"
                               value="<c:out value='${delivery.addressFrom}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Delivery date: </th>
                    <td>
                        <jsp:useBean id="now" class="java.util.Date"/>
                        <fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today"/>
                        <fmt:formatDate value="${delivery.getDateDel().getTime()}" pattern="yyyy-MM-dd" var="corrDate"/>
                        <input type="date" name="dateDel" size="45"
                                <c:if test="${delivery != null}"> value = "<c:out value='${corrDate}' />"</c:if>
                                <c:if test="${delivery == null}"> value = "<c:out value='${today}' />"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <th>Delivery price: </th>
                    <td>
                        <input type="text" name="price" size="45"
                               value="<c:out value='${delivery.price}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Delivery type: </th>
                    <td>
                        <input type="text" name="deliveryType" size="15"
                               value="<c:out value='${delivery.deliveryType}' />"
                        />
                    </td>
                </tr>
                <tr>
                <th>Delivery showroom: </th>
                <td>
                    <select name="showroomId">
                        <c:forEach var="showroom" items="${listShowroom}">
                            <option <c:if test="${currentshowroomId == showroom.showroomId}">selected</c:if> value="<c:out value='${showroom.showroomId}' />">${showroom.address} ${showroom.city}</option>
                        </c:forEach>
                    </select>
                </td>
                </tr>
                <tr>
                    <th>Delivery car: </th>
                    <td>
                        <select name="carId">
                            <c:forEach var="car" items="${listCar}">
                                <option <c:if test="${currentcarId == car.carId}">selected</c:if> value="<c:out value='${car.carId}' />">${car.name} ${car.model} ${car.price}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save" />
                </td>
                </tr>
            </table>
        </form>
</div>
</body>
</html>