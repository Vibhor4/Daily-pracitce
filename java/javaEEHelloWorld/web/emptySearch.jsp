<%--
  Created by IntelliJ IDEA.
  User: zzt
  Date: 12/21/15
  Time: 10:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="util" uri="http://zzt.learning.com" %>
<util:logCheck/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
<%@page session="true" %> will translate to
javax.servlet.http.HttpSession session = null;
// create new session if no session now, will return null if set session="false"
session = pageContext.getSession();
--%>
<%@page session="false" %>
<jsp:useBean id="keywords" class="javaBean.SearchBean" scope="page">
    <%--
    <jsp:useBean id="keywords" class="javaBean.SearchBean" scope="session">
    session will be created and keyword will always be the first search result
    --%>
    <jsp:setProperty name="keywords" property="keywords"/>
    <%--<jsp:setProperty name="keywords" property="keywords" param="keywords"/>--%>
</jsp:useBean>
<html>
<head>
    <title>Using search bean</title>
</head>
<body>
<p style="color: red;">
    Can't find the result related to:
    <jsp:getProperty name="keywords" property="keywords"/>
</p>

</body>
</html>
