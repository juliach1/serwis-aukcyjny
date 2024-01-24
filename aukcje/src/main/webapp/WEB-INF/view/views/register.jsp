<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Rejestracja - SellBuy</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/register.css">
</head>


<body>

<%@include file="../fragments/navbar.jspf" %>

<main>
    <section class="central register-container container col-sm-12 col-md-11 col-lg-8 col-xl-6">

        <div class="login bg-light mt-5 p-5">

            <%--@elvariable id="userRegisterModel" type="src/main/java/com/aukcje/model/UserRegisterModel.java"--%>
            <form:form modelAttribute="userRegisterModel" method="POST" action="${pageContext.request.contextPath}/rejestracja/autoryzacja" >

                <c:if test="${registrationError != null}">
                    <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                            ${registrationError}
                    </div>
                </c:if>


                <p class="display-3 text-center">
                    Rejestracja
                </p>

                <div class="mx-5">

                    <div class="row mb-2 justify-content-center">

                        <div class="col-12 col-sm-6 col-lg-5 mb-sm-2">
                            <label for="usernameInput" class="form-label">Login</label>
                            <form:input type="text" class="form-control" name="username" id="usernameInput" placeholder="Podaj login" path="username"/>
                            <form:errors path="username" cssClass="error" />

                        </div>
                        <div class="col-12 col-md-6 col-lg-5">
                            <label for="emailInput" class="form-label">E-mail</label>
                            <form:input type="email" class="form-control" name="email" id="emailInput" placeholder="Podaj adres e-mail" path="email"/>
                            <form:errors path="email" cssClass="error" />

                        </div>

                    </div>

                    <div class="row justify-content-center mb-2">

                        <div class="col-12 col-sm-6 col-lg-5 mb-sm-2">
                            <label for="passwordInput" class="form-label">Hasło</label>
                            <form:input type="password" class="form-control" name="password" id="passwordInput" placeholder="Podaj hasło" path="password"/>
                            <form:errors path="password" cssClass="error" />

                        </div>
                        <div class="col-12 col-sm-6 col-lg-5">
                            <label for="passwordInput" class="form-label">Powtórz hasło</label>
                            <form:input type="password" class="form-control" name="matchingPassword" id="matchingPasswordInput" placeholder="Powtórz hasło" path="matchingPassword"/>
                            <form:errors path="matchingPassword" cssClass="error" />

                        </div>

                    </div>

                    <div class="row mb-2 justify-content-center">

                        <div class="col-12 col-sm-6 col-lg-5 mb-sm-2">
                            <label for="firstNameInput" class="form-label">Imię</label>
                            <form:input type="text" class="form-control" name="firstName" id="firstNameInput" placeholder="Podaj imię" path="firstName"/>
                        </div>

                        <div class="col-12 col-sm-6 col-lg-5">
                            <label for="lastNameInput" class="form-label">Nazwisko</label>
                            <form:input type="text" class="form-control" name="lastName" id="lastNameInput" placeholder="Podaj nazwisko" path="lastName"/>
                        </div>

                    </div>


                    <div class="row justify-content-center py-5 mt-3">
                        <button type="submit"
                                class="btn button-submit col-sm-6 text-uppercase">
                            Zarejestruj się
                        </button>
                    </div>

                        <hr/>
                        <div class="text-center mb-4 mt-5"> Masz już konto? <a href="${pageContext.request.contextPath}/logowanie">Zaloguj się</a> </div>


                </div>

            </form:form>

        </div>


    </section>

</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>