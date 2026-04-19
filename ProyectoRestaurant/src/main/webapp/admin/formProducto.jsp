<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${not empty producto}">
                Editar Producto
            </c:when>
            <c:otherwise>
                Nuevo Producto
            </c:otherwise>
        </c:choose>
    </title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body { background-color: #f8f4ee; }

        .navbar-admin {
            background-color: #4e342e;
        }

        .navbar-admin .navbar-brand {
            color: #fff8e1 !important;
        }

        .btn-custom {
            background-color: #4e342e;
            border: none;
            color: white;
        }

        .btn-custom:hover {
            background-color: #3e2723;
        }

        .card-admin {
            border: none;
            border-radius: 14px;
            box-shadow: 0 3px 15px rgba(78,52,46,0.10);
        }

        .preview-img {
            width: 120px;
            height: 120px;
            object-fit: cover;
            border-radius: 10px;
            border: 1px solid #ddd;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-admin navbar-dark mb-4">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${ctx}/productos?accion=listar">
            <i class="bi bi-arrow-left-circle-fill"></i> Volver a productos
        </a>
    </div>
</nav>

<div class="container">

    <div class="card card-admin">
        <div class="card-body p-4">

            <h4 class="fw-bold mb-4" style="color:#4e342e;">
                <c:choose>
                    <c:when test="${not empty producto}">
                        <i class="bi bi-pencil-fill"></i> Editar Producto
                    </c:when>
                    <c:otherwise>
                        <i class="bi bi-plus-circle-fill"></i> Nuevo Producto
                    </c:otherwise>
                </c:choose>
            </h4>

            <form method="post" action="${ctx}/productos">

                <c:choose>
                    <c:when test="${not empty producto}">
                        <input type="hidden" name="accion" value="actualizar">
                        <input type="hidden" name="id" value="${producto.id}">
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="accion" value="guardar">
                    </c:otherwise>
                </c:choose>

                <div class="row g-3">

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">
                            <i class="bi bi-tag-fill"></i> Nombre
                        </label>
                        <input type="text"
                               name="nombre"
                               class="form-control"
                               required
                               value="<c:out value="${producto.nombre}"/>">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">
                            <i class="bi bi-currency-dollar"></i> Precio
                        </label>
                        <input type="number"
                               step="0.01"
                               min="0"
                               name="precio"
                               class="form-control"
                               required
                               value="<c:out value="${producto.precio}"/>">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">
                            <i class="bi bi-grid-fill"></i> Categoría
                        </label>
                        <select name="idCategoria" class="form-select" required>
                            <option value="">Seleccione categoría</option>
                            <c:forEach var="cat" items="${categorias}">
                                <option value="${cat.id}"
                                    ${producto.categoria.id == cat.id ? 'selected' : ''}>
                                    ${cat.nombre}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">
                            <i class="bi bi-link-45deg"></i> URL de la imagen
                        </label>
                        <input type="url"
                               name="imagen"
                               class="form-control"
                               placeholder="https://ejemplo.com/mifoto.jpg"
                               value="<c:out value="${producto.imagen}"/>">
                    </div>

                    <c:if test="${not empty producto.imagen}">
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Vista previa</label>
                            <div>
                                <img src="${producto.imagen}"
                                     class="preview-img" alt="Vista previa">
                            </div>
                        </div>
                    </c:if>

                    <div class="col-12">
                        <label class="form-label fw-semibold">
                            <i class="bi bi-card-text"></i> Descripción
                        </label>
                        <textarea name="descripcion"
                                  rows="3"
                                  class="form-control"><c:out value="${producto.descripcion}"/></textarea>
                    </div>

                </div>

                <div class="mt-4 d-flex justify-content-between">

                    <a href="${ctx}/productos?accion=listar"
                       class="btn btn-outline-secondary">
                        Cancelar
                    </a>

                    <button type="submit" class="btn btn-custom">
                        <c:choose>
                            <c:when test="${not empty producto}">
                                <i class="bi bi-save-fill"></i> Actualizar
                            </c:when>
                            <c:otherwise>
                                <i class="bi bi-check-circle-fill"></i> Guardar
                            </c:otherwise>
                        </c:choose>
                    </button>

                </div>

            </form>

        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>