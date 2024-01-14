<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Podgląd użytkownika</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/bstreeview.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/main-page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/profile/profile.css">


</head>


<body>

<%@include file="../../../fragments/navbar.jspf" %>
<jsp:useBean id="userDTO" scope="request" type="com.aukcje.dto.UserDTO"/>
<jsp:useBean id="auctionDTOS" scope="request" type="java.util.List<com.aukcje.dto.OfferDTO>"/>
<jsp:useBean id="buyNowDTOS" scope="request" type="java.util.List<com.aukcje.dto.OfferDTO>"/>
<jsp:useBean id="isPrincipalProfile" scope="request" type="java.lang.Boolean"/>


<jsp:useBean id="offersNumber" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="ratingsNumber" scope="request" type="java.lang.Long"/>

<main>
    <section class="central bg-light container col-sm-12 col-md-10 col-xl-8">

            <section id="user-view" class="p-5 mt-5">

                <section id="page-title" class="text-center">
                    <h2 class="mt-5">
                        Profil użytkownika
                    </h2>
                    <p class="display-5">
                        ${userDTO.username}
                    </p>
                </section>
                <section id="user-info" class="row my-5">
                    <div class="col-xl-4 col-lg-5 col-md-6 col-sm-12">
                        <c:choose>
                            <c:when test="${userDTO.avatarPath==null || userDTO.avatarPath==''}">
                                <img id="profile-photo-ph" src="${pageContext.request.contextPath}/files/img/users/avatar-placeholder.png" alt="Użytkownik nie ustawił zdjęcia profilowego" class="d-block w-100">
                            </c:when>
                            <c:otherwise>
                                <img id="profile-photo" src="${pageContext.request.contextPath}/files/img/users/${userDTO.id}/${userDTO.id}.png" alt="Zdjęcie profilowe użytkownika" class="d-block w-100">
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${isPrincipalProfile}">
                            <form:form method="post" class="d-flex" enctype="multipart/form-data" action="${pageContext.request.contextPath}/uzytkownik/zmien-zdjecie">
                            <div class="form-check mt-4 pb-4 ps-0">
                                <input onchange="showSubmitBtn()" type="file" name="file" id="file" accept="image/png, image/jpeg">
                            </div>
                                <input id="file-submit" type="submit" style="display: none" value="ok" class="w-50"/>
                            </form:form>
                        </c:if>
                    </div>

                    <div class="col">
                        <div class="row">
                            <div class="d-inline-flex">
                                <strong class="mx-3">Liczba ofert: </strong> <p>${offersNumber}</p>
                            </div>
                            <div class="d-inline-flex">
                                <strong class="mx-3">Ocena: </strong>
                                <c:choose>
                                    <c:when test="${userDTO.averageRate != null}">
                                        <p>${userDTO.averageRate} na podstawie ${ratingsNumber} ocen</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Użytkownik przeprowadził zbyt mało transakcji</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="d-inline-flex">
                                <strong class="mx-3">Data rejestracji:</strong>
                                <p>${userDTO.registrationDate.dayOfMonth}-${userDTO.registrationDate.monthValue}-${userDTO.registrationDate.year}r.</p>
                            </div>
                        </div>
                    </div>

                    <c:if test="${isPrincipalProfile}">
                        <div class="btn btn-submit">
                            <button id="btn-edit-profile-${userDTO.id}"
                                    type="submit"
                                    onclick=""
                                    class="btn w-100 button-submit h-auto">
                                Edytuj swój profil
                            </button>
                        </div>
                    </c:if>

                </section>

                <section>
                    <section id="buy-now" class="p-5 mt-5">

                        <div class="offers row px-md-3">
                            <p class="display-5 text-left mt-5">
                                Oferty "Kup teraz"
                            </p>
                            <c:if test="${buyNowDTOS.size()<1}">
                                <div class="noresultbox p-5 my-5 border rounded-3">
                                    <p class=" display-8 text-danger"> Użytkownik nie posiada aktualnie aktywnych ofert "kup teraz". </p>
                                </div>
                            </c:if>

                            <c:forEach var = "offerDTO" items = "${buyNowDTOS}">
                                <div class="col-12 col-md-6 col-lg-4 col-xl-3 mt-3 mb-2">
                                    <div class="card g-5 mx-2">
                                        <a class="stretched-link" href="${pageContext.request.contextPath}/oferta/podglad/${offerDTO.id}"></a>
                                        <div class="m-0 p-0">
                                            <div class="img-container">
                                                <img class="card-img-top" src="${pageContext.request.contextPath}/files/img/offers/${offerDTO.id}/1.png" alt="Card image">
                                            </div>
                                            <c:choose>
                                                <c:when test="${isPrincipalProfile}">
                                                    <div class="card-body text-center mb-0 pb-0">
                                                        <p class="offertype-text mb-1 ">${offerDTO.offerType.id == 1 ? "KUP TERAZ" : "AUKCJA"}</p>
                                                        <p class="m-0">Cena:</p>
                                                        <h4 class="card-title price">${offerDTO.price}<span class="text-center">${offerDTO.price*10 % 1 == 0 ? '0' : ''}zł</span> </h4>
                                                        <div class="d-flex flex-row pt-3">
                                                            <div class="offer-title-card">
                                                                <p class="card-text text-start item-title pb-0">${offerDTO.offerDetails.title}</p>
                                                            </div>
                                                            <div class="d-inline-flex ms-auto ps-3" style="z-index: 2; position: relative;">
                                                                <a id="edit-button_${offerDTO.id}" href="${pageContext.request.contextPath}/oferta/edytuj/${offerDTO.id}" title="Edytuj ofertę"> <i class="edit-icon card-action-icon bi bi-pencil-square me-2"></i> </a>
                                                                <a id="remove-button_${offerDTO.id}" title="Usuń ofertę" data-bs-toggle="modal" data-bs-target="#myModal_${offerDTO.id}"> <i id="remove-icon_${offerDTO.id}" class="remove-icon card-action-icon bi bi-trash3"></i> </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
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
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal" id="myModal_${offerDTO.id}">
                                    <div class="modal-dialog">
                                        <div class="modal-content">

                                            <!-- Modal Header -->
                                            <div class="modal-header">
                                                <h4 class="modal-title">Usunąć ofertę?</h4>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                            </div>
                                            <!-- Modal body -->
                                            <div class="modal-body">
                                                Czy na pewno chcesz usunąć wybraną ofertę? Jest to nieodwracalne.
                                            </div>

                                            <!-- Modal footer -->
                                            <div class="modal-footer">
                                                <button type="button" class="btn button-submit" onclick="deleteOffer(${offerDTO.id})">Tak</button>
                                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Nie</button>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                            <p class="text h3 mt-4 text-end">
                                <a id="user-auctions-btn_${userDTO.id}" title="Edytuj adres"
                                   href="${pageContext.request.contextPath}/oferta/oferty-uzytkownika/${userDTO.id}?isAuction=false"
                                   role="button">
                                    Zobacz wszystkie oferty "kup teraz" użytkownika...
                                </a>
                            </p>
                        </div>

                    </section>

                    <section id="auctions" class="p-5 mt-5">

                        <div class="offers row px-md-3">
                            <p class="display-5 text-left mt-5">
                                Aukcje
                            </p>
                            <c:if test="${auctionDTOS.size()<1}">
                                <div class="noresultbox p-5 my-5 border rounded-3">
                                    <p class=" display-8 text-danger"> Użytkownik nie posiada aktualnie aktywnych aukcji.  </p>
                                </div>
                            </c:if>

                            <c:forEach var = "offerDTO" items = "${auctionDTOS}">
                                <div class="col-12 col-md-6 col-lg-4 col-xl-3 mt-3 mb-2">
                                    <div class="card g-5 mx-2">
                                        <a class="stretched-link" href="${pageContext.request.contextPath}/oferta/podglad/${offerDTO.id}"></a>
                                        <div class="m-0 p-0">
                                            <div class="img-container">
                                                <img class="card-img-top" src="${pageContext.request.contextPath}/files/img/offers/${offerDTO.id}/1.png" alt="Card image">
                                            </div>
                                            <c:choose>
                                                <c:when test="${isPrincipalProfile}">
                                                    <div class="card-body text-center mb-0 pb-0">
                                                        <p class="offertype-text mb-1 ">${offerDTO.offerType.id == 1 ? "KUP TERAZ" : "AUKCJA"}</p>
                                                        <p class="m-0">Cena:</p>
                                                        <h4 class="card-title price">${offerDTO.price}<span class="text-center">${offerDTO.price*10 % 1 == 0 ? '0' : ''}zł</span> </h4>
                                                        <div class="d-flex flex-row pt-3">
                                                            <div class="offer-title-card">
                                                                <p class="card-text text-start item-title pb-0">${offerDTO.offerDetails.title}</p>
                                                            </div>
                                                            <div class="d-inline-flex ms-auto ps-3" style="z-index: 2; position: relative;">
                                                                <a id="edit-button_${offerDTO.id}" href="${pageContext.request.contextPath}/oferta/edytuj/${offerDTO.id}" title="Edytuj ofertę"> <i class="edit-icon card-action-icon bi bi-pencil-square me-2"></i> </a>
                                                                <a id="remove-button_${offerDTO.id}" title="Usuń ofertę" data-bs-toggle="modal" data-bs-target="#myModal_${offerDTO.id}"> <i id="remove-icon_${offerDTO.id}" class="remove-icon card-action-icon bi bi-trash3"></i> </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
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
                                                </c:otherwise>
                                            </c:choose>

                                        </div>
                                    </div>
                                </div>
                                <div class="modal" id="myModal_${offerDTO.id}">
                                    <div class="modal-dialog">
                                        <div class="modal-content">

                                            <!-- Modal Header -->
                                            <div class="modal-header">
                                                <h4 class="modal-title">Usunąć ofertę?</h4>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                            </div>
                                            <!-- Modal body -->
                                            <div class="modal-body">
                                                Czy na pewno chcesz usunąć wybraną ofertę? Jest to nieodwracalne.
                                            </div>

                                            <!-- Modal footer -->
                                            <div class="modal-footer">
                                                <button type="button" class="btn button-submit" onclick="deleteOffer(${offerDTO.id})">Tak</button>
                                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Nie</button>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                            <p class="text h3 mt-4 text-end">
                                <a id="user-auctions-btn_${userDTO.id}" title="Edytuj adres"
                                   href="${pageContext.request.contextPath}/oferta/oferty-uzytkownika/${userDTO.id}?isAuction=true"
                                   role="button">
                                    Zobacz wszystkie aukcje użytkownika...
                                </a>
                            </p>

                        </div>
                    </section>
                </section>



        </section>

    </section>

</main>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/files/js/user/user/user-profile.js"></script>
<script src="${pageContext.request.contextPath}/files/js/user/user/user-profile.js"></script>
<script src="${pageContext.request.contextPath}/files/js/user/offer/offer-actions.js"></script>

</body>
</html>