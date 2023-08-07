<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
<div>
    <h1>Błąd</h1>
    <p>${errorMessage}</p>
</div>
</body>
</html>