<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Administrar Productos</title>

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

        .badge-categoria {
            background-color: #efebe9;
            color: #5d4037;
        }

        .table thead {
            background-color: #efebe9;
        }

        .precio {
            color: #2e7d32;
            font-weight: bold;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-admin navbar-dark mb-4">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${ctx}/index.jsp">
            <i class="bi bi-cup-hot-fill"></i> Panel Admin
        </a>

        <div>
            <a href="${ctx}/menu" class="btn btn-outline-light btn-sm me-2">
                <i class="bi bi-journal-text"></i> Ver menú
            </a>
            <a href="${ctx}/logout" class="btn btn-outline-warning btn-sm">
                <i class="bi bi-box-arrow-right"></i> Cerrar sesión
            </a>
        </div>
    </div>
</nav>

<div class="container">

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="fw-bold" style="color:#4e342e;">
            <i class="bi bi-box-seam"></i> Gestión de Productos
        </h3>

        <a href="${ctx}/productos?accion=nuevo" class="btn btn-custom">
            <i class="bi bi-plus-circle-fill"></i> Nuevo producto
        </a>
    </div>

    <div class="card card-admin mb-4">
        <div class="card-body">
            <form method="get" action="${ctx}/productos" class="row g-2 align-items-center">

                <input type="hidden" name="accion" value="listar">

                <div class="col-md-4">
                    <label class="form-label fw-semibold">
                        <i class="bi bi-filter-circle"></i> Filtrar por categoría
                    </label>
                    <select name="idCategoria" class="form-select"
                            onchange="this.form.submit()">
                        <option value="">Todas</option>
                        <c:forEach var="cat" items="${categorias}">
                            <option value="${cat.id}"
                                ${categoriaFiltro == cat.id ? 'selected' : ''}>
                                ${cat.nombre}
                            </option>
                        </c:forEach>
                    </select>
                </div>

            </form>
        </div>
    </div>

    <div class="card card-admin">
        <div class="card-body p-0">

            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">

                    <thead>
                        <tr>
                            <th class="ps-3">#</th>
                            <th>Imagen</th>
                            <th>Nombre</th>
                            <th>Categoría</th>
                            <th>Precio</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:choose>

                            <c:when test="${not empty productos}">
                                <c:forEach var="producto" items="${productos}">
                                    <tr>
                                        <td class="ps-3">${producto.id}</td>

                                        <td>
                                            <c:choose> 
                                                <c:when test="${not empty producto.imagen}">
                                                    <img src="${producto.imagen}" 
                                                         width="60" height="60"
                                                         style="object-fit:cover; border-radius:8px;">
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">Sin imagen</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

                                        <td>
                                            <strong>${producto.nombre}</strong>
                                            <br>
                                            <small class="text-muted">
                                                ${producto.descripcion}
                                            </small>
                                        </td>

                                        <td>
                                            <span class="badge badge-categoria">
                                                ${producto.categoria.nombre}
                                            </span>
                                        </td>

                                        <td class="precio">
                                            S/
                                            <fmt:formatNumber value="${producto.precio}"
                                                minFractionDigits="2"
                                                maxFractionDigits="2"/>
                                        </td>

                                        <td class="text-center">
                                            <a href="${ctx}/productos?accion=editar&id=${producto.id}"
                                               class="btn btn-outline-primary btn-sm me-1">
                                                <i class="bi bi-pencil-fill"></i>
                                            </a>

                                            <a href="${ctx}/productos?accion=eliminar&id=${producto.id}"
                                               class="btn btn-outline-danger btn-sm"
                                               onclick="return confirm('¿Eliminar este producto?')">
                                                <i class="bi bi-trash-fill"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>

                            <c:otherwise>
                                <tr>
                                    <td colspan="6" class="text-center py-4 text-muted">
                                        No hay productos registrados.
                                    </td>
                                </tr>
                            </c:otherwise>

                        </c:choose>

                    </tbody>
                </table>
            </div>

        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>