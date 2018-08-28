<%@ page import="by.gsu.epamlab.controllers.ConstantsJSP" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF8">
    <title>Login - Web project</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
</head>
<body>
<div id="login-box">
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            <c:out value="${errorMessage}"></c:out>
        </div>
    </c:if>
    <div class="container">
        <h2>Login form</h2>
        <form name="loginForm" method="POST" action="<c:url value='/login'/>">
            <div class="form-group">
                <label for="login">Login:</label>
                <input type="text" name=<%= ConstantsJSP.KEY_LOGIN %> class="form-control" id="login"
                       placeholder="Enter login">
            </div>
            <div class="form-group">
                <label for="pwd">Password:</label>
                <input type="password" name=<%= ConstantsJSP.KEY_PASSWORD %> class="form-control" id="pwd"
                       placeholder="Enter password">
            </div>
            <button type="submit" class="btn btn-primary">Enter</button>
        </form>
        <hr>
        <a href="<c:url value='/reg'/>">Registration</a> <a href="<c:url value='/'/>">Back</a>
    </div>

</div>
<%@include file="footer.jsp"%>
</body>
</html>
