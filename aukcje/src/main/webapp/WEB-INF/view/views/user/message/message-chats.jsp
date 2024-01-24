<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Twoje czaty - SellBuy</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
</head>


<body>

<%@include file="../../../fragments/navbar.jspf" %>
<jsp:useBean id="newestMessageDTOS" scope="request" type="java.util.List<com.aukcje.dto.NewestMessageDTO>"/>


<main>
    <section class="central container col-sm-12 col-md-10 col-xl-8 bg-light">
        <div class="address-list bg-light p-5 mt-5 d-flex flex-column align-items-center">

            <p class="display-3 text-center my-4">
                Twoje czaty
            </p>

            <div class="card-body">

                <ul class="d-flex justify-content-center ps-0">

                    <div class="col-10">
                        <c:choose>
                            <c:when test="${empty newestMessageDTOS}">
                                <p class="text-danger">Nie rozmawiałeś jeszcze z innymi użytkownikami.</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var ="newestMessageDTO" items = "${newestMessageDTOS}" varStatus="index">
                                    <c:if test="${index.index!=0}">
                                        <hr class="my-4 col" />
                                    </c:if>
                                    <a class="d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/wiadomosci/czat?uzytkownikId=${newestMessageDTO.otherUser.id}">
                                        <div class="col-3">
                                            <c:choose>
                                                <c:when test="${newestMessageDTO.otherUser.avatarPath==null || newestMessageDTO.otherUser.avatarPath==''}">
                                                    <img id="profile-photo-ph" src="${pageContext.request.contextPath}/files/img/users/avatar-placeholder.png" alt="Użytkownik nie ustawił zdjęcia profilowego" class="d-block w-100">
                                                </c:when>
                                                <c:otherwise>
                                                    <img id="profile-photo" src="${pageContext.request.contextPath}/files/img/users/${newestMessageDTO.otherUser.id}/${newestMessageDTO.otherUser.id}.png" alt="Zdjęcie profilowe użytkownika" class="d-block w-100">
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div id="message-chat_${newestMessageDTO.otherUser}">

                                            <div class="align-self-center">
                                                <div class="col">
                                                    <div class="ms-4 text-end">
                                                        <p class="mb-0">${newestMessageDTO.sendTime.toLocalTime()}</p>
                                                        <p class="mb-0">${newestMessageDTO.sendTime.toLocalDate()}</p>
                                                        <p class="mb-0">${newestMessageDTO.otherUser.username}</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>

                </ul>

            </div>
        </div>

    </section>
</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/files/js/user/address/address.js"></script>


</body>
</html>