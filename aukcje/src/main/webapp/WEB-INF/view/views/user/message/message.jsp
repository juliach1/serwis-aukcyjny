<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Twoje rozmowy</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/user/message/message.css">
</head>


<body>

<%@include file="../../../fragments/navbar.jspf" %>
<jsp:useBean id="messageDTOS" scope="request" type="java.util.List<com.aukcje.dto.MessageDTO>"/>
<jsp:useBean id="user" scope="request" type="com.aukcje.dto.UserDTO"/>
<jsp:useBean id="principalDTO" scope="request" type="com.aukcje.dto.UserDTO"/>

<main>
    <section class="central container col-sm-12 col-md-10 col-xl-8 bg-light">
            <div class="row d-flex justify-content-center">
                <div class="col-sm-12 col-md-10 col-lg-8 col-xl-7">
                    <p class="display-4 text-center my-5">
                        Twoja rozmowa z użytkownikiem <a href="${pageContext.request.contextPath}/uzytkownik/podglad/${user.id}">${user.username}</a>
                    </p>
                    <div class="card my-4" id="chat2">

                        <div id="messages-scrolled" class="card-body overflow-auto" style="position: relative; height: 70vh">

                            <c:forEach var ="messageDTO" items = "${messageDTOS}" varStatus="index">
                                <c:choose>
                                    <c:when test="${index.index>0 && messageDTO.sendTime.toLocalDate() != messageDTOS.get(index.index-1).sendTime.toLocalDate()}">
                                        <div class="time-divider d-flex justify-content-center mb-4 text-muted">
                                            <p>${messageDTO.sendTime.toLocalDate()}</p>
                                        </div>
                                    </c:when>
                                    <c:when test="${index.index==0}">
                                        <div class="time-divider d-flex justify-content-center mb-4 text-muted">
                                            <p>${messageDTO.sendTime.toLocalDate()}</p>
                                        </div>
                                    </c:when>
                                </c:choose>
                            <c:choose>
                                <c:when test="${principalDTO.id == messageDTO.sender.id}">
                                    <div class="d-flex flex-row justify-content-end">
                                        <div class="me-3">
                                            <p class="sender-message-text bg-light-purple">${messageDTO.content}</p>
                                            <p class="small mb-3 text-muted">${messageDTO.sendTime.toLocalTime()}</p>
                                        </div>
                                        <c:choose>
                                            <c:when test="${messageDTO.sender.avatarPath==null || messageDTO.sender.avatarPath==''}">
                                                <img class="user-icon"  src="${pageContext.request.contextPath}/files/img/users/avatar-placeholder.png" alt="Użytkownik nie ustawił zdjęcia profilowego">
                                            </c:when>
                                            <c:otherwise>
                                                <img class="user-icon" src="${pageContext.request.contextPath}/files/img/users/${messageDTO.sender.id}/${messageDTO.sender.id}.png" alt="Zdjęcie profilowe użytkownika">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </c:when>
                                <c:when test="${principalDTO.id == messageDTO.receiver.id}">

                                    <div class="d-flex flex-row justify-content-start">
                                        <c:choose>
                                            <c:when test="${messageDTO.sender.avatarPath==null || messageDTO.sender.avatarPath==''}">
                                                <img class="user-icon"  src="${pageContext.request.contextPath}/files/img/users/avatar-placeholder.png" alt="Użytkownik nie ustawił zdjęcia profilowego">
                                            </c:when>
                                            <c:otherwise>
                                                <img class="user-icon" src="${pageContext.request.contextPath}/files/img/users/${messageDTO.sender.id}/${messageDTO.sender.id}.png" alt="Zdjęcie profilowe użytkownika">
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="ms-3">
                                            <p class="p-3 mb-1 rounded-3" style="background-color: #f5f6f7;">${messageDTO.content}</p>
                                            <p class="small ms-3 rounded-3 text-muted">${messageDTO.sendTime.toLocalTime()}</p>
                                        </div>
                                    </div>
                                </c:when>
                            </c:choose>

                            </c:forEach>

                        </div>
                        <div class="card-footer text-muted d-flex justify-content-start align-items-center p-3">
                            <c:choose>
                                <c:when test="${principalDTO.avatarPath==null || principalDTO.avatarPath==''}">
                                    <img class="user-icon"  src="${pageContext.request.contextPath}/files/img/users/avatar-placeholder.png" alt="Użytkownik nie ustawił zdjęcia profilowego">
                                </c:when>
                                <c:otherwise>
                                    <img class="user-icon" src="${pageContext.request.contextPath}/files/img/users/${principalDTO.id}/${principalDTO.id}.png" alt="Zdjęcie profilowe użytkownika">
                                </c:otherwise>
                            </c:choose>

                            <textarea class="form-control mx-2" id="contentInput"></textarea>
                            <button type="submit" onclick="sendMessageRequest(${user.id})" class="btn button-submit col-sm-5 col-lg-4 ml-sm-2 text-uppercase">Wyślij</button>

                            <a class="ms-1 text-muted" href="#!"><i class="fas fa-paperclip"></i></a>
                            <a class="ms-3 text-muted" href="#!"><i class="fas fa-smile"></i></a>
                            <a class="ms-3" href="#!"><i class="fas fa-paper-plane"></i></a>
                        </div>
                    </div>

                </div>
            </div>
    </section>
</main>
<script src="${pageContext.request.contextPath}/files/js/user/message/message.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>