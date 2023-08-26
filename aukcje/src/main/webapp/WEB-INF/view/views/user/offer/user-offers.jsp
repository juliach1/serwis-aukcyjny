<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Strona główna</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/bstreeview.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/main-page.css">


</head>


<body>

<%@include file="../../../fragments/navbar.jspf" %>
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
                        </form:select>`
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

                        <%--
                        <div class="d-flex flex-wrap align-content-end col-4 col-md-4 col-lg-3 ml-0">
                            <label for="sortTypeSelect" class="form-label">Sortowanie</label>
                            <form:select  type="text" path="sortType" class=" form-control form-select display" id="sortTypeSelect" name="typSortowania">
                                <c:forEach var = "sortType" items = "${offerSearchDTO.sortTypes}">
                                    <option value="${sortType}">${sortType}</option>
                                </c:forEach>
                            </form:select>

                        </div>
                        --%>

                    <div class="row d-flex ms-auto me-auto me-lg-0 p-0 align-items-end mt-5 col-md-10 col-lg-3">
                        <button type="submit" class="btn button-submit text-uppercase mt-4">Szukaj</button>
                    </div>

                </div>

            </form:form>

            <hr/>

            <section id="offers" class="mt-4">

                <div class="offers row px-md-3">
                    <p class="display-5 text-left mt-5">
                        Moje oferty
                    </p>
                    <c:forEach begin="0" end="${pageSize-1}" varStatus="index">
                        <jsp:useBean id="offerDTOS" scope="request" type="java.util.List<com.aukcje.dto.OfferDTO>"/>
                        <c:if test="${offerDTOS.size() > index.index}">

                            <div class="col-12 col-md-6 col-lg-4 col-xl-3 mt-3 mb-2">

                                <div class="card g-5 mx-2">
                                    <a class="stretched-link" href="${pageContext.request.contextPath}/oferta/podglad/${offerDTOS.get(index.index).id}">

                                        <div class="m-0 p-0">
                                            <div class="img-container">
                                                <img class="card-img-top" src="${pageContext.request.contextPath}/files/img/offers/${offerDTOS.get(index.index).id}/1.png" alt="Card image">
                                            </div>
                                            <div class="card-body text-center">
                                                <p class="offertype-text mb-1 ">${offerDTOS.get(index.index).offerType.id == 1 ? "KUP TERAZ" : "AUKCJA"}</p>
                                                <p class="m-0">Cena:</p>
                                                <h4 class="card-title price">${offerDTOS.get(index.index).price}<span class="text-center">${offerDTOS.get(index.index).price*10 % 1 == 0 ? '0' : ''}zł</span> </h4>
                                                    <%--                                                <h4 class="card-title price text-center">${buyNowDTOS.get(index.index).price}zł</h4>--%>
                                                <div class="d-flex flex-row pt-3">
                                                    <p class="card-text text-left item-title pb-0 align-self-center">${offerDTOS.get(index.index).offerDetails.title}</p>
                                                    <a class="text-right ms-auto" id="observe-button-${offerDTOS.get(index.index).id}" href="#"> <i id="observe-icon-${offerDTOS.get(index.index).id}" class="observe-icon bi bi-eye"></i> </a>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>


                            </div>

                        </c:if>
                    </c:forEach>
                </div>
                <p class="text h1 mt-4 text-end">
                    DODAĆ PAGINACJĘ
                </p>

                </div>

            </section>


        </section>

    </section>

</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/files/js/user/main/main-page.js"></script>

</body>
</html>