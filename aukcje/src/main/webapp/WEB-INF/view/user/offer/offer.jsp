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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/offer/offer.css">

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
    <%--@elvariable id="offerDTO" type="com.aukcje.dto.OfferDTO"--%>

    <section class="central container col-sm-12 col-md-10 col-xl-8">


        <section id="offer-display" class="bg-light p-5">

            <header class="mb-5">
                <p class="display-6">
                    ${offerDTO.offerDetails.title}
                </p>

                <h4>
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
            </header>

            <main class="row">

                <section class="left-part col-md-7 col-lg-9 ">
                    <div id="photos-carousel" class="carousel slide" data-bs-ride="carousel">

                        <div class="carousel-indicators">

                            <c:forEach begin="0" end="${offerDTO.offerPhotoDTO.size()-1}" varStatus="index">

                                <c:choose>
                                    <c:when test="${index.index == 0}">
                                        <button type="button" data-bs-target="#photos-carousel" data-bs-slide-to="${index.index}" class="active"></button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="button" data-bs-target="#photos-carousel" data-bs-slide-to="${index.index}"></button>
                                    </c:otherwise>
                                </c:choose>

                            </c:forEach>

                        </div>

                        <div class="carousel-inner">

                            <c:forEach begin="0" end="${offerDTO.offerPhotoDTO.size()-1}" varStatus="index">

                                <c:choose>

                                    <c:when test="${index.index == 0}">
                                        <div class="dynamic-height carousel-item active">
                                            <img src="${pageContext.request.contextPath}/files/img/offers/${offerDTO.id}/${index.index+1}.jpeg" alt="photo" class="d-block w-100">
                                        </div>                            </c:when>
                                    <c:otherwise>
                                        <div class="dynamic-height carousel-item">
                                            <img src="${pageContext.request.contextPath}/files/img/offers/${offerDTO.id}/${index.index+1}.jpeg" alt="photo" class="d-block w-100">
                                        </div>
                                    </c:otherwise>

                                </c:choose>

                            </c:forEach>

                        </div>

                        <button class="carousel-control-prev" type="button" data-bs-target="#photos-carousel" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon"></span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#photos-carousel" data-bs-slide="next">
                            <span class="carousel-control-next-icon"></span>
                        </button>


                    </div>
                    <div id="offer-desc">
                        <h5>
                            ${offerDTO.offerDetails.description}
                        </h5>

                    </div>
                </section>

                <section class="right-part col-md">

                    <div class="buy-panel text-center border border-2 px-2 py-3 py-md-4 py-lg-5">
                        <h1>${offerDTO.price} <span class="text-center"> zł</span> </h1>

                            <div class="d-flex justify-content-center pt-4 my-2">

                                <div id="quantity-selection" class="d-inline-flex justify-content-center col-4 col-md-2">
                                    <button id="button-quantity-down" type="button" class="btn button-quantity col">
                                        <i class="bi bi-caret-down"></i>
                                    </button>

                                    <input type="number" class="form-control" id="quantity" placeholder="1" name="quantity" min="1" max="${offerDTO.quantity}">

                                    <button id="button-quantity-up" type="button" class="btn button-quantity col">
                                        <i class="bi bi-caret-up"></i>
                                    </button>

                                </div>

                        </div>

                        <p class="pt-2">/${offerDTO.quantity}szt.</p>

                        <div class="d-inline-flex justify-content-center mt-2 col-9">
                            <a id="observe-button py-0 my-0" href="#"> <i id="observe-icon" class="observe-icon bi bi-eye"></i> </a>

                            <button type="submit" class="btn btn-add-to-cart text-lowercase button-add-to-cart h-auto ms-2 w-100">
                                do koszyka
                            </button>

                        </div>

                        <button type="submit" class="btn col-9 btn-buy-now text-uppercase button-add-to-cart h-auto ms-2 mt-4 h-auto">
                            KUP TERAZ
                        </button>

                    </div>


                    <div class="info-panel mt-3">

                        <div class=" border border-2 px-2 py-3 py-md-4 py-lg-5">
                            <p><span class="fw-bold">Stan: </span> ${offerDTO.offerDetails.itemCondition.name}</p>
                            <p><span class="fw-bold">Kategoria: </span> ${offerDTO.categoryPath.get(offerDTO.categoryPath.size()-1).name} </p>
                            <p><span class="fw-bold">Wyświetleń: </span> ${offerDTO.views} </p>
                            <p><span class="fw-bold">Data dodania: </span> ${offerDTO.insertDate} </p>
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