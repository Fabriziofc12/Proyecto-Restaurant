<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn"  uri="jakarta.tags.functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi Carrito - Sopa de Muy Muy</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background-color: #f8f4ee;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .navbar-custom  { background-color: #4e342e; }
        .navbar-brand,
        .text-crema     { color: #fff8e1 !important; }

        .offcanvas-custom { background-color: #4e342e; }
        .offcanvas-custom .nav-link {
            color: #fff8e1 !important;
            font-size: 1.05rem;
            margin-bottom: 8px;
        }
        .offcanvas-custom .nav-link:hover { color: #ffd54f !important; }

        .page-title {
            background-color: #4e342e;
            color: #fff8e1;
            padding: 40px 0 30px;
        }

        .btn-custom {
            background-color: #4e342e;
            border: none;
            color: white;
        }
        .btn-custom:hover {
            background-color: #3e2723;
            color: white;
        }

        .form-control:focus {
            border-color: #d4a373;
            box-shadow: 0 0 0 0.2rem rgba(212,163,115,0.25);
        }

        .carrito-item {
            border: none;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(78,52,46,0.08);
            margin-bottom: 1rem;
        }

        .carrito-item img {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 10px;
        }

        .carrito-item .img-placeholder {
            width: 100px;
            height: 100px;
            border-radius: 10px;
            background-color: #efebe9;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2.5rem;
        }

        .resumen-card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(78,52,46,0.10);
            position: sticky;
            top: 80px;
        }

        .resumen-card .card-header {
            background-color: #4e342e;
            color: #fff8e1;
            border-radius: 12px 12px 0 0 !important;
            font-weight: bold;
            font-size: 1.1rem;
        }

        .precio-unitario { color: #6d4c41; font-size: 0.95rem; }
        .subtotal        { color: #2e7d32; font-weight: bold; }

        .cantidad-input {
            width: 70px;
            text-align: center;
        }

        .carrito-vacio {
            text-align: center;
            padding: 60px 20px;
            color: #8d6e63;
        }

        .carrito-vacio i { font-size: 5rem; margin-bottom: 1rem; }

        .footer { background-color: #3e2723; color: white; }
    </style>
</head>
<body>

<nav class="navbar navbar-dark navbar-custom sticky-top">
    <div class="container">
        <button class="navbar-toggler border-0" type="button"
                data-bs-toggle="offcanvas" data-bs-target="#menuLateral">
            <span class="navbar-toggler-icon"></span>
        </button>

        <a class="navbar-brand fw-bold mx-auto" href="${ctx}/index.jsp">
            <i class="bi bi-cup-hot-fill"></i> Sopa de Muy Muy
        </a>

        <div>
            <a href="${ctx}/carrito?accion=ver" class="text-crema fs-5">
                <i class="bi bi-cart3-fill"></i>
            </a>
        </div>
    </div>
</nav>

<div class="offcanvas offcanvas-start offcanvas-custom text-white"
     tabindex="-1" id="menuLateral">
    <div class="offcanvas-header">
        <h5 class="offcanvas-title"><i class="bi bi-list"></i> Menú</h5>
        <button type="button" class="btn-close btn-close-white"
                data-bs-dismiss="offcanvas"></button>
    </div>
    <div class="offcanvas-body">
        <c:if test="${not empty sessionScope.usuario}">
            <p class="mb-4">Bienvenido, <strong>${sessionScope.usuario.nombre}</strong></p>
        </c:if>
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="${ctx}/index.jsp">
                    <i class="bi bi-house-door-fill"></i> Inicio
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${ctx}/menu">
                    <i class="bi bi-journal-text"></i> Menú
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${ctx}/carrito?accion=ver">
                    <i class="bi bi-cart-fill"></i> Mi carrito
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${ctx}/pedidos?accion=mis-pedidos">
                    <i class="bi bi-bag-check-fill"></i> Mis pedidos
                </a>
            </li>

            <c:if test="${sessionScope.rol eq 'ADMIN'}">
                <hr class="border-light">
                <li class="nav-item">
                    <a class="nav-link" href="${ctx}/productos?accion=listar">
                        <i class="bi bi-box-seam"></i> Administrar productos
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${ctx}/usuarios">
                        <i class="bi bi-people-fill"></i> Administrar usuarios
                    </a>
                </li>
            </c:if>

            <hr class="border-light">
            <li class="nav-item">
                <a class="nav-link" href="${ctx}/logout">
                    <i class="bi bi-box-arrow-right"></i> Cerrar sesión
                </a>
            </li>
        </ul>
    </div>
</div>

<section class="page-title">
    <div class="container">
        <h2 class="fw-bold mb-0">
            <i class="bi bi-cart3-fill me-2"></i>Mi Carrito
        </h2>
        <nav aria-label="breadcrumb" class="mt-2">
            <ol class="breadcrumb mb-0">
                <li class="breadcrumb-item">
                    <a href="${ctx}/index.jsp" class="text-crema text-decoration-none">Inicio</a>
                </li>
                <li class="breadcrumb-item">
                    <a href="${ctx}/menu" class="text-crema text-decoration-none">Menú</a>
                </li>
                <li class="breadcrumb-item active text-warning">Carrito</li>
            </ol>
        </nav>
    </div>
</section>

<main class="flex-grow-1 py-4">
    <div class="container">

        <c:choose>

            <%-- CARRITO VACÍO --%>
            <c:when test="${empty detalles}">
                <div class="carrito-vacio">
                    <i class="bi bi-cart-x"></i>
                    <h3>Tu carrito está vacío</h3>
                    <p>Aún no has agregado productos a tu carrito.</p>
                    <a href="${ctx}/menu" class="btn btn-custom btn-lg mt-2">
                        <i class="bi bi-journal-text"></i> Ver el menú
                    </a>
                </div>
            </c:when>

            <c:otherwise>
                <div class="row g-4">

                    <div class="col-lg-8">

                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h5 class="mb-0 fw-bold" style="color:#4e342e;">
                                <i class="bi bi-bag-fill me-1"></i>
                                Productos (<c:out value="${fn:length(detalles)}"/>
                                <c:set var="total_items" value="0"/>
                                <c:forEach var="d" items="${detalles}">
                                    <c:set var="total_items" value="${total_items + d.cantidad}"/>
                                </c:forEach>
                                ${total_items} unidades)
                            </h5>
                            <a href="${ctx}/carrito?accion=vaciar"
                               class="btn btn-outline-danger btn-sm"
                               onclick="return confirm('¿Seguro que quieres vaciar el carrito?')">
                                <i class="bi bi-trash3"></i> Vaciar carrito
                            </a>
                        </div>

                        <c:forEach var="detalle" items="${detalles}">
                            <div class="card carrito-item">
                                <div class="card-body">
                                    <div class="row align-items-center g-3">

                                        <div class="col-auto">
                                            <c:choose>
                                                <c:when test="${not empty detalle.producto.imagen}">
                                                    <img src="${detalle.producto.imagen}"
                                                         alt="${detalle.producto.nombre}">
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="img-placeholder">🍲</div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                        <div class="col">
                                            <h6 class="fw-bold mb-1">${detalle.producto.nombre}</h6>
                                            <p class="precio-unitario mb-0">
                                                Precio unitario:
                                                S/ <fmt:formatNumber value="${detalle.precioUnitario}"
                                                                     minFractionDigits="2"
                                                                     maxFractionDigits="2"/>
                                            </p>
                                        </div>

                                        <div class="col-auto">
                                            <form action="${ctx}/carrito" method="post"
                                                  class="d-flex align-items-center gap-2">
                                                <input type="hidden" name="accion"    value="actualizar">
                                                <input type="hidden" name="idDetalle" value="${detalle.id}">
                                                <input type="hidden" name="idProducto" value="${detalle.producto.id}">
                                                <input type="number"
                                                       name="cantidad"
                                                       value="${detalle.cantidad}"
                                                       min="1" max="99"
                                                       class="form-control cantidad-input"
                                                       onchange="this.form.submit()">
                                            </form>
                                        </div>

                                        <div class="col-auto text-end">
                                            <span class="subtotal fs-5">
                                                S/ <fmt:formatNumber
                                                        value="${detalle.precioUnitario * detalle.cantidad}"
                                                        minFractionDigits="2"
                                                        maxFractionDigits="2"/>
                                            </span>
                                        </div>

                                        <div class="col-auto">
                                            <a href="${ctx}/carrito?accion=eliminarItem&idDetalle=${detalle.id}"
                                               class="btn btn-outline-danger btn-sm"
                                               title="Eliminar"
                                               onclick="return confirm('¿Eliminar este producto del carrito?')">
                                                <i class="bi bi-trash3"></i>
                                            </a>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        <div class="mt-3">
                            <a href="${ctx}/menu" class="btn btn-outline-secondary">
                                <i class="bi bi-arrow-left"></i> Seguir eligiendo platos
                            </a>
                        </div>

                    </div>

                    <div class="col-lg-4">
                        <div class="card resumen-card">
                            <div class="card-header py-3">
                                <i class="bi bi-receipt-cutoff me-2"></i>Resumen del pedido
                            </div>
                            <div class="card-body p-4">

                                <c:forEach var="detalle" items="${detalles}">
                                    <div class="d-flex justify-content-between mb-2">
                                        <span class="text-muted" style="font-size:0.9rem;">
                                            ${detalle.producto.nombre}
                                            <small>(x${detalle.cantidad})</small>
                                        </span>
                                        <span style="font-size:0.9rem;">
                                            S/ <fmt:formatNumber
                                                    value="${detalle.precioUnitario * detalle.cantidad}"
                                                    minFractionDigits="2"
                                                    maxFractionDigits="2"/>
                                        </span>
                                    </div>
                                </c:forEach>

                                <hr>

                                <div class="d-flex justify-content-between mb-4">
                                    <span class="fw-bold fs-5">Total</span>
                                    <span class="fw-bold fs-5" style="color:#2e7d32;">
                                        S/ <fmt:formatNumber value="${total}"
                                                             minFractionDigits="2"
                                                             maxFractionDigits="2"/>
                                    </span>
                                </div>

                                <form action="${ctx}/pedidos" method="post">

                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">
                                            <i class="bi bi-bag-check"></i> Tipo de pedido
                                        </label>
                                        <select name="tipoPedido" class="form-select" id="tipoPedido"
                                                onchange="toggleDireccion()">
                                            <option value="2">Para comer acá</option>
                                            <option value="1">Para llevar</option>
                                        </select>
                                    </div>

                                    <div class="mb-3" id="divDireccion" style="display:none;">
                                        <label class="form-label fw-semibold">
                                            <i class="bi bi-geo-alt-fill"></i> Dirección de entrega
                                        </label>
                                        <input type="text"
                                               class="form-control"
                                               name="direccion"
                                               id="inputDireccion"
                                               placeholder="Ej: Av. Los Sabores 123">
                                    </div>

                                    <div class="d-grid">
                                        <button type="submit" class="btn btn-custom btn-lg">
                                            <i class="bi bi-bag-check-fill"></i> Realizar pedido
                                        </button>
                                    </div>

                                </form>

                            </div>
                        </div>
                    </div>

                </div>
            </c:otherwise>
        </c:choose>

    </div>
</main>

<footer class="footer py-3 mt-auto">
    <div class="container text-center">
        <small>&copy; 2025 Sopa de Muy Muy. Todos los derechos reservados.</small>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function toggleDireccion() {
        var tipo      = document.getElementById('tipoPedido').value;
        var divDir    = document.getElementById('divDireccion');
        var inputDir  = document.getElementById('inputDireccion');

        if (tipo === '1') {
            divDir.style.display   = 'block';
            inputDir.required      = true;
        } else {
            divDir.style.display   = 'none';
            inputDir.required      = false;
            inputDir.value         = '';
        }
    }
</script>
</body>
</html>