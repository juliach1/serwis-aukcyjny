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
    <title>Edytuj kategorię</title>

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

                <%--@elvariable id="categories" type="com.aukcje.dto.CategoryDTO"--%>
                <%--@elvariable id="categoryModel" type="com.aukcje.model.CategoryModel.java"--%>
                <%--@elvariable id="categoryDTO" type="com.aukcje.dto.CategoryDTO.java"--%>


                <form:form method="post" action="${pageContext.request.contextPath}/admin/kategoria/edytuj/przetworz" modelAttribute="categoryModel">
            <p class="display-3 text-center mt-3 mb-5">
                Edytuj kategorię
            </p>

                    <form:hidden path="id"/>

                <div class="row mt-2 mb-2">
                <div class="col-md-6">
                    <label for="nameInput" class="form-label">Nazwa kategorii</label>
                    <form:input type="text" path="name" class="form-control" id="nameInput" value="${categoryDTO.name}" placeholder="Podaj nazwę"/>
                    <form:errors path="name" cssClass="error"/>
                </div>

                <div class="col-md-6">
                    <label for="parentCategorySelect" class="form-label">Kategoria nadrzędna</label>
                    <form:select type="text" path="parentCategory" class="form-control form-select display" id="parentCategorySelect" placeholder="Wybierz kategorię...">
                        <c:forEach var = "categoryList" items = "${categories}">
                            <option value="${categoryList.id}" ${categoryList.name == categoryDTO.parentCategory ? 'selected="selected"' : ''}>
                                <c:if test="${categoryList.parentCategory != null}"> ${categoryList.parentCategory} --> </c:if> <span class="fw-bold"> ${categoryList.name} </span>
                            </option>
                        </c:forEach>
                        <option value="placeholder" ${categoryDTO.parentCategory == null ? 'selected="selected"' : ''}>Wybierz kategorię...</option>

                    </form:select>
                    <form:errors path="parentCategory" cssClass="error"/>
                </div>

            </div>

            <div class="row mt-5 justify-content-center">
                <button type="submit" class="btn button-submit col-sm-5 col-lg-4 mr-sm-2 mt-5 text-uppercase">ZAPISZ</button>
                <button type="button" class="btn button-cancel col-sm-5 col-lg-4 ml-sm-2 mt-3 mt-sm-5 text-capitalize">Anuluj</button>
            </div>
            <div class="row mt-2 justify-content-center">
                <a class="btn button-delete mt-5 col-sm-5 col-lg-3 ml-sm-2 mt-3 mt-sm-5 text-capitalize d-flex justify-content-center align-items-center" href="${pageContext.request.contextPath}/admin/kategoria/usun/${categoryDTO.id}" role="button">Usuń kategorię</a>
            </div>

            </form:form>

    </section>

</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>