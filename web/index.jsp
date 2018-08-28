<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF8">
  <title>Web project</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body>
<header>
  <%@include file="WEB-INF/views/header.jsp"%>
</header>
<section>
  <article>
    <div class="container">
      <div class="jumbotron">
        <h1>Organize life</h1>
        <p>TodoList lets you keep track of everything in one place, so you can get it all
          done and enjoy more peace of mind along the way.</p>
        <a  class="btn btn-primary btn-lg" href="<c:url value='/app'/>">Get started</a>
      </div>
    </div>
  </article>
</section>
<footer>
  <%@include file="WEB-INF/views/footer.jsp"%>
</footer>
</body>
</html>
