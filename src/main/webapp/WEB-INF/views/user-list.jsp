<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- задаём название страницы --%>
<c:set var="pageTitle" value="Список пользователей" />

<jsp:include page="header.jsp" />

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2>Пользователи</h2>
    <a href="${pageContext.request.contextPath}/users/add" class="btn btn-primary">
        Добавить нового
    </a>
</div>

<table class="table table-striped table-hover bg-white shadow-sm rounded">
    <thead class="table-dark">
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Email</th>
        <th class="text-end">Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="u">
        <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.email}</td>
            <td class="text-end">
                <a href="${pageContext.request.contextPath}/users/edit/${u.id}"
                   class="btn btn-sm btn-outline-secondary me-1">Ред.</a>
                <a href="${pageContext.request.contextPath}/users/delete/${u.id}"
                   class="btn btn-sm btn-outline-danger"
                   onclick="return confirm('Удалить пользователя ${u.name}?');">
                    Удал.
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="footer.jsp" />
