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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/cart/cart.css">

</head>


<body>

<%@include file="../../../../fragments/navbar.jspf" %>
<jsp:useBean id="purchaseDTOS" scope="request" type="java.util.List<com.aukcje.dto.OfferPurchaseInfoDTO>"/>

<main>
    <section class="central container col-sm-12 col-md-10 col-xl-8">

        <section class="h-100 gradient-custom">
            <div class="container py-5">
                <div class="row d-flex justify-content-center my-4">
                    <div class="col">

                        <c:forEach var ="purchase" items = "${purchaseDTOS}">
                            <div class="card mb-4">
                                <div class="card-header py-3">
                                    <h2>${purchase.offer.offerDetails.title}</h2>
                                </div>
                                <div class="card-body">

                                    <div id="cart-offer_${purchase.offer.id}" data-offer-id_${purchase.offer.id}="${purchase.offer.id}" class="row cart-offer-card">
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

                                        <div class="col-lg-4 col-md-6 mb-4 mb-lg-0">
                                                <!-- Data -->
                                            <p>${purchase.offer.offerDetails.description}</p>
                                            <div class="align-self-bottom">
                                                <hr class="my-4" />
                                                <p> <strong>Data zakupu:</strong> ${purchase.purchaseTime.toLocalDate()}, ${purchase.purchaseTime.toLocalTime()}</p>
                                                <p> <strong>Status zamówienia:</strong> ${purchase.purchaseStatus.name}</p>
                                                <p> <strong>Sprzedawca:</strong> ${purchase.seller.username}</p>
                                            </div>

                                            <!-- Data -->
                                        </div>

                                        <div class="col-lg-2 col-md-6 mb-4 mb-lg-0 text-end align-self-center">
                                            <!-- Data -->
                                            <p><strong>Adres dostawy:</strong></p>
                                            <p class="mb-0">${purchase.address.firstName} ${purchase.address.lastName}</p>
                                            <p class="mb-0">${purchase.address.country.name}</p>
                                            <p class="mb-0">${purchase.address.city}, ${purchase.address.postalCode}</p>
                                            <p class="mb-0">${purchase.address.streetName}</p>
                                            <p class="mb-0">tel. ${purchase.address.phone}</p>
                                            <!-- Data -->
                                        </div>

                                        <div class="col-lg-3 col-md-6 mb-4 mb-lg-0 align-self-center">

                                            <p class="text-center pt-2 mb-4">${purchase.offer.quantity}szt.</p>

                                            <div class="d-flex justify-content-center">
                                                <h1 id="price_${purchase.offer.id}" class="price" data-pc-price_${purchase.offer.id}="${purchase.offer.price}">${purchase.offer.price * purchase.quantity}<span>${purchase.offer.price*10 % 1 == 0 ? '0' : ''} zł</span></h1>
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
<script src="${pageContext.request.contextPath}/files/js/user/cart/cart-offer.js"></script>
<script src="${pageContext.request.contextPath}/files/js/user/cart/purchase.js"></script>

</body>
</html>