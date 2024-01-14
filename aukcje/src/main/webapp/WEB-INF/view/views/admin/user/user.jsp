<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>
<%--=<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>--%>

<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">--%>
<%--    <title>Użytkownicy</title>--%>

<%--    <link rel="preconnect" href="https://fonts.googleapis.com">--%>
<%--    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>--%>
<%--    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">--%>
<%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">--%>


<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/admin/user.css">--%>
<%--</head>--%>


<%--<body>--%>
<%--<%@include file="../../../fragments/navbar.jspf" %>--%>

<%--<main>--%>
<%--    <section class="central container col-sm-12 col-md-10 col-xl-8">--%>

<%--        <div class="search bg-light p-5 mt-5">--%>

<%--            <form:form method="get" action="${pageContext.request.contextPath}/admin/uzytkownik" class="pb-5">--%>

<%--                &lt;%&ndash;@elvariable id="searchModel" type="com.aukcje.model.UserSearchModel"&ndash;%&gt;--%>

<%--                <p class="display-3 text-center mt-3 mb-5">--%>
<%--                    Użytkownicy--%>
<%--                </p>--%>


<%--                <div class="row justify-content-center">--%>
<%--                    <div class="col-sm-12 col-lg-8 col-xl-6 py-3 justify-content-center">--%>
<%--                        <input type="text" class="form-control" id="fraza"--%>
<%--                               placeholder="Wyszukaj użytkownika..." name="fraza">--%>

<%--                        <div class="row justify-content-center align-content-center">--%>

<%--                            <div class="col-5 py-3">--%>
<%--                                <label for="rozmiarStrony" class="form-label">Wyników na stronę</label>--%>
<%--                                <input type="number" min="1" class="form-control" id="rozmiarStrony" placeholder="10" name="rozmiarStrony">--%>
<%--                            </div>--%>
<%--                            <button type="submit"--%>
<%--                                    class="btn btn-warning button-submit mb-md-0 mt-5 col-sm-6 align-self-center text-uppercase">--%>
<%--                                WYSZUKAJ--%>
<%--                            </button>--%>

<%--                        </div>--%>

<%--                    </div>--%>
<%--                </div>--%>

<%--            </form:form>--%>

<%--            <div class="users-list pt-5">--%>
<%--                <table class="table table-striped" >--%>
<%--                    <thead>--%>
<%--                    <tr>--%>
<%--&lt;%&ndash;                        <th>Nr</th>&ndash;%&gt;--%>
<%--                        <th>Id</th>--%>
<%--                        <th>Nazwa użytkownika</th>--%>
<%--                        <th>Średnia ocena</th>--%>
<%--                        <th>Status</th>--%>
<%--                        <th>Akcje</th>--%>

<%--                    </tr>--%>
<%--                    </thead>--%>

<%--                    <tbody>--%>
<%--                    <c:forEach var="tempUser" varStatus="index" items="${users}">--%>
<%--                        <tr>--%>
<%--&lt;%&ndash;                        <th scope="row">${index.index + 1}</th>&ndash;%&gt;--%>
<%--                            <td>${tempUser.id}</td>--%>
<%--                            <td>${tempUser.username}</td>--%>
<%--                            <td class="text-center">${tempUser.averageRate}</td>--%>
<%--                            <td class="text-lowercase">${tempUser.userStatus.name}</td>--%>

<%--                            <td>--%>
<%--                                <a href="${pageContext.request.contextPath}/uzytkownik/podglad/${tempUser.id}">Podgląd</a>--%>
<%--                                |--%>
<%--                                <a href="${pageContext.request.contextPath}/admin/uzytkownik/edytuj/${tempUser.id}">Edytuj</a>--%>
<%--                                |--%>

<%--                                <c:choose>--%>
<%--                                    <c:when test="${tempUser.userStatus.name=='ZBANOWANY'}">--%>
<%--                                        <a href="${pageContext.request.contextPath}/admin/uzytkownik/odblokuj/${tempUser.id}" style="color: red">--%>
<%--                                            Odblokuj--%>
<%--                                        </a>--%>

<%--                                    </c:when>--%>
<%--                                    <c:otherwise>--%>
<%--                                        <a href="${pageContext.request.contextPath}/admin/uzytkownik/zablokuj/${tempUser.id}" style="color: red">--%>
<%--                                            Zablokuj--%>
<%--                                        </a>--%>
<%--                                    </c:otherwise>--%>

<%--                                </c:choose>--%>

<%--                                |--%>
<%--                                <a href="${pageContext.request.contextPath}/admin/uzytkownik/usun/${tempUser.id}" style="color: red">Usuń</a>--%>
<%--                            </td>--%>

<%--                        </tr>--%>
<%--                    </c:forEach><tr>--%>
<%--                    </tbody>--%>

<%--                </table>--%>


<%--            </div>--%>

<%--        </div>--%>

<%--    </section>--%>



<%--</main>--%>
<%--<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>--%>
<%--&lt;%&ndash;<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>&ndash;%&gt;--%>
<%--&lt;%&ndash;<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>&ndash;%&gt;--%>
<%--<script src="${pageContext.request.contextPath}/files/js/bootstrap.min.js"></script>--%>

<%--<script src="${pageContext.request.contextPath}/files/js/admin/user/user.js"></script>--%>

<%--</body>--%>
<%--</html>--%>