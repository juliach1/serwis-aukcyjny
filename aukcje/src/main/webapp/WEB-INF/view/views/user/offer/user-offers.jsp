<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:useBean id="userDTO" scope="request" type="com.aukcje.dto.UserDTO"/>
    <title>Oferty użytkownika ${userDTO.username} - SellBuy</title>

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
<jsp:useBean id="userDTO" scope="request" type="com.aukcje.dto.UserDTO"/>
<jsp:useBean id="isAuction" scope="request" type="java.lang.Boolean"/>


<main>
    <section class="central bg-light container col-sm-12 col-md-10 col-xl-8">

            <section id="offers" class="p-5 mt-5">

                <div class="offers row px-md-3">
                    <p class="display-5 text-left mt-5">
                        <c:choose>
                            <c:when test="${isAuction}">
                                Aukcje użytkownika ${userDTO.username}
                            </c:when>
                            <c:otherwise>
                                Oferty "kup teraz" użytkownika ${userDTO.username}
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <jsp:useBean id="offerDTOS" scope="request" type="java.util.List<com.aukcje.dto.OfferDTO>"/>

                    <c:if test="${offerDTOS.size()<1}">
                        <div class="noresultbox p-5 my-5 border rounded-3">
                            <p class=" display-8 text-danger">
                                <c:choose>
                                    <c:when test="${isAuction}">
                                        Użytkownik ${userDTO.username} nie posiada aktywnych aukcji.
                                    </c:when>
                                    <c:otherwise>
                                        Użytkownik ${userDTO.username} nie posiada aktywnych ofert "kup teraz".
                                    </c:otherwise>
                                </c:choose>

                            </p>
                        </div>
                    </c:if>

                    <c:forEach var = "offerDTO" items = "${offerDTOS}">
                        <div class="col-12 col-md-6 col-lg-4 col-xl-3 mt-3 mb-2">
                            <div class="card g-5 mx-2">
                                <a class="stretched-link" href="${pageContext.request.contextPath}/oferta/podglad/${offerDTO.id}"></a>
                                <div class="m-0 p-0">
                                    <div class="img-container">
                                        <img class="card-img-top" src="${pageContext.request.contextPath}/files/img/offers/${offerDTO.id}/1.png" alt="Card image">
                                    </div>
                                    <div class="card-body text-center mb-0 pb-0">
                                        <p class="offertype-text mb-1 ">${offerDTO.offerType.id == 1 ? "KUP TERAZ" : "AUKCJA"}</p>
                                        <p class="m-0">Cena:</p>
                                        <h4 class="card-title price">${offerDTO.price}<span class="text-center">${offerDTO.price*10 % 1 == 0 ? '0' : ''}zł</span> </h4>
                                        <div class="d-flex flex-row align-items-center">
                                            <div class="offer-title-card">
                                                <p class="card-text text-start item-title pb-0">${offerDTO.offerDetails.title}</p>
                                            </div>
                                            <div class="d-inline-flex ms-auto ps-3" style="z-index: 2; position: relative;">
                                                <a id="observe-button_${offerDTO.id}" onclick="changeObservationStatus(${offerDTO.id})" title="${offerDTO.isFavorite ? 'Usuń z' : 'Dodaj do'} obserwowanych"> <i id="observe-icon_${offerDTO.id}" class="card-action-icon observe-icon bi bi-eye${offerDTO.isFavorite ? '-fill' : ''}"></i> </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

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
<%--<script src="${pageContext.request.contextPath}/files/js/user/main/main-page.js"></script>--%>
<script src="${pageContext.request.contextPath}/files/js/user/offer/offer-actions.js"></script>


</body>
</html>