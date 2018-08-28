<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-md bg-dark navbar-dark">
    <a class="navbar-brand" href="<c:url value='/'/> ">Todolist</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <c:choose>
                <c:when test="${not empty user}">
                    <form id="logoutForm" style="display: inline" action="<c:url value='/logout'/>"  method="post">
                        <ul class="nav navbar-nav">
                            <li class="nav-item"><a class="nav-link" href="#">Welcome, <b><c:out value="${user.name}"/></b></a></li>
                            <li class="nav-item"><a class="nav-link" href="javascript:document.getElementById('logoutForm').submit()" >Logout</a></li>
                        </ul>
                    </form>
                </c:when>
                <c:when test="${empty user}">
                    <ul class="nav navbar-nav">
                        <li class="nav-item"> <a class="nav-link" href="<c:url value='/login'/>">Login</a></li>
                        <li class="nav-item"> <a class="nav-link" href="<c:url value='/reg'/>">Registration</a></li>
                    </ul>
                </c:when>
            </c:choose>
    </div>
</nav>
<br>
