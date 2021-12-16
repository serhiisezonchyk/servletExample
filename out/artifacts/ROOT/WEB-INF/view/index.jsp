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
    <title>Main</title>
  </head>
  <body>
  <nav class="one">
    <h1 align="center">DataBase "Car Showroom" Managment</h1>
    <ul>
      <li><a href="../../ShowroomPages">Showroom</a></li>
      <li><a href="../../CarPages">Car</a></li>
      <li><a href="../../SellerPages">Seller</a></li>
      <li><a href="../../CustomerPages">Customer</a></li>
      <li><a href="../../DeliveryPages">Delivery</a></li>
      <li><a href="../../OrderPages">Order</a></li>
      <li><a href="../../ReqOrderPages">ReqOrder</a></li>
      <li><a href="../../ReqToCallManagerPages">ReqToCallManager</a></li>
      <li><a href="../../RolesPages">Roles</a></li>
      <li><a href="../../UsersPages">Users</a></li>
      <li><a href="Reports">Reports</a></li>
    </ul>
  </nav>
  </body>
</html>
