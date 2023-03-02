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
    <title>User</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/main-page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/bstreeview.css">

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
            <div class="navbar-nav ms-auto">
                <a class="nav-link px-lg-3 active" aria-current="page" href="${pageContext.request.contextPath}/uzytkownik/strona-glowna">STRONA GŁÓWNA</a>
                <a class="nav-link px-lg-3" href="#">Ogłoszenia</a>
                <a class="nav-link px-lg-3" href="#">Koszyk</a>

                <security:authorize access="hasRole('ADMIN')">
                    <a class="nav-link px-lg-3" href="${pageContext.request.contextPath}/admin/uzytkownik">Użytkownicy</a>
                </security:authorize>

                <li class="nav-item dropdown">
                    <span class="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown">Moje konto</span>
                    <ul class="dropdown-menu dropdown-navbar bg-success-light">
                        <li><a class="dropdown-item text-dark" href="#">Mój profil</a></li>
                        <li><a class="dropdown-item text-dark" href="#">Moje zamówienia</a></li>
                        <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">Wyloguj</a></li>
                    </ul>
                </li>
            </div>
        </div>
    </div>
</nav>


<main>
    <section class="central container col-sm-12 col-md-10 col-xl-8">

        <section id="main-search-info" class="bg-light p-5 mt-5">

            <%--@elvariable id="offerSearchDTO" type="com.aukcje.dto.OfferSearchDTO"--%>
            <%--@elvariable id="rootCategoriesDTO" type="com.aukcje.dto.CategoryDTO"--%>


            <p class="display-3 text-center mt-4 mb-5">
                Szukaj ogłoszenia
            </p>

            <%--@elvariable id="offerSearchModel" type="com.aukcje.model.OfferSearchModel"--%>
            <form:form method="get" action="${pageContext.request.contextPath}/uzytkownik/strona-glowna/szukaj/przetworz" modelAttribute="offerSearchModel">

                <div class="row mt-4">

                    <div class="col-7 col-sm-8">
                        <form:input type="text" path="phrase" class="form-control" id="fraza" name="fraza" placeholder="Wpisz frazę..."/>
                    </div>

                    <div class="col-5 col-sm-4 mb-2">
                        <form:select type="number" path="searchCategory" class="form-control form-select display" name="kategoria" id="kategoria">
                            <option value='0' class="myplaceholder"> Wybierz kategorię... </option>
                            <c:forEach var = "rootCategory" items = "${offerSearchDTO.categoryIdNames}">
                                <option value="${rootCategory.key}" ${rootCategory.value == offerSearchModel.searchCategory ? 'selected="selected"' : ''}> ${rootCategory.value} </option>
                            </c:forEach>
                        </form:select>
                    </div>

                </div>
                <div class="row">
                    <div class="col">
                        <c:forEach var = "offerType" items = "${offerSearchDTO.allOfferTypes}">
                            <form:checkbox  path="offerTypes" name="typyOfert" value="${offerType}"/>  <span class="me-4">${offerType}</span>
                        </c:forEach>
                    </div>
                    <div  class="col text-end">
                        Szukać w opisie? <form:checkbox path="searchInDescription" value="${offerSearchModel.searchInDescription}" name="szukacWOpisie" id="szukacWOpisie"></form:checkbox>
                    </div>
                </div>

                <p class="display-6 mt-5 mb-1">
                    Filtry
                </p>

                <div class="row cols-3">
                    <div class="col-5 col-md-4 col-lg-3 me-3">
                        <label for="minMaxPriceInputs" class="form-label">Cena</label>
                        <div id="minMaxPriceInputs" class="row cols-2 ps-2">
                            <form:input type="number" min="0" step="0.01" class="form-control col me-1" name="cenaMin" id="cenaMin" placeholder="od"  path="minPrice"/>
                            -
                            <form:input type="number" min="0" step="0.01" class="form-control col ms-1" name="cenaMax" id="cenaMax" placeholder="do" path="maxPrice"/>
                        </div>
                        <form:errors path="minPrice" cssClass="error"></form:errors>
                        <form:errors path="maxPrice" cssClass="error"></form:errors>
                        <c:if test="${offerSearchModel.minPrice > offerSearchModel.maxPrice}">
                            <div class="error">Cena minimalna nie może być wyższa od ceny maksymalnej</div>
                        </c:if>
                    </div>

                    <div class="d-flex flex-wrap align-content-end col-4 col-md-4 col-lg-3 ml-0">
                        <label for="sortTypeSelect" class="form-label">Sortowanie</label>
                        <form:select  type="text" path="sortType" class=" form-control form-select display" id="sortTypeSelect" name="typSortowania">
                            <c:forEach var = "sortType" items = "${offerSearchDTO.sortTypes}">
                                <option value="${sortType}">${sortType}</option>
                            </c:forEach>
                        </form:select>

                    </div>

                    <div class="row d-flex ms-auto me-auto me-lg-0 p-0 align-items-end mt-5 col-md-10 col-lg-3">
                        <button type="submit" class="btn button-submit text-uppercase mt-4">Szukaj</button>
                    </div>

                </div>

            </form:form>

            <hr/>

                <section id="offers" class="mt-4">

                    <div class="favorite-offers row cols-md-2 cols-lg-4 cols-xl-6 px-3">
                        <p class="display-5 text-left mt-5">
                            Ulubione ogłoszenia
                        </p>
                        <c:forEach begin="0" end="${pageSize-1}" varStatus="loop">
                            <div class="card col-12 col-md-6 col-lg-4 col-xl-3 g-5">
