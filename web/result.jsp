<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: pooja.vasudevan
  Date: 2/3/20
  Time: 11:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%= request.getAttribute("doctype") %>--%>
<html>
<head>
  <title>Migratory Birds</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<% if (request.getAttribute("pictureURL") != null ) { %>
<h1>Bird chosen: <%= request.getAttribute("birdName")%></h1><br>
<img src="<%= request.getAttribute("pictureURL")%>"><br><br>
<form action="getBird" method="GET">
  <p>Credit: https://nationalzoo.si.edu/scbi/migratorybirds</p>
  <p>Photographer:  <%= request.getAttribute("author")%></p>
  <input type="submit" value="Continue" />
<% } else  { %>
<h1>Chosen bird: <%= request.getAttribute("birdName")%> could not be found</h1><br>
  <p>Credit: https://nationalzoo.si.edu/scbi/migratorybirds</p>
  <input type="submit" value="Continue" />
<% } %>

</form>
</body>
</html>

