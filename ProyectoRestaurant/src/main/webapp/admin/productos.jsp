<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Productos - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body class="bg-light">

    <nav class="navbar navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="#">Panel de Administración - Restaurant</a>
            <a class="btn btn-outline-light btn-sm" href="ProductoServlet?accion=listar">Ver Vista Cliente</a>
        </div>
    </nav>

    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Inventario de Productos</h2>
            <a href="ProductoServlet?accion=prepararCrear" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Agregar Nuevo Producto
            </a>
        </div>

        <div class="card shadow">
            <div class="card-body">
                <table class="table table-hover align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Imagen</th>
                            <th>Nombre</th>
                            <th>Categoría</th>
                            <th>Precio</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${productos}">
                            <tr>
                                <td>${p.id}</td>
                                <td>
                                    <img src="assets/img/${p.imagen != null && p.imagen != '' ? p.imagen : 'no-image.png'}" 
                                         alt="${p.nombre}" 
                                         style="width: 60px; height: 60px; object-fit: cover;" 
                                         class="rounded shadow-sm">
                                </td>
                                <td>
                                    <strong>${p.nombre}</strong><br>
                                    <small class="text-muted">${p.descripcion}</small>
                                </td>
                                <td>
                                    <span class="badge bg-info text-dark">${p.categoria.nombre}</span>
                                </td>
                                <td>
                                    <span class="fw-bold text-success">$ ${p.precio}</span>
                                </td>
                                <td class="text-center">
                                    <div class="btn-group" role="group">
                                        <a href="ProductoServlet?accion=prepararEditar&id=${p.id}" 
                                           class="btn btn-warning btn-sm" title="Editar">
                                            <i class="bi bi-pencil-square"></i>
                                        </a>
                                        <a href="ProductoServlet?accion=eliminar&id=${p.id}" 
                                           class="btn btn-danger btn-sm" 
                                           onclick="return confirm('¿Estás seguro de eliminar este producto?')" 
                                           title="Eliminar">
                                            <i class="bi bi-trash"></i>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        
                        <c:if test="${empty productos}">
                            <tr>
                                <td colspan="6" class="text-center py-4">
                                    No hay productos registrados. <a href="ProductoServlet?accion=prepararCrear">Crea el primero aquí</a>.
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>