<%--                                <img class="card-img-top" src="img_avatar1.png" alt="Card image">--%>
                                <div class="card-body">
                                    <h4 class="card-title">John Doe</h4>
                                    <p class="card-text">Some example text.</p>
                                    <a href="#" class="btn btn-primary">See Profile</a>
                                </div>
                            </div>
                        </c:forEach>
                        <p class="text h3 mt-4 text-end">
                            Zobacz wszystkie ulubione...
                        </p>
                    </div>

                    <div class="buynow row cols-md-2 cols-lg-4 cols-xl-6 px-3">
                        <p class="display-5 text-left mt-5">
                            Najnowsze "Kup teraz"
                        </p>

                        <c:forEach begin="0" end="${pageSize-1}" varStatus="index">
                            <jsp:useBean id="buyNowDTOS" scope="request" type="java.util.List<com.aukcje.dto.OfferDTO>"/>
                            <c:if test="${buyNowDTOS.size() > index.index}">
                            <div class="card col-12 col-md-6 col-lg-4 col-xl-3 g-5">
<%--                                <img class="card-img-top" src="img_avatar1.png" alt="Card image">--%>
                                <div class="card-body">
                                    <h4 class="card-title">${buyNowDTOS.get(index.index).offerDetails.title}</h4>
                                    <p class="card-text">${buyNowDTOS.get(index.index).offerDetails.description}</p>
                                    <a href="#" class="btn btn-primary">Zobacz ogłoszenie</a>
                                </div>
                            </div>
                            </c:if>
                        </c:forEach>

                        <p class="text h3 mt-4 text-end">
                            Zobacz wszystkie kup teraz...
                        </p>
                    </div>

                    <div class="auctions row cols-md-2 cols-lg-4 cols-xl-6 px-3">
                        <p class="display-5 text-left mt-5">
                            Najnowsze aukcje
                        </p>

                        <c:forEach begin="0" end="${pageSize-1}" varStatus="index">
                            <jsp:useBean id="auctionDTOS" scope="request" type="java.util.List<com.aukcje.dto.OfferDTO>"/>
                            <c:if test="${auctionDTOS.size() > index.index}">
                            <div class="card col-12 col-md-6 col-lg-4 col-xl-3 g-5">
<%--                                <img class="card-img-top" src="img_avatar1.png" alt="Card image">--%>
                                <div class="card-body">
                                    <h1 class="card-text">${auctionDTOS.get(index.index).price} zł</h1>
                                    <div class="row">
                                        <p class="card-title col">${auctionDTOS.get(index.index).offerDetails.title}</p>
                                        <div class="col text-end">OBS</div>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/admin/uzytkownik" class="btn btn-primary">Zobacz ogłoszenie</a>
                                </div>
                            </div>
                            </c:if>
                        </c:forEach>
                        <p class="text h3 mt-4 text-end">
                            Zobacz wszystkie aukcje...
                        </p>
                    </div>

                </section>


        </section>

    </section>

</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>