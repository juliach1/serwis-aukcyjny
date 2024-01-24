<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Twoje adresy - SellBuy</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/address/address.css">
</head>


<body>

<%@include file="../../../fragments/navbar.jspf" %>
<jsp:useBean id="addressDTOS" scope="request" type="java.util.List<com.aukcje.dto.AddressDTO>"/>

<main>
    <section class="central container col-sm-12 col-md-10 col-xl-8 bg-light">
        <div class="address-list bg-light p-5 mt-5 d-flex flex-column align-items-center">

            <p class="display-3 text-center my-4">
                Twoje adresy
            </p>

            <div class="card-body">

                <ul class="list-group list-group-flush">
                    <c:choose>
                        <c:when test="${ addressDTOS.size()==0}">
                            <p class="text-danger">Brak dostępnych adresów. Dodaj nowy adres, by móc dokonywać zakupów.</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var = "newestMessageDTO" items = "${addressDTOS}" varStatus="index">

                                <div id="address_${newestMessageDTO.id}">
                                    <c:if test="${index.index!=0}">
                                        <hr class="my-4" />
                                    </c:if>

                                    <li class="d-flex align-items-center mx-4">
                                        <div class="col text-end">
                                            <div class="address text-right">
                                                <p class="mb-0">${newestMessageDTO.firstName} ${newestMessageDTO.lastName}</p>
                                                <p class="mb-0">${newestMessageDTO.country.name}</p>
                                                <p class="mb-0">${newestMessageDTO.city}, ${newestMessageDTO.postalCode}</p>
                                                <p class="mb-0">${newestMessageDTO.streetName}</p>
                                                <p class="mb-0">tel. ${newestMessageDTO.phone}</p>
                                            </div>
                                        </div>

                                        <a id="edit-button_${newestMessageDTO.id}" title="Edytuj adres"
                                           href="${pageContext.request.contextPath}/uzytkownik/adres/edytuj/${newestMessageDTO.id}"
                                           role="button">
                                            <i id="edit-icon_${newestMessageDTO.id}" class="edit-icon card-action-icon bi bi-pencil-square ms-4"></i>
                                        </a>

                                        <a id="remove-button_${newestMessageDTO.id}" title="Usuń adres" data-bs-toggle="modal"
                                           data-bs-target="#myModal_${newestMessageDTO.id}"
                                           href="${pageContext.request.contextPath}/uzytkownik/adres/usun/${newestMessageDTO.id}"
                                           role="button">
                                            <i id="remove-icon_${newestMessageDTO.id}" class="remove-icon card-action-icon ms-4 bi bi-trash3 text-danger"></i>
                                        </a>
                                    </li>
                                </div>

                                <div class="modal" id="myModal_${newestMessageDTO.id}">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title">Usunąć adres?</h4>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body">
                                                Czy na pewno chcesz usunąć wybrany adres? Nie da się cofnąć tego działania.
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn button-submit" onclick="deleteAddress(${newestMessageDTO.id})">Tak</button>
                                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Nie</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </ul>

            </div>
            <div class="w-50 my-5">
                <a id="add-address-btn" title="Dodaj nowy adres"
                   href="${pageContext.request.contextPath}/uzytkownik/adres/dodaj"
                   role="button"
                   class="btn button-submit text-uppercase d-flex justify-content-center align-items-center">
                    Nowy adres
                </a>
            </div>
        </div>

    </section>
</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/files/js/user/address/address.js"></script>


</body>
</html>