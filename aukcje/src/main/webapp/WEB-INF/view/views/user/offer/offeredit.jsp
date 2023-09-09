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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/offer/offer-edit.css">



</head>


<body onload="setChosenCategoryId(${offerDTO.category.id})">

<%@include file="../../../fragments/navbar.jspf" %>

<main>
    <%--@elvariable id="offerAddModel" type="com.aukcje.model.OfferAddModel"--%>
    <%--@elvariable id="itemConditionDTOS" type="java.util.List<com.aukcje.dto.ItemConditionDTO>"--%>
        <%--@elvariable id="offerTypeDTOS" type="java.util.List<com.aukcje.dto.OfferTypeDTO>"--%>
      <%--@elvariable id="badCategory" type="java.lang.String"--%>


        <section class="central container col-11 col-sm-10 col-md-9 col-lg-8 col-xl-7">
        <section id="new-offer" class="bg-light p-4 pt-5 mt-5">

            <div class="mx-3 mx-sm-4 mx-md-5 px-md-2 px-lg-3 px-xl-4">

                <p class="display-3 text-center mt-3 mb-5">
                    Edytuj przedmiot
                </p>

            <form:form method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/oferta/dodaj/przetworz" modelAttribute="offerAddModel">

                    <form:hidden path="id"/>
                    <form:hidden path="categoryId" id="chosen-category-id"/>
                    <form:hidden path="isBaseCategoryChosen" id="base-category-chosen"/>


                    <div class="row row-cols-md-2 row-cols-1 pb-3">

                        <div class="d-flex col mt-4 justify-content-center align-items-center">
                            <c:choose>

                                <c:when test="${offerDTO.offerType.id == 2}">
                                    <form:radiobutton id="buy-now-check" path="offerType" class="me-2"  onchange="setPriceInputVisibility(this)" value="${offerTypeDTOS.get(0).name}"/> ${offerTypeDTOS.get(0).name}
                                    <form:radiobutton id="auction-check" path="offerType" class="ms-5 me-2" checked="checked" onchange="setPriceInputVisibility(this)" value="${offerTypeDTOS.get(1).name}"/> ${offerTypeDTOS.get(1).name}
                                </c:when>
                                <c:otherwise>
                                    <form:radiobutton id="buy-now-check" path="offerType" class="me-2"  checked="checked" onchange="setPriceInputVisibility(this)" value="${offerTypeDTOS.get(0).name}"/> ${offerTypeDTOS.get(0).name}
                                    <form:radiobutton id="auction-check" path="offerType" class="ms-5 me-2" onchange="setPriceInputVisibility(this)" value="${offerTypeDTOS.get(1).name}"/> ${offerTypeDTOS.get(1).name}
                                </c:otherwise>

                            </c:choose>


                            <form:errors element="offerType" cssClass="error" />
                        </div>

                        <div class="col pb-4 pb-md-0">
                            <label for="titleInput" class="form-label">Tytuł oferty</label>
                            <form:input type="text" value="${offerDTO.offerDetails.title}" path="title" class="form-control" id="titleInput" placeholder="Podaj tytuł oferty"/>
                            <form:errors path="title" cssClass="error"/>
                        </div>

                    </div>

                    <div class="pb-4">
                        <label for="descriptionTextArea" class="form-label">Opis oferty</label>
                        <textarea type="text" path="description" style="height: 150px" class="form-control" id="descriptionTextArea" placeholder="Podaj kupującym więcej szczegółów na temat sprzedawanego przedmiotu">${offerDTO.offerDetails.description}</textarea>
                        <form:errors path="description" cssClass="error"/>
                    </div>


                    <div class="row pb-4">

                        <div class="col align-self-end p-0">
                                <div class="d-flex">
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
                                        <c:choose>
                                            <c:when test="${ empty offerDTO.category.id }">
                                                ${offerDTO.category.name}
                                                Wybrana kategoria
                                            </c:when>
                                            <c:otherwise>
                                                ${offerDTO.category.name}
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                        </div>


                        <div class="col-md-4 col-lg-5 col-xl-6">
                            <label for="itemConditionSelect" class="form-label">Stan</label>
                            <form:select type="text" path="itemCondition" class="form-select form-control" id="itemConditionSelect">
                                <option value="${offerDTO.offerDetails.itemCondition.name}" selected>${offerDTO.offerDetails.itemCondition.name}</option>
                                <c:forEach var = "conditionVar" items = "${itemConditionDTOS}">
                                    <c:if test="${conditionVar.name != offerDTO.offerDetails.itemCondition.name}">
                                        <option value="${conditionVar.name}"> ${conditionVar.name} </option>
                                    </c:if>
                                </c:forEach>
                            </form:select>
                            <form:errors element="itemCondition" cssClass="error" />
                        </div>

                    </div>


<%--                <c:if test="${not empty badCategory}">--%>
<%--                <p class="d-flex-row error">${badCategory}</p>--%>
<%--                </c:if>--%>

                    <form:errors path="categoryId" cssClass="error"/>


                <section id="pricing" class="row pb-5">

                        <div class="col">
                            <label id="buy-now-price-label" for="priceInput" class="form-label">Cena [zł/szt]</label>
                            <label id="auction-price-label" for="priceInput" class="form-label d-none">Cena początkowa [zł]</label>
                            <form:input type="text" value="${offerDTO.price}" path="price" class="form-control" id="priceInput" placeholder="Podaj cenę"/>
                            <form:errors path="price" cssClass="error" />
                        </div>

                        <div class="col">
                            <label for="quantityInput" class="form-label">Liczba przedmiotów</label>
                            <form:input type="number" value="${offerDTO.quantity}" step="1" path="quantity" class="form-control" id="quantityInput"/>
                            <form:errors path="quantity" cssClass="error" />
                        </div>

                    </section>

                    <div class="d-flex flex-row">

                        <c:if test="${offerDTO.offerPhoto.size()-1 >= 0}">
                            <c:forEach begin="0" end="${offerDTO.offerPhoto.size()-1}" varStatus="index">
                                <img class="edit-offer-img" src="${pageContext.request.contextPath}/files/img/offers/${offerDTO.id}/${index.index+1}.png" class="img-thumbnail" alt="Zdjęcie przedmiotu">
                            </c:forEach>
                        </c:if>

                    </div>

                    <div class="form-check mt-4 pb-4 ps-0">
                        <input type="file" name="file" id="file" accept="image/png, image/jpeg">
                    </div>

                <div class="row mt-3 mb-3 justify-content-center">
                    <button type="submit" class="btn button-submit col-sm-5 col-lg-4 mt-5 text-uppercase me-sm-2">Dodaj ogłoszenie</button>
                    <button type="button" class="btn button-cancel col-sm-5 col-lg-4 mt-3 mt-sm-5 text-capitalize ms-sm-2" onclick="location.href='${pageContext.request.contextPath}/uzytkownik/strona-glowna'">Anuluj</button>
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