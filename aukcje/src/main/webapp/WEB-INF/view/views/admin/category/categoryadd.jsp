<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Nowa kategoria - SellBuy</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/files/css/main.css">

</head>


<body>

<%@include file="../../../fragments/navbar.jspf" %>


<main>
    <section class="central container col-sm-12 col-md-10 col-xl-8">

        <div class="add-address bg-light p-5 mt-5">

            <%--@elvariable id="categories" type="com.aukcje.dto.CategoryDTO"--%>
            <%--@elvariable id="categoryModel" type="src/main/java/com/aukcje/model/CategoryModel.java"--%>
            <%--@elvariable id="categoryDTO" type="com.aukcje.dto.CategoryDTO.java"--%>

            <form:form method="post" action="${pageContext.request.contextPath}/admin/kategoria/dodaj/przetworz" modelAttribute="categoryModel">
            <p class="display-3 text-center mt-3 mb-5">
                Dodaj nową kategorię
            </p>

            <div class="row mt-2 mb-2">
                <div class="col-md-6">
                    <label for="nameInput" class="form-label">Nazwa kategorii</label>
                    <form:input type="text" path="name" class="form-control" id="nameInput" placeholder="Podaj nazwę"/>
                    <form:errors path="name" cssClass="error"/>

                    <div id="error_paragraph">
                        <form:errors element="categoryExists" cssClass="error" />
                    </div>
                </div>

                <div class="col-md-6">
                    <label for="parentCategorySelect" class="form-label">Kategoria nadrzędna</label>
                    <form:select type="text" path="parentCategory" class="form-control form-select display" id="parentCategorySelect" placeholder="Wybierz kategorię...">
                        <option value="placeholder" selected="selected">Wybierz kategorię...</option>
                        <c:forEach var = "categoryVar" items = "${categories}">
                            <option value="${categoryVar.id}">
                                <c:if test="${categoryVar.parentCategory != null}"> ${categoryVar.parentCategory} --> </c:if> <span class="fw-bold"> ${categoryVar.name} </span>
                            </option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="parentCategory" cssClass="error"/>
                </div>

            </div>



            <div class="row mt-5 justify-content-center">
                <button type="submit" class="btn button-submit col-sm-5 col-lg-4 mr-sm-2 mt-5 text-uppercase">ZAPISZ</button>
                <button type="button" class="btn button-cancel col-sm-5 col-lg-4 ml-sm-2 mt-3 mt-sm-5 text-capitalize">Anuluj</button>
            </div>

            </form:form>

    </section>

</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha512-bnIvzh6FU75ZKxp0GXLH9bewza/OIw6dLVh9ICg0gogclmYGguQJWl8U30WpbsGTqbIiAwxTsbe76DErLq5EDQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/files/js/admin/category/category.js"></script>

</body>
</html>