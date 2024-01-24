<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Logowanie - SellBuy</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
</head>


<body>

<%@include file="../fragments/navbar.jspf" %>

<main>
    <section class="central container col-sm-9 col-md-7 col-xl-5">

        <div class="login bg-light mt-5 pt-4">

            <form:form method="POST" action="${pageContext.request.contextPath}/logowanie/autoryzacja">

                <p class="display-3 text-center mt-3 mb-5">
                    Logowanie
                </p>

                 <div class="d-flex flex-column align-items-center">

                     <div class="col-10 col-sm-9 col-md-8 col-lg-7 col-xl-6 py-2 py-md-3">
                         <label for="usernameInput" class="form-label">Login</label>
                         <input type="text" class="form-control" name="username" id="usernameInput" placeholder="Podaj login"/>
                         <form:errors path="username" cssClass="error"/>

                     </div>

                     <div class="col-10 col-sm-9 col-md-8 col-lg-7 col-xl-6 py-2 py-md-3 mb-3">
                         <label for="passwordInput" class="form-label">Hasło</label>
                         <input type="password" class="form-control" name="password" id="passwordInput" placeholder="Podaj hasło"/>
                         <form:errors path="password" cssClass="error"/>

                     </div>

                     <button type="submit"
                             class="btn button-submit my-5 col-10 col-sm-9 col-md-8 col-lg-7 col-xl-5 align-self-center text-uppercase">
                         Zaloguj się
                     </button>

                     <hr/>
                     <div class="mb-5 mt-2"> Nie masz konta? <a href="${pageContext.request.contextPath}/rejestracja">Zarejestruj się</a> </div>
                 </div>

            </form:form>

        </div>

    </section>

</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>