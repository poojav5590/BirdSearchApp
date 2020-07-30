<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: pooja.vasudevan
  Date: 2/3/20
  Time: 11:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Migratory Bird</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<h1>Migratory Birds</h1>
<form action="getBird" method="GET">
  <% if (!((ArrayList<String>)request.getAttribute("birdList")).isEmpty()) { %>
  <p>Choose a bird from the dropdown:</p>
  <select name="birdbox" id="'birdbox">
    <% for (int i =0; i < ((ArrayList<String>)request.getAttribute("birdList")).size(); i++) { %>
    <% String val = ((ArrayList<String>)request.getAttribute("birdList")).get(i);%>
    <option value = "<%=val%>"><%=((ArrayList<String>)request.getAttribute("birdList")).get(i)%></option>
    <% } %>
  </select><br>
  <input type="submit" value="Submit" />
  <%} else {%>
  <p>Bird not found</p>
  <p>Created by Pooja Vasudevan</p>
  <p>Enter the name of a bird:</p>
  <label for="letter">Type the word.</label>
  <input type="text" name="searchWord" value="" /><br>
  <input type="submit" value="Submit" />
 <% }%>
</form>
</body>
</html>
