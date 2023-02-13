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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/admin/user.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/bstreeview.css">
</head>


<body>

<nav class="navbar navbar-expand-lg navbar-dark py-2 sticky-top" id="navbar">
    <div class="container">
        <a class="navbar-brand" href="#">
            <a class="navbar-brand page-logo" href="#"> Sell<span class="page-logo-bold">B<i class="bi bi-basket page-logo-icon"></i>Y</span></a>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav ms-auto">
                <a class="nav-link px-lg-3" aria-current="page" href="index.html">STRONA GŁÓWNA</a>
                <a class="nav-link px-lg-3" href="#">Ogłoszenia</a>
                <a class="nav-link px-lg-3" href="#">Koszyk</a>

                <security:authorize access="hasRole('ADMIN')">
                    <a class="nav-link px-lg-3 active" href="${pageContext.request.contextPath}/admin/uzytkownik">Użytkownicy</a>
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
    <section class="central container col-sm-12 col-md-10 col-xl-8">

        <div class="categories-list bg-light p-5 mt-5 d-flex flex-column align-items-center">
            <p class="display-3 text-center mt-3 mb-5">
                Wszystkie kategorie
            </p>

            <div id="tree" class="col-12"></div>

            <a class="btn button-submit col-sm-7 col-lg-6 col-xl-5 ml-sm-2 mt-3 mt-sm-5 text-capitalize d-flex justify-content-center align-items-center" href="${pageContext.request.contextPath}/admin/kategoria/dodaj" role="button">Dodaj nową kategorię</a>

        </div>

        </div>

    </section>


</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/files/js/bstreeview.js"></script>




<script>

    let url = '${pageContext.request.contextPath}/rest/categories';
    var response;
    var baseurl = '${pageContext.request.contextPath}'

   $.ajax({
        url: url,
        success: function(data) {
            response = data;
            console.log(response);
        },
        async: false
    });


    $('#tree').bstreeview({data: response});



</script>

</body>
</html>