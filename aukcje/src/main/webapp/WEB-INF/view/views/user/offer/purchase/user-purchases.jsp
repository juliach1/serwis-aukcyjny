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
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/offer/purchase/user-purchase.css">

</head>


<body>

<%@include file="../../../../fragments/navbar.jspf" %>
<jsp:useBean id="purchaseDTOS" scope="request" type="java.util.List<com.aukcje.dto.OfferPurchaseInfoDTO>"/>
<jsp:useBean id="purchaseStatusDTOS" scope="request" type="java.util.List<com.aukcje.dto.PurchaseStatusDTO>"/>

<main>
    <section class="central container mt-5 col-sm-12 col-md-10 col-xl-8">

        <section class="h-100 gradient-custom">
            <div class="container bg-light py-5 px-5">
                <div class="row d-flex justify-content-center my-4">
                    <div class="col">
                        <p class="display-3 text-center">
                                <c:choose>
                                <c:when test="${requestScope.bought == true}">
                                    Twoje zakupy
                                </c:when>
                                <c:when test="${requestScope.sold == true}">
                                        Sprzedane przedmioty
                                </c:when>
                            </c:choose>
                        </p>

                            <c:if test="${purchaseDTOS.size()<1}">
                                <div class="noresultbox p-5 my-5 border rounded-3">
                                    <p class=" display-8 text-danger">
                                        Nie kupiłeś jeszcze żadnego przedmiotu. Skorzystaj z wyszukiwarki i znajdź oferty, które pokochasz.
                                    </p>
                                </div>
                            </c:if>

                                <c:forEach var ="purchase" items = "${purchaseDTOS}" varStatus="index">
                                    <div class="my-5">
                                        <c:choose>
                                            <c:when test="${index.index==0}">
                                                <h1 class="purchase-date">${purchase.purchaseTime.toLocalTime()} ${purchase.purchaseTime.toLocalDate()}</h1>
                                            </c:when>
                                            <c:when test="${index.index>0 && purchase.purchaseTime != purchaseDTOS.get(index.index-1).purchaseTime}">
                                                <h1 class="purchase-date">${purchase.purchaseTime.toLocalTime()} ${purchase.purchaseTime.toLocalDate()}</h1>
                                            </c:when>
                                        </c:choose>
                                        <div class="card purchased-item-card">
                                            <div class="card-header py-3">
                                                <h2>${purchase.offer.offerDetails.title}</h2>
                                            </div>
                                            <div class="card-body py-lg-0">

                                                <div id="cart-offer_${purchase.offer.id}" data-offer-id_${purchase.offer.id}="${purchase.offer.id}" class="row cart-offer-card">
                                                    <div class="col-lg-3 col-md-12 mb-4 mb-lg-0 p-lg-0">
                                                        <div class="bg-image hover-overlay hover-zoom ripple rounded" data-mdb-ripple-color="light">
                                                            <div class="img-container">
                                                                <img class="card-img-top img-fluid" src="${pageContext.request.contextPath}/files/img/offers/${purchase.offer.id}/1.png" alt="Card image">
                                                            </div>

                                                            <a href="">
                                                                <div class="mask" style="background-color: rgba(251, 251, 251, 0.2)"></div>
                                                            </a>
                                                        </div>
                                                    </div>

                                                    <div class="col-lg-4 col-md-6 mb-4 mb-lg-0 pt-3">
                                                        <!-- Data -->
                                                        <p class="item-description-short">${purchase.offer.offerDetails.description}</p>
                                                        <div class="purchase-data align-self-bottom">
                                                            <hr class="my-4" />
                                                            <c:choose>
                                                                <c:when test="${requestScope.bought==true}">
                                                                    <p class="mt-2">
                                                                        <strong>Sprzedawca:</strong>
                                                                        <a href="">
                                                                                ${purchase.seller.username}
                                                                        </a>
                                                                    </p>
                                                                </c:when>
                                                                <c:when test="${requestScope.sold==true}">
                                                                    <strong>Kupujący:</strong>
                                                                    <p class="mt-2">
                                                                        <a href="">
                                                                                ${purchase.buyer.username}
                                                                        </a>
                                                                    </p>
                                                                </c:when>
                                                            </c:choose>
                                                            <section class="status-section">
                                                                <div class="d-inline-flex">
                                                                    <strong>Status zamówienia:</strong>
                                                                    <p id="purchase-status-name-${purchase.id}" class="ms-1">${purchase.purchaseStatus.name}</p></div>

                                                                <c:choose>

                                                                    <c:when test="${purchase.purchaseStatus.name == 'Nowe'}">

                                                                        <c:choose>
                                                                            <c:when test="${purchase.seller.username == pageContext.request.userPrincipal.name}">
                                                                                <button id="change-status-btn-${purchase.id}"
                                                                                        type="submit"
                                                                                        onclick="changeOfferStatus(${purchase.id},2)"
                                                                                        class="btn button-submit  h-auto ms-2 w-100 mb-3">
                                                                                    Wysłano zamówienie
                                                                                </button>
                                                                            </c:when>
                                                                        </c:choose>

                                                                    </c:when>

                                                                    <c:when test="${purchase.purchaseStatus.name == 'Wysłane'}">
                                                                        <c:choose>
                                                                            <c:when test="${purchase.buyer.username == pageContext.request.userPrincipal.name}">
                                                                                <button id="change-status-btn-${purchase.id}"
                                                                                        type="submit"
                                                                                        onclick="changeOfferStatus(${purchase.id},3)"
                                                                                        class="btn button-submit h-auto ms-2 w-100 mb-3">
                                                                                    Odebrano zamówienie
                                                                                </button>
                                                                            </c:when>
                                                                        </c:choose>
                                                                    </c:when>

                                                                    <c:when test="${purchase.purchaseStatus.name == 'Zrealizowane'}">
                                                                        <c:choose>
                                                                            <c:when test="${purchase.buyer.username == pageContext.request.userPrincipal.name}">
                                                                                <select class="form-select w-50 w-lg-75 w-md-100 w-sm-50" aria-label="Default select example">
                                                                                    <option selected>Oceń sprzedającego...</option>
                                                                                    <option value="5">5</option>
                                                                                    <option value="4">4</option>
                                                                                    <option value="3">3</option>
                                                                                    <option value="2">2</option>
                                                                                    <option value="1">1</option>
                                                                                </select>
                                                                            </c:when>
                                                                        </c:choose>
                                                                    </c:when>

                                                                </c:choose>
                                                            </section>

                                                        </div>
                                                    </div>

                                                    <div class="col-lg-2 col-md-6 mb-4 mb-lg-0 text-end align-self-center">
                                                        <p><strong>Adres dostawy:</strong></p>
                                                        <p class="mb-0">${purchase.address.firstName} ${purchase.address.lastName}</p>
                                                        <p class="mb-0">${purchase.address.country.name}</p>
                                                        <p class="mb-0">${purchase.address.city}, ${purchase.address.postalCode}</p>
                                                        <p class="mb-0">${purchase.address.streetName}</p>
                                                        <p class="mb-0">tel. ${purchase.address.phone}</p>
                                                    </div>

                                                    <div class="col-lg-3 col-md-6 mb-4 mb-lg-0 align-self-center">
                                                        <p class="text-center pt-2 mb-4">${purchase.offer.quantity}szt.</p>
                                                        <div class="d-flex justify-content-center">
                                                            <h1 class="price display-3" >${purchase.offer.price * purchase.quantity}<span>${purchase.offer.price*10 % 1 == 0 ? '0' : ''} zł</span></h1>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>

                    </div>

                </div>
            </div>
        </section>

    </section>

</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/files/js/user/purchase/purchase.js"></script>

</body>
</html>