<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="${user.id == null ? 'Добавить пользователя' : 'Редактировать пользователя'}" />

<jsp:include page="header.jsp" />

<!-- Хлебные крошки -->
<nav aria-label="breadcrumb" class="mb-4">
    <ol class="breadcrumb">
        <li class="breadcrumb-item">
            <a href="${pageContext.request.contextPath}/users">
                <i class="bi bi-house"></i> Главная
            </a>
        </li>
        <li class="breadcrumb-item">
            <a href="${pageContext.request.contextPath}/users">Пользователи</a>
        </li>
        <li class="breadcrumb-item active" aria-current="page">
            ${user.id == null ? 'Добавить' : 'Редактировать'}
        </li>
    </ol>
</nav>

<div class="row justify-content-center">
    <div class="col-md-8 col-lg-6">
        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white">
                <h5 class="card-title mb-0">
                    <i class="bi bi-person-${user.id == null ? 'plus' : 'gear'}"></i>
                    ${pageTitle}
                </h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/users/save" method="post" novalidate>
                    <input type="hidden" name="id" value="${user.id}" />

                    <div class="mb-3">
                        <label for="name" class="form-label">
                            <i class="bi bi-person"></i> Имя <span class="text-danger">*</span>
                        </label>
                        <input type="text"
                               class="form-control ${not empty nameError ? 'is-invalid' : ''}"
                               id="name"
                               name="name"
                               value="${user.name}"
                               placeholder="Введите имя пользователя"
                               required />
                        <c:if test="${not empty nameError}">
                            <div class="invalid-feedback">${nameError}</div>
                        </c:if>
                        <div class="form-text">Имя должно содержать от 2 до 50 символов</div>
                    </div>

                    <div class="mb-4">
                        <label for="email" class="form-label">
                            <i class="bi bi-envelope"></i> Email <span class="text-danger">*</span>
                        </label>
                        <input type="email"
                               class="form-control ${not empty emailError ? 'is-invalid' : ''}"
                               id="email"
                               name="email"
                               value="${user.email}"
                               placeholder="Введите email адрес" />
                        <c:if test="${not empty emailError}">
                            <div class="invalid-feedback">${emailError}</div>
                        </c:if>
                        <div class="form-text">Введите корректный email адрес</div>
                    </div>

                    <div class="d-grid gap-2 d-md-flex justify-content-md-between">
                        <a href="${pageContext.request.contextPath}/users" class="btn btn-outline-secondary">
                            <i class="bi bi-arrow-left"></i> Отмена
                        </a>
                        <button type="submit" class="btn btn-success">
                            <i class="bi bi-check-lg"></i>
                            ${user.id == null ? 'Создать' : 'Сохранить'}
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Дополнительная информация -->
        <c:if test="${user.id != null}">
            <div class="card mt-3">
                <div class="card-body">
                    <h6 class="card-subtitle mb-2 text-muted">Информация о записи</h6>
                    <small class="text-muted">ID пользователя: ${user.id}</small>
                </div>
            </div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />