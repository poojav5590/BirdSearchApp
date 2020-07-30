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
  <title>Migratory Birds</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<h1>Migratory Birds</h1>
<form action="getBird" method="GET">
  <p>Created by Pooja Vasudevan</p>
  <p>Enter the name of a bird:</p>
  <label for="letter">Type the word.</label>
  <input type="text" name="searchWord" value="" /><br>
  <input type="submit" value="Submit" />
</form>
</body>
</html>
