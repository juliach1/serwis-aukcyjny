<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>${offerDTO.offerDetails.title} - SellBuy</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/main-page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/offer/offer.css">

</head>


<body>
<%@include file="../../../fragments/navbar.jspf" %>

<main>

    <%--@elvariable id="offerDTO" type="com.aukcje.dto.OfferDTO"--%>

    <section class="central container col-sm-12 col-md-9 col-xl-8">

        <section id="offer-display" class="bg-light p-5">

            <header class="mb-3">


                <section class="d-flex align-items-end">

                    <div class="flex-column flex-fill col-md-6 col-lg-5 col-xl-4">
                        <p class="display-6">
                            ${offerDTO.offerDetails.title}
                        </p>

                        <h4 class="">
                            <c:forEach begin="0" end="${offerDTO.categoryPath.size()-1}" varStatus="index">
                                <c:choose>
                                    <c:when test="${index.index != 0}">
                                        →  ${offerDTO.categoryPath.get(index.index).name}
                                    </c:when>
                                    <c:otherwise>
                                        ${offerDTO.categoryPath.get(index.index).name}
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </h4>
                    </div>


                     <c:if test="${offerDTO.offerType.name=='aukcja'}">
                         <div class="ps-3 col-md-6 col-lg-5 col-xl-4 pb-0 mb-0 text-end">
                             <p class="p-0 m-0 fw-bold">Koniec licytacji:</p>
                             <p class="p-0 m-0">${offerDTO.endDate.toLocalDate()} ${offerDTO.endDate.toLocalTime()}</p>
                             <p class="p-0 m-0 mt-2 text-danger">
                                 <c:choose>

                             <c:when test="${offerDTO.daysLeft > 0}">
                                 Pozostało ${offerDTO.daysLeft} dni
                             </c:when>
                             <c:otherwise>
                                 Licytacja zakończona
                             </c:otherwise>
                            </c:choose>
                             </p>

                         </div>
                     </c:if>
                </section>

            </header>

            <main class="row">

                <section class="left-part col-md-6 col-lg-7 col-xl-8 ">

                    <div id="photos-carousel" class="carousel slide" data-bs-ride="true">
                        <c:choose>

                            <c:when test="${offerDTO.offerPhotoDTO.size() > 0}">

                                <div class="carousel-indicators">
                                    <c:forEach begin="0" end="${offerDTO.offerPhotoDTO.size()-1}" varStatus="index">

                                    <c:choose>
                                        <c:when test="${index.index == 0}">
                                            <button type="button" data-bs-target="#photos-carousel" data-bs-slide-to="${index.index}" class="active"></button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="button" data-bs-target="#photos-carousel" data-bs-slide-to="${index.index}" class=""></button>
                                        </c:otherwise>
                                    </c:choose>

                                    </c:forEach>

                                </div>

                                <div class="carousel-inner">
                                    <c:forEach begin="0" end="${offerDTO.offerPhotoDTO.size()-1}" varStatus="index">
                                        <c:choose>
                                            <c:when test="${index.index == 0}">
                                                <div class="carousel-item active">
                                                    <img src="${pageContext.request.contextPath}/files/img/offers/${offerDTO.id}/${index.index+1}.png" alt="Zdjęcie przedmiotu" class="d-block w-100">
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="carousel-item">
                                                    <img src="${pageContext.request.contextPath}/files/img/offers/${offerDTO.id}/${index.index+1}.png" alt="Zdjęcie przedmiotu" class="d-block w-100">
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>

                                <div class="carousel-indicators">
                                    <button type="button" data-bs-target="#photos-carousel" data-bs-slide-to="0" class="active"></button>
                                </div>
                                <div class="carousel-inner">
                                        <div class="carousel-item active">
                                            <img src="${pageContext.request.contextPath}/files/img/offers/offer-photo-placeholder.png" alt="Brak zdjęć przedmiotu" class="d-block w-100">
                                        </div>
                                </div>

                            </c:otherwise>

                        </c:choose>

                        <button class="carousel-control-prev" type="button" data-bs-target="#photos-carousel" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon"></span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#photos-carousel" data-bs-slide="next">
                            <span class="carousel-control-next-icon"></span>
                        </button>

                    </div>

                    <div id="offer-desc" class="my-5 pb-3">
                        <h3>
                            ${offerDTO.offerDetails.description}
                        </h3>

                    </div>
                </section>

                <section class="right-part col-md">
                     <c:choose>
                            <c:when test="${offerDTO.offerType.name=='kup teraz'}">
                                <div class="right-part-panel mb-3" id="buy-panel">
                                    <div class="text-center shadow px-2 py-3 py-md-4 py-lg-5">

                                        <p>Cena:</p>


                                        <h1>${offerDTO.price}<span class="text-center">${offerDTO.price*10 % 1 == 0 ? '0' : ''} zł</span> </h1>

                                        <div class=" pt-4 my-2">

                                            <div id="quantity-selection" class="d-inline-flex justify-content-center col-4 col-md-2">
                                                <button id="button-quantity-down" type="button" class="btn button-quantity col">
                                                    <i class="bi bi-caret-down"></i>
                                                </button>

                                                <input type="number" class="form-control" id="quantity" placeholder="1" name="quantity" value="1" min="1" max="${offerDTO.quantity}">

                                                <button id="button-quantity-up" type="button" class="btn button-quantity col">
                                                    <i class="bi bi-caret-up"></i>
                                                </button>

                                            </div>

                                        </div>

                                        <p class="pt-2">/${offerDTO.quantity}szt.</p>

                                        <div class="d-inline-flex justify-content-center mt-2 col-9">
                                            <a id="observe-button" href="#"> <i id="observe-icon" class="observe-icon bi bi-eye"></i> </a>
                                            <button type="submit"  href="${pageContext.request.contextPath}/koszyk/dodaj"  class="btn btn-add-to-cart text-lowercase button-add-to-cart h-auto ms-2 w-100">
                                                do koszyka
                                            </button>
                                        </div>

                                        <button type="submit" class="btn col-9 btn-buy-now text-uppercase button-add-to-cart h-auto ms-2 mt-4 h-auto">
                                            KUP TERAZ
                                        </button>

                                    </div>

                                </div>
                            </c:when>
                            <c:otherwise>
                                <%--@elvariable id="startValue" type="java.lang.Double"--%>
                                <%--@elvariable id="highestValueUserAuction" type="com.aukcje.dto.UserAuctionDTO"--%>

                                <div class="right-part-panel shadow mb-3" id="buy-panel-auction">
                                    <div class="text-center px-2 py-3 py-md-4 py-lg-5">

                                        <p>Aktualna cena:</p>


                                        <div class="current-price mb-4">
                                            <c:choose>
                                                <c:when test="${highestValueUserAuction.value > 0 }">
                                                    <h1>${offerDTO.price}<span class="text-center">${offerDTO.price*10 % 1 == 0 ? '0' : ''} zł</span> </h1>

                                                    <h1>${highestValueUserAuction.value}<span class="text-center">${offerDTO.price*10 % 1 == 0 ?'0' : ''} zł</span> </h1>
                                                </c:when>
                                                <c:otherwise>
                                                    <h1>${startValue}<span class="text-center">${offerDTO.price*10 % 1 == 0 ? '0' : ''} zł</span>  </h1>
                                                </c:otherwise>
                                            </c:choose>
                                            <p class="pt-2">/${offerDTO.quantity}szt.</p>
                                        </div>

                                        <p>Twoja oferta:</p>
                                        <div class="user-offer d-inline-flex">
                                            <c:choose>
                                                <c:when test="${highestValueUserAuction.value > 0 && highestValueUserAuction.value>startValue}">
                                                    <input type="number" class="form-control" id="user-offer" name="quantity" min="${highestValueUserAuction.value + 0.01}" placeholder="${highestValueUserAuction.value + 1.0}">
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="number" class="form-control" id="user-offer" name="quantity" min="${startValue + 0.01}" placeholder="${startValue + 1.0}">
                                                </c:otherwise>
                                            </c:choose>

                                            <p>zł</p>
                                        </div>

                                        <div class="d-inline-flex justify-content-center mt-5 col-9">
                                            <a id="observe-button-auction" class="observe-button" href="#"> <i id="observe-icon-auction" class="observe-icon bi bi-eye"></i> </a>
                                            <button type="submit" class="btn btn-bid text-uppercase h-auto ms-2 w-100">
                                                licytuj
                                            </button>

                                        </div>
                                    </div>

                                </div>
                            </c:otherwise>
                        </c:choose>




                        <div class="right-part-panel shadow" id="item-info-panel" class="mt-4 mt-md-3">

                            <div class="px-2 py-3 py-md-4 py-lg-5">
                                <p><span class="fw-bold">Stan: </span> ${offerDTO.offerDetails.itemCondition.name}</p>
                                <p><span class="fw-bold">Kategoria: </span> ${offerDTO.categoryPath.get(offerDTO.categoryPath.size()-1).name} </p>
                                <p><span class="fw-bold">Wyświetleń: </span> ${offerDTO.views} </p>
                                <p><span class="fw-bold">Data dodania: </span> ${offerDTO.insertDate.toLocalDate()} ${offerDTO.insertDate.toLocalTime()} </p>
                            </div>

                        </div>

                    <div id="seller-info-panel" class="mt-5">

                        <div class="d-flex justify-content-center">
                            <a class="text-center" href="#####PODGLAD UZYTKOWNIKA">

                                <c:choose>
                                    <c:when test="${empty offerDTO.user.avatarPath}">

                                        <img src="${pageContext.request.contextPath}/files/img/users/avatar-placeholder.png" class="avatar" alt="Zdjęcie profilowe sprzedawcy">

                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/files/img/users/${offerDTO.user.avatarPath}" class="avatar rounded" alt="Zdjęcie">

                                    </c:otherwise>
                                </c:choose>

                                <h1>${offerDTO.user.firstName}</h1>

                            </a>
                        </div>



                    </div>
                </section>


            </main>


        </section>
    </section>

</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/files/js/user/offer/offer.js"></script>

</body>
</html>