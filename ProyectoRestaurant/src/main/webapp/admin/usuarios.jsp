<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Administrar Usuarios</title>

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

        .table thead {
            background-color: #efebe9;
        }

        .badge-rol {
            font-size: 0.8rem;
            padding: 6px 14px;
            border-radius: 50px;
        }

        .badge-ADMIN {
            background-color: #c62828;
            color: white;
        }

        .badge-CLIENTE {
            background-color: #2e7d32;
            color: white;
        }

        .badge-COCINERO {
            background-color: #1565c0;
            color: white;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-admin navbar-dark mb-4">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${ctx}/productos?accion=listar">
            <i class="bi bi-arrow-left-circle-fill"></i> Volver al Panel
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
            <i class="bi bi-people-fill"></i> Gestión de Usuarios
        </h3>
    </div>

    <div class="card card-admin">
        <div class="card-body p-0">

            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">

                    <thead>
                        <tr>
                            <th class="ps-3">ID</th>
                            <th>Nombre</th>
                            <th>Correo</th>
                            <th>Teléfono</th>
                            <th>Rol</th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:choose>

                            <c:when test="${not empty usuarios}">
                                <c:forEach var="usuario" items="${usuarios}">
                                    <tr>
                                        <td class="ps-3">${usuario.id}</td>

                                        <td>
                                            <strong>${usuario.nombre}</strong>
                                        </td>

                                        <td>${usuario.correo}</td>

                                        <td>
                                            <c:out value="${usuario.telefono}" default="—"/>
                                        </td>

                                        <td>
                                            <span class="badge badge-rol badge-${usuario.rol.trol}">
                                                ${usuario.rol.trol}
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>

                            <c:otherwise>
                                <tr>
                                    <td colspan="5" class="text-center py-4 text-muted">
                                        No hay usuarios registrados.
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