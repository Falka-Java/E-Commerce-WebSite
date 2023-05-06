<%--
  Created by IntelliJ IDEA.
  User: vsh15
  Date: 5/6/2023
  Time: 11:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../includes/head.jsp" %>
    <title>Title</title>
</head>
<body>
Invoice page

<%
    if(request.getAttribute("error")!=null){
        out.print("<div class=\"alert alert-danger\" role=\"alert\">");
        out.print(request.getAttribute("error"));
        out.print("</div>");}
    else{
        out.println("Everything went fine");
    }
%>
</body>
</html>
