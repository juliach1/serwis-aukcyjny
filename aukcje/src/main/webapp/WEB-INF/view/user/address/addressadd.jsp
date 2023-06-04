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
    <title>Dodawanie adresu</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
</head>


<body>
<nav class="navbar navbar-expand-lg navbar-dark py-2 sticky-top" id="navbar">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/uzytkownik/strona-glowna">
            <a class="navbar-brand page-logo" href="#"> Sell<span class="page-logo-bold">B<i class="bi bi-basket page-logo-icon"></i>Y</span></a>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link px-lg-3 active" aria-current="page" href="${pageContext.request.contextPath}/uzytkownik/strona-glowna">STRONA GŁÓWNA</a>
                </li>
                <security:authorize access="hasRole('USER')">
                    <li class="nav-item">
                        <a class="nav-link px-lg-3" href="#">Koszyk</a>
                    </li>
                </security:authorize>

                <security:authorize access="hasRole('ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link px-lg-3" href="${pageContext.request.contextPath}/admin/uzytkownik">Użytkownicy</a>
                    </li>
                </security:authorize>

                <security:authorize access="hasRole('ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link px-lg-3" href="${pageContext.request.contextPath}/admin/kategoria/pobierz-wszystkie">Kategorie</a>
                    </li>
                </security:authorize>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">Moje konto</a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#">Mój profil</a></li>
                        <security:authorize access="hasRole('USER')">
                            <li><a class="dropdown-item" href="#">Moje zamówienia</a></li>
                        </security:authorize>
                        <li>
                            <form:form action="${pageContext.request.contextPath}/wyloguj" method="POST">
                                <input class="dropdown-item text-danger" value="Wyloguj" type="submit"/>
                            </form:form>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<main>
    <section class="central container col-sm-12 col-md-10 col-xl-8">

        <div class="add-address bg-light p-5 mt-5">

            <%--@elvariable id="countries" type="com.aukcje.entity.Country"--%>
            <%--@elvariable id="addressModel" type="src/main/java/com/aukcje/model/AddressModel.java"--%>

            <form:form method="post" action="${pageContext.request.contextPath}/uzytkownik/adres/dodaj/przetworz" modelAttribute="addressModel">
            <p class="display-3 text-center mt-3 mb-5">
                Dodaj nowy adres
            </p>

            <div class="row mt-2">
                <div class="col-md-6">
                    <label for="firstNameInput" class="form-label">Imię</label>
                    <form:input type="text" path="firstName" class="form-control" id="firstNameInput" placeholder="Podaj imię"/>
                    <form:errors path="firstName" cssClass="error"/>
                </div>

                <div class="col-md-6">
                    <label for="lastNameInput" class="form-label">Nazwisko</label>
                    <form:input type="text" path="lastName" class="form-control" id="lastNameInput" placeholder="Podaj nazwisko"/>
                    <form:errors path="lastName" cssClass="error"/>
                </div>

            </div>

            <div class="row">

                <div class="col-md-6">
                    <label for="countryInput" class="form-label">Kraj</label>
                    <form:select type="text" path="country" class="form-control form-select display" id="countryInput">
                        <c:forEach var = "countryVar" items = "${countries}">
                            <option value="${countryVar.name}"
                                ${countryVar.name == "Polska" ? 'selected="selected"' : ''}> ${countryVar.name} </option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="country" cssClass="error"/>
                </div>

                <div class="col-md-6">
                    <label for="cityInput" class="form-label">Miasto</label>
                    <form:input type="text" path="city" class="form-control" id="cityInput" placeholder="Podaj miasto"/>
                    <form:errors path="city" cssClass="error"/>
                </div>

            </div>

            <div class="row justify-content-center">

                <div class="col-md-9 col-lg-8 col-xl-7">
                    <label for="streetNameInput" class="form-label">Adres</label>
                    <form:textarea type="text" path="streetName" class="form-control " id="streetNameInput" placeholder="Podaj pełny adres"/>
                    <form:errors path="streetName" cssClass="error"/>
                </div>

            </div>


                <div class="row">
                    <div class="col-md-4">
                        <label for="postalCodeInput" class="form-label">Kod pocztowy</label>
                        <form:input type="text" path="postalCode" class="form-control" id="postalCodeInput" placeholder="Podaj kod pocztowy"/>
                        <form:errors path="postalCode" cssClass="error"/>
                    </div>

                    <div class="col-md-8 justify-content-center">
                        <label for="phoneInput" class="form-label">Numer telefonu</label>
                        <form:input type="text" path="phone" class="form-control" id="phoneInput" placeholder="Uwzględnij numer kierunkowy!"/>
                        <form:errors path="phone" cssClass="error"/>
                    </div>

                </div>

            <div class="row mt-4 justify-content-center">
                <button type="submit" class="btn button-submit col-sm-5 col-lg-4 mr-sm-2 mt-5 me-sm-2 text-uppercase">ZAPISZ</button>
                <button type="button" class="btn button-cancel col-sm-5 col-lg-4 ml-sm-2 mt-3 ms-sm-2 mt-sm-5 text-capitalize" onclick="location.href='${pageContext.request.contextPath}/admin/uzytkownik'">Anuluj</button>
            </div>

            </form:form>

    </section>

</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>