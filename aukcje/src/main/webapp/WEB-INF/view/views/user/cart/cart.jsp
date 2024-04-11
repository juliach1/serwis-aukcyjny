<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Koszyk - SellBuy</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/cart/cart.css">

</head>


<body>

<jsp:useBean id="cartOfferDTOS" scope="request" type="java.util.List<com.aukcje.dto.CartOfferDTO>"/>
<jsp:useBean id="addressDTOS" scope="request" type="java.util.List<com.aukcje.dto.AddressDTO>"/>
<%--@elvariable id="allItemsPrice" type="java.lang.Double"--%>

<%@include file="../../../fragments/navbar.jspf" %>

<main>
    <section class="central container col-sm-12 col-md-10 col-xl-8">

        <section class="h-100 gradient-custom">
            <div class="container py-5">
                <div class="row d-flex justify-content-center my-4">
                    <div class="col-md-8">
                        <div class="card mb-4">
                            <div class="card-header py-3">
                                <h5 class="mb-0">Liczba ofert w koszyku: <h5 id="all-pcs">${cartOfferDTOS.size()}</h5> </h5>
                            </div>
                            <div class="card-body">
                                <c:forEach var = "purchase" items = "${cartOfferDTOS}">
                                    <div id="cart-offer_${purchase.id}" data-offer-id_${purchase.id}="${purchase.offer.id}" class="row cart-offer-card">
                                    <div class="col-lg-3 col-md-12 mb-4 mb-lg-0">
                                        <!-- Image -->
                                        <div class="bg-image hover-overlay hover-zoom ripple rounded" data-mdb-ripple-color="light">
                                            <div class="img-container">
                                                <img class="card-img-top img-fluid" src="${pageContext.request.contextPath}/files/img/offers/${purchase.offer.id}/1.png" alt="Card image">
                                            </div>

                                            <a href="#!">
                                                <div class="mask" style="background-color: rgba(251, 251, 251, 0.2)"></div>
                                            </a>
                                        </div>
                                        <!-- Image -->
                                    </div>

                                    <div class="col-lg-5 col-md-6 mb-4 mb-lg-0">
                                        <!-- Data -->
                                        <p><strong>${purchase.offer.offerDetails.title}</strong></p>
                                        <p>dostępnych ${purchase.offer.quantity}szt.</p>
                                        <div class="d-flex align-items-center">
                                            <a class="remove-button text-right me-5" title="Usuń przedmiot z koszyka" id="remove-button_${purchase.id}" onclick="removeCartOffer(${purchase.id})" role="button"> <i id="remove-icon-${purchase.id}" class="card-action-icon remove-icon bi bi-trash3"></i> </a>
                                            <a class="observe-button text-right" title="Przenieś do obserwowanych" id="observe-button_${purchase.id}" onclick="moveFromCartToFavoriteOffers(${purchase.id}, ${purchase.offer.id})"> <i id="observe-icon_${purchase.id}" class="card-action-icon observe-icon bi bi-eye"></i> </a>
                                        </div>
                                       <!-- Data -->
                                    </div>

                                    <div class="col-lg-4 col-md-6 mb-4 mb-lg-0">
                                        <!-- Quantity -->
                                        <div class="d-flex justify-content-center">
                                            <div id="quantity-selection_${purchase.id}" class="d-inline-flex justify-content-center col-4 col-md-2">
                                                <button id="button-quantity-down_${purchase.id}" onclick="decrementQuantity(${purchase.id})" type="button" class="btn button-quantity button-quantity-down col">
                                                    <i class="bi bi-caret-down"></i>
                                                </button>

                                                <input type="number" class="quantity form-control" id="quantity_${purchase.id}" name="quantity" value="${purchase.quantity}" min="1" max="${purchase.offer.quantity}" readonly>

                                                <button id="button-quantity-up_${purchase.id}" onclick="incrementQuantity(${purchase.id})" type="button" class="btn button-quantity-up button-quantity col">
                                                    <i class="bi bi-caret-up"></i>
                                                </button>
                                            </div>
                                        </div>

                                        <p class="text-center pt-2 mb-4">/${purchase.offer.quantity}szt.</p>

                                        <div class="d-flex justify-content-center">
                                            <h1 id="price_${purchase.id}" class="price" data-pc-price_${purchase.id}="${purchase.offer.price}">${purchase.offer.price * purchase.quantity}<span>${purchase.offer.price*10 % 1 == 0 ? '0' : ''} zł</span></h1>
                                        </div>
                                    </div>
                                        <hr class="my-4" />
                                    </div>
                                </c:forEach>

                            </div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-body">
                                <p><strong>Planowana data dostawy</strong></p>
                                <p class="mb-0">12.10.2020 - 14.10.2020</p>
                            </div>
                        </div>
                        <div class="card mb-4 mb-lg-0">
                            <div class="card-body">
                                <p><strong>Akceptowane formy płatności</strong></p>
                                <img class="me-2" width="45px"
                                     src="https://mdbcdn.b-cdn.net/wp-content/plugins/woocommerce-gateway-stripe/assets/images/visa.svg"
                                     alt="Visa" />
                                <img class="me-2" width="45px"
                                     src="https://mdbcdn.b-cdn.net/wp-content/plugins/woocommerce-gateway-stripe/assets/images/amex.svg"
                                     alt="American Express" />
                                <img class="me-2" width="45px"
                                     src="https://mdbcdn.b-cdn.net/wp-content/plugins/woocommerce-gateway-stripe/assets/images/mastercard.svg"
                                     alt="Mastercard" />
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">

                        <div class="card mb-4">

                            <div class="card-header py-3">
                                <h5 class="mb-0">Adres dostawy: </h5>
                            </div>

                            <div class="card-body">

                                    <ul class="list-group list-group-flush">
                                        <c:choose>
                                            <c:when test="${empty addressDTOS}">
                                                <p class="text-danger">Brak dostępnych adresów.</p>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var = "addressDTO" items = "${addressDTOS}" varStatus="index">
                                                    <c:if test="${index.index!=0}">
                                                        <hr class="my-4" />
                                                    </c:if>
                                                    <li class="d-flex align-items-center mx-4">
                                                        <div class="pr-3">
                                                            <input type="radio" name="addressSelection" value="${addressDTO.id}">
                                                        </div>
                                                        <div class="col ms-4 text-end">
                                                            <div class="address text-right">
                                                                <p class="mb-0">${addressDTO.firstName} ${addressDTO.lastName}</p>
                                                                <p class="mb-0">${addressDTO.country.name}</p>
                                                                <p class="mb-0">${addressDTO.city}, ${addressDTO.postalCode}</p>
                                                                <p class="mb-0">${addressDTO.streetName}</p>
                                                                <p class="mb-0">tel. ${addressDTO.phone}</p>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                        <p id="addressNotSelectedError" class="error" style="display: none">Wybierz adres</p>
                                    </ul>
                            </div>
                        </div>

                        <div class="card mb-4">
                            <div class="card-header py-3">
                                <h5 class="mb-0">Podsumowanie koszyka</h5>
                            </div>
                            <div class="card-body">
                                <ul class="list-group list-group-flush">
                                    <li
                                            class="list-group-item d-flex justify-content-between align-items-center border-0 px-0 mb-3">
                                        <div>
                                            <strong>Całkowita kwota</strong>
                                        </div>
                                        <p><strong id="all-items-price">${allItemsPrice}<span>${allItemsPrice*10 % 1 == 0 ? '0' : ''} zł</span></strong></p>
                                    </li>
                                </ul>

                                <button id="purchaseBtn" type="button" onclick="buyItems()" class="btn btn-primary btn-lg btn-block w-100">
                                    Do kasy
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </section>

</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/files/js/user/cart/cart-offer.js"></script>
<script src="${pageContext.request.contextPath}/files/js/user/cart/purchase.js"></script>

</body>
</html>