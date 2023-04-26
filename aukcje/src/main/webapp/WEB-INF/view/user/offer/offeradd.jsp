<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Dodaj ofertę - SellBuy</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/bstreeview.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/main-page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/offer/offer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/offer/offer-add.css">



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
    <%--@elvariable id="offerAddModel" type="com.aukcje.model.OfferAddModel"--%>
    <%--@elvariable id="itemConditionDTOS" type="java.util.List<com.aukcje.dto.ItemConditionDTO>"--%>
    <%--@elvariable id="offerTypeDTOS" type="java.util.List<com.aukcje.dto.OfferTypeDTO>"--%>

    <section class="central container col-sm-12 col-md-10 col-xl-8">
        <section id="new-offer" class="bg-light p-5 mt-5">

            <div class="mx-sm-1 mx-md-2 mx-lg-4 mx-xl-5 px-xl-4 px-lg-3 px-md-2">

                <p class="display-3 text-center mt-3 mb-5">
                    Dodaj przedmiot
                </p>

                <form:form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/oferta/dodaj/przetworz" modelAttribute="offerAddModel">

                    <form:hidden path="categoryId" id="chosen-category-id"/>


                    <div class="row row-cols-md-2 row-cols-1 pb-3">

                        <div class="d-flex col mt-4 justify-content-center align-items-center">
                            <form:radiobutton id="buy-now-check" path="offerType" class="me-2" checked="checked" onchange="setPriceInputVisibility(this)" value="${offerTypeDTOS.get(0).name}"/> ${offerTypeDTOS.get(0).name}
                            <form:radiobutton id="auction-check" path="offerType" class="ms-5 me-2" checked="unchecked" onchange="setPriceInputVisibility(this)" value="${offerTypeDTOS.get(1).name}"/> ${offerTypeDTOS.get(1).name}
                            <form:errors element="offerType" cssClass="error" />
                        </div>

                        <div class="col pb-3 pb-md-0">
                            <label for="titleInput" class="form-label">Tytuł oferty</label>
                            <form:input type="text" path="title" class="form-control" id="titleInput" placeholder="Podaj tytuł oferty"/>
                            <form:errors path="title" cssClass="error"/>
                        </div>

                    </div>

                    <div class="pb-3">
                        <label for="descriptionTextArea" class="form-label">Opis oferty</label>
                        <form:textarea type="text" path="description" class="form-control" id="descriptionTextArea" placeholder="Podaj kupującym więcej szczegółów na temat sprzedawanego przedmiotu"/>
                        <form:errors path="description" cssClass="error"/>
                    </div>


                    <div class="row pb-3">
                        <div class="col d-flex">
                            <div class="d-flex align-self-end">

                                <button id="choose-category-button" class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#demo">
                                    Wybierz kategorię
                                </button>

                                <div class="offcanvas offcanvas-start" id="demo">
                                    <div class="offcanvas-header">
                                        <h1 class="offcanvas-title">Kategorie</h1>
                                        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas"></button>
                                    </div>
                                    <div class="offcanvas-body">
                                        <div id="categoryTree" class="col-12" onclick=""></div>
                                    </div>
                                </div>

                                <p id="chosen-category-display" class="align-self-end ms-3">
                                        Wybrana kategoria
                                </p>

                                <form:errors element="category" cssClass="error" />

                            </div>


                        </div>
                        <div class="col-md-4 col-lg-6">
                            <label for="itemConditionSelect" class="form-label">Stan</label>
                            <form:select path="itemCondition" class="form-select form-control" id="itemConditionSelect">
                                <c:forEach var = "conditionVar" items = "${itemConditionDTOS}">
                                    <option value="${conditionVar.name}"> ${conditionVar.name} </option>
                                </c:forEach>
                            </form:select>
                            <form:errors element="itemCondition" cssClass="error" />
                        </div>
                    </div>

                    <section id="pricing" class="row pb-5">

                        <div class="col">
                            <label id="buy-now-price-label" for="priceInput" class="form-label">Cena [zł/szt]</label>
                            <label id="auction-price-label" for="priceInput" class="form-label d-none">Cena początkowa [zł]</label>
                            <form:input type="text" path="price" class="form-control" id="priceInput"/>
                            <form:errors element="price" cssClass="error" />
                        </div>
                        <div class="col">
                            <label for="quantityInput" class="form-label">Liczba przedmiotów</label>
                            <form:input type="number" min="1" path="quantity" class="form-control" id="quantityInput"/>
                            <form:errors element="quantity" cssClass="error" />
                        </div>

                    </section>

                    <div class="form-check mt-4 pb-3 ps-0">
                        <input type="file" name="file" id="file" accept="image/png, image/jpeg">
                    </div>

                <div class="row mt-3 mb-3 justify-content-center">
                    <button type="submit" class="btn button-submit col-sm-5 col-lg-4 mt-5 text-uppercase me-sm-2">Dodaj ogłoszenie</button>
                    <button type="button" class="btn button-cancel col-sm-5 col-lg-4 mt-3 mt-sm-5 text-capitalize ms-sm-2">Anuluj</button>
                </div>

            </section>

        </form:form>

        </section>
    </section>

</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/files/js/bstreeview.js"></script>

<script>

    //---------------------CREATING CATEGORIES TREE--------------------

    var baseurl = '${pageContext.request.contextPath}'
    let url = baseurl.concat('/rest/categories/selection');
    var response;

    $.ajax({
        url: url,
        success: function(data) {
            response = data;
            console.log(response);
        },
        async: false
    });

    var tree = $('#categoryTree').bstreeview({data: response});
</script>

<script src="${pageContext.request.contextPath}/files/js/user/offer/offeradd.js"></script>



</body>
</html>