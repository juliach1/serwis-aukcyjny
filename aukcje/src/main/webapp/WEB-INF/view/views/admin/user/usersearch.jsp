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
    <title>Szukaj użytkownika</title>

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

        <div class="search bg-light p-5 mt-5">

            <%--@elvariable id="userSearchModel" type="com.aukcje.model.UserSearchModel"--%>
            <form:form method="get" action="${pageContext.request.contextPath}/admin/uzytkownik" class="pb-5" modelAttribute="userSearchModel">
                <%--@elvariable id="searchModel" type="com.aukcje.model.UserSearchModel"--%>

                <p class="display-3 text-center mt-3 mb-5">
                    Użytkownicy
                </p>


                <div class="row justify-content-center">
                    <div class="col-sm-12 col-lg-8 col-xl-7 py-3 justify-content-center">
                        <form:input type="text" class="form-control" id="fraza"
                               placeholder="Wyszukaj użytkownika..." name="fraza" path="phrase"/>

                        <div class="row justify-content-center align-content-center">

                            <div class="col-5 py-3">
                                <label for="rozmiarStrony" class="form-label">Wyników na stronę</label>
                                <form:input type="number" min="1" class="form-control" id="rozmiarStrony" placeholder="10"
                                        name="rozmiarStrony" path="pageSize"/>
                            </div>

                            <button type="submit"
                                    class="btn btn-warning button-submit mb-md-0 mt-5 col-sm-6 align-self-center text-uppercase">
                                WYSZUKAJ
                            </button>

                            <div class="container row" >

                                <div class="col">
                                    <form:checkbox value="true" name="isActive" id="isActive" path="isActive"/>
                                    <label for="isActive" class="form-label">Aktywni</label>
                                </div>

                                <div class="col">
                                    <form:checkbox value="true" name="isBlocked" id="isBlocked" path="isBlocked"/>
                                    <label for="isBlocked" class="form-label">Zablokowani</label>
                                </div>

                                <div class="col">
                                    <form:checkbox  value="true" name="isDeleted" id="isDeleted" path="isDeleted"/>
                                    <label for="isDeleted" class="form-label">Usunięci</label>
                                </div>


                            </div>

                        </div>

                    </div>
                </div>

            </form:form>

            <div class="users-list pt-5">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <%--                        <th>Nr</th>--%>
                        <th>Id</th>
                        <th>Nazwa użytkownika</th>
                        <th>Średnia ocena</th>
                        <th>Status</th>
                        <th>Akcje</th>

                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="tempUser" varStatus="index" items="${users}">
                        <tr>
                            <td>${tempUser.id}</td>
                            <td>${tempUser.username}</td>
                            <td class="text-center">${tempUser.averageRate}</td>
                            <td class="text-lowercase">${tempUser.userStatus.name}</td>

                            <td>
                                <a href="#">Podgląd</a>
                                |
                                <a href="${pageContext.request.contextPath}/admin/uzytkownik/edytuj/${tempUser.id}">Edytuj</a>
                                |
                                <c:choose>
                                    <c:when test="${tempUser.userStatus.id==2}">
                                        <a href="${pageContext.request.contextPath}/admin/uzytkownik/odblokuj/${tempUser.id}" style="color: red">
                                            Odblokuj
                                        </a>

                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/admin/uzytkownik/zablokuj/${tempUser.id}" style="color: red">
                                            Zablokuj
                                        </a>
                                    </c:otherwise>

                                </c:choose>
                                |
                                <a href="${pageContext.request.contextPath}/admin/uzytkownik/usun/${tempUser.id}" style="color: red">Usuń</a>
                            </td>

                        </tr>
                    </c:forEach><tr>
                    </tbody>

                </table>

            </div>
        </div>

    </section>

    <script>
        <%--var req = new XMLHttpRequest();--%>
        <%--req.open('GET', '${pageContext.request.contextPath}/rest/categories', false);--%>
        <%--req.send(null);--%>
        <%--if(req.status == 200)--%>
        <%--    dump(req.responseText);--%>
        <%--console.log(req);--%>
    </script>



</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>