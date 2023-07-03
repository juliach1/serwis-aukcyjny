<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Edycja użytkownika</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/admin/user.css">
</head>


<body>

<nav class="navbar navbar-expand-lg navbar-dark py-2 sticky-top" id="navbar">
    <div class="container">
        <a class="navbar-brand" href="#">
            <a class="navbar-brand page-logo" href="#"> Sell<span class="page-logo-bold">B<i class="bi bi-basket page-logo-icon"></i>Y</span></a>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav ms-auto">
                <a class="nav-link px-lg-3" aria-current="page" href="index.html">STRONA GŁÓWNA</a>

                <security:authorize access="hasRole('USER') ">
                    <a class="nav-link px-lg-3" href="#">Koszyk</a>
                </security:authorize>

                <security:authorize access="hasRole('ADMIN')">
                    <a class="nav-link px-lg-3 active" href="${pageContext.request.contextPath}/admin/uzytkownik">Użytkownicy</a>
                </security:authorize>

                <security:authorize access="hasRole('ADMIN')">
                    <a class="nav-link px-lg-3" href="${pageContext.request.contextPath}/admin/kategoria/pobierz-wszystkie">Kategorie</a>
                </security:authorize>


                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">Moje konto</a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#">Mój profil</a></li>
                        <security:authorize access="hasRole('USER')">
                            <li><a class="dropdown-item" href="#">Moje zamówienia</a></li>
                        </security:authorize>
                        <li>
                            <form:form action="${pageContext.request.contextPath}/logout" method="POST">
                                <input class="dropdown-item text-danger" value="Wyloguj" type="submit"/>
                            </form:form>
                        </li>
                    </ul>
                </li>
            </div>
        </div>
    </div>
</nav>


<main>
    <section class="central container col-sm-12 col-md-10 col-xl-8">

        <div class="edit bg-light p-5 mt-5">

        <%--@elvariable id="userEditModel" type="com.aukcje.model.UserEditModel"--%>
        <%--@elvariable id="userDTO" type="com.aukcje.dto.UserDTO"--%>
        <%--@elvariable id="statuses" type="com.aukcje.dto.UserStatusDTO"--%>

            <form:form method="post" action="${pageContext.request.contextPath}/admin/uzytkownik/edytuj/przetworz" modelAttribute="userEditModel">
                <p class="display-3 text-center mt-3 mb-5">
                    Edytuj użytkownika
                </p>

                <form:hidden path="id"/>

                <div class="row">
                    <div class="col-md-6">
                        <label for="usernameInput" class="form-label">Nazwa użytkownika</label>
                        <form:input type="text" path="username" value="${userDTO.username}" class="form-control" id="usernameInput"/>
                        <form:errors path="username" cssClass="error"/>
                    </div>

                    <div class="col-md-6">
                        <label for="emailInput" class="form-label">E-mail</label>
                        <form:input type="text" path="email" value="${userDTO.email}" class="form-control" id="emailInput"/>
                        <form:errors path="email" cssClass="error"/>

                    </div>

                </div>

                <div class="row">
                    <div class="col-md-6">
                        <label for="firstNameInput" class="form-label">Imię</label>
                        <form:input type="text" path="firstName" value="${userDTO.firstName}" class="form-control" id="firstNameInput"/>

                    </div>
                    <div class="col-md-6">
                        <label for="lastNameInput" class="form-label">Nazwisko</label>
                        <form:input type="text" path="lastName" value="${userDTO.lastName}" class="form-control" id="lastNameInput"/>

                    </div>
                </div>

                <div class="row justify-content-center">

                    <div class="col-sm-6 col-md-5 col-lg-4">
                        <label for="statusInput" class="form-label">Status</label>
                        <form:select type="text" path="userStatus" value="${userDTO.userStatus.name}" class="form-control form-select display" id="statusInput">
                            <c:forEach var = "userStatusVar" items = "${statuses}">
                                <option value="${userStatusVar.id}"
                                    ${userDTO.userStatus.name == userStatusVar.name ? 'selected="selected"' : ''}>${userStatusVar.name}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>

            <div class="row justify-content-center">
                <button type="submit" class="btn button-submit col-sm-5 col-lg-4 mr-sm-2 mt-5 text-uppercase">ZAPISZ</button>
                <button type="button" class="btn button-cancel col-sm-5 col-lg-4 ml-sm-2 mt-3 mt-sm-5 text-capitalize" onclick="location.href='${pageContext.request.contextPath}/admin/uzytkownik'">Anuluj</button>
            </div>

        </form:form>

    </section>

</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>