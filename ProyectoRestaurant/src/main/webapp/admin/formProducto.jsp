<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>${producto != null ? "Editar Producto" : "Nuevo Producto"}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">${producto != null ? "Editar Producto" : "Agregar Nuevo Producto"}</h4>
                </div>
                <div class="card-body">
                    <form action="ProductoServlet" method="POST">
                        
                        <input type="hidden" name="id" value="${producto.id}">

                        <div class="mb-3">
                            <label class="form-label">Nombre del Producto</label>
                            <input type="text" name="nombre" class="form-control" value="${producto.nombre}" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Descripción</label>
                            <textarea name="descripcion" class="form-control" rows="3">${producto.descripcion}</textarea>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Precio</label>
                            <div class="input-group">
                                <span class="input-group-text">$</span>
                                <input type="number" step="0.01" name="precio" class="form-control" value="${producto.precio}" required>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Nombre de la Imagen (ej: pizza.jpg)</label>
                            <input type="text" name="imagen" class="form-control" value="${producto.imagen}">
                            <div class="form-text">Asegúrate de que el archivo esté en la carpeta /assets/img/</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Categoría</label>
                            <select name="idCategoria" class="form-select" required>
                                <option value="">Seleccione una categoría...</option>
                                <c:forEach var="cat" items="${categorias}">
                                    <option value="${cat.id}" ${producto.categoria.id == cat.id ? 'selected' : ''}>
                                        ${cat.nombre}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-success">Guardar Producto</button>
                            <a href="ProductoServlet?accion=gestionar" class="btn btn-secondary">Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>