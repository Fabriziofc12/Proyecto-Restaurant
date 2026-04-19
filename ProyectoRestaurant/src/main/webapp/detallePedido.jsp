<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalle de Pedido - Sopa de Muy Muy</title>

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

        .info-card {
            border: none;
            border-radius: 14px;
            box-shadow: 0 3px 15px rgba(78,52,46,0.10);
        }

        .info-card .card-header {
            background-color: #4e342e;
            color: #fff8e1;
            border-radius: 14px 14px 0 0 !important;
            font-weight: bold;
        }

        .detalle-item img {
            width: 70px;
            height: 70px;
            object-fit: cover;
            border-radius: 8px;
        }

        .detalle-item .img-placeholder {
            width: 70px;
            height: 70px;
            background: #efebe9;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2rem;
        }

        .badge-ORDENADO    { background-color: #1565c0; color: white; }
        .badge-PREPARACION { background-color: #e65100; color: white; }
        .badge-LISTO       { background-color: #2e7d32; color: white; }
        .badge-FINALIZADO  { background-color: #546e7a; color: white; }

        .badge-estado {
            font-size: 0.85rem;
            padding: 6px 14px;
            border-radius: 50px;
        }

        .total-final {
            font-size: 1.4rem;
            font-weight: bold;
            color: #2e7d32;
        }

        .footer { background-color: #3e2723; color: white; }

        /* Timeline de estado */
        .timeline {
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: relative;
            padding: 10px 0;
        }

        .timeline::before {
            content: '';
            position: absolute;
            top: 50%;
            left: 0;
            right: 0;
            height: 3px;
            background: #d7ccc8;
            transform: translateY(-50%);
            z-index: 0;
        }

        .timeline-step {
            display: flex;
            flex-direction: column;
            align-items: center;
            z-index: 1;
            gap: 6px;
        }

        .timeline-dot {
            width: 36px;
            height: 36px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1rem;
            background: #d7ccc8;
            color: white;
        }

        .timeline-dot.activo   { background: #4e342e; }
        .timeline-dot.completo { background: #2e7d32; }

        .timeline-label {
            font-size: 0.75rem;
            color: #8d6e63;
            text-align: center;
        }

        .timeline-label.activo  { color: #4e342e; font-weight: bold; }
        .timeline-label.completo{ color: #2e7d32; font-weight: bold; }
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
                <i class="bi bi-cart3"></i>
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
            <i class="bi bi-receipt-cutoff me-2"></i>Detalle del Pedido #${pedido.id}
        </h2>
        <nav aria-label="breadcrumb" class="mt-2">
            <ol class="breadcrumb mb-0">
                <li class="breadcrumb-item">
                    <a href="${ctx}/index.jsp" class="text-crema text-decoration-none">Inicio</a>
                </li>
                <li class="breadcrumb-item">
                    <a href="${ctx}/pedidos?accion=mis-pedidos" class="text-crema text-decoration-none">Pedidos</a>
                </li>
                <li class="breadcrumb-item active text-warning">Detalle</li>
            </ol>
        </nav>
    </div>
</section>

<main class="flex-grow-1 py-4">
    <div class="container">
        <div class="row g-4">

            <div class="col-lg-8">

                <div class="card info-card mb-4">
                    <div class="card-header py-3">
                        <i class="bi bi-activity me-2"></i>Estado del pedido
                    </div>
                    <div class="card-body py-4 px-4">
                        <div class="timeline">

                            <c:set var="estadoId" value="${pedido.estadoPedido.id}"/>

                            <div class="timeline-step">
                                <div class="timeline-dot ${estadoId >= 1 ? (estadoId > 1 ? 'completo' : 'activo') : ''}">
                                    <i class="bi bi-clock-fill"></i>
                                </div>
                                <span class="timeline-label ${estadoId >= 1 ? (estadoId > 1 ? 'completo' : 'activo') : ''}">
                                    Ordenado
                                </span>
                            </div>

                            <div class="timeline-step">
                                <div class="timeline-dot ${estadoId >= 2 ? (estadoId > 2 ? 'completo' : 'activo') : ''}">
                                    <i class="bi bi-fire"></i>
                                </div>
                                <span class="timeline-label ${estadoId >= 2 ? (estadoId > 2 ? 'completo' : 'activo') : ''}">
                                    En preparación
                                </span>
                            </div>

                            <div class="timeline-step">
                                <div class="timeline-dot ${estadoId >= 3 ? (estadoId > 3 ? 'completo' : 'activo') : ''}">
                                    <i class="bi bi-check-circle-fill"></i>
                                </div>
                                <span class="timeline-label ${estadoId >= 3 ? (estadoId > 3 ? 'completo' : 'activo') : ''}">
                                    Listo
                                </span>
                            </div>

                            <div class="timeline-step">
                                <div class="timeline-dot ${estadoId >= 4 ? 'completo' : ''}">
                                    <i class="bi bi-bag-check-fill"></i>
                                </div>
                                <span class="timeline-label ${estadoId >= 4 ? 'completo' : ''}">
                                    Finalizado
                                </span>
                            </div>

                        </div>
                    </div>
                </div>

                <div class="card info-card">
                    <div class="card-header py-3">
                        <i class="bi bi-bag-fill me-2"></i>Productos del pedido
                    </div>
                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table table-hover mb-0">
                                <thead style="background:#efebe9;">
                                    <tr>
                                        <th class="ps-3">Producto</th>
                                        <th class="text-center">Cantidad</th>
                                        <th class="text-center">Precio unit.</th>
                                        <th class="text-end pe-3">Subtotal</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="det" items="${detalles}">
                                        <tr class="detalle-item">
                                            <td class="ps-3 py-3">
                                                <div class="d-flex align-items-center gap-3">
                                                    <c:choose>
                                                        <c:when test="${not empty det.producto.imagen}">
                                                            <img src="${det.producto.imagen}"
                                                                 alt="${det.producto.nombre}">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="img-placeholder">🍲</div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <div>
                                                        <p class="fw-bold mb-0">${det.producto.nombre}</p>
                                                        <small class="text-muted">
                                                            <c:out value="${det.producto.categoria.nombre}"
                                                                   default="Sin categoría"/>
                                                        </small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="text-center align-middle">
                                                <span class="badge bg-secondary">${det.cantidad}</span>
                                            </td>
                                            <td class="text-center align-middle">
                                                S/ <fmt:formatNumber value="${det.precioUnitario}"
                                                                     minFractionDigits="2"
                                                                     maxFractionDigits="2"/>
                                            </td>
                                            <td class="text-end pe-3 align-middle fw-bold" style="color:#2e7d32;">
                                                S/ <fmt:formatNumber
                                                        value="${det.precioUnitario * det.cantidad}"
                                                        minFractionDigits="2"
                                                        maxFractionDigits="2"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                                <tfoot style="background:#efebe9;">
                                    <tr>
                                        <td colspan="3" class="text-end fw-bold pe-3 py-3">TOTAL</td>
                                        <td class="text-end pe-3 py-3 total-final">
                                            S/ <fmt:formatNumber value="${pedido.total}"
                                                                 minFractionDigits="2"
                                                                 maxFractionDigits="2"/>
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>

            </div>

            <div class="col-lg-4">
                <div class="card info-card">
                    <div class="card-header py-3">
                        <i class="bi bi-info-circle-fill me-2"></i>Información del pedido
                    </div>
                    <div class="card-body p-4">

                        <ul class="list-unstyled mb-0">
                            <li class="mb-3">
                                <small class="text-muted d-block">Número de pedido</small>
                                <strong>#${pedido.id}</strong>
                            </li>
                            <li class="mb-3">
                                <small class="text-muted d-block">Fecha</small>
                                <strong>${pedido.fecha}</strong>
                            </li>
                            <li class="mb-3">
                                <small class="text-muted d-block">Estado</small>
                                <span class="badge badge-estado badge-${pedido.estadoPedido.estado}">
                                    ${pedido.estadoPedido.estado}
                                </span>
                            </li>
                            <li class="mb-3">
                                <small class="text-muted d-block">Tipo de pedido</small>
                                <strong>${pedido.tipoPedido.tipo}</strong>
                            </li>
                            <c:if test="${not empty pedido.direccionEntrega}">
                                <li class="mb-3">
                                    <small class="text-muted d-block">Dirección de entrega</small>
                                    <strong>${pedido.direccionEntrega}</strong>
                                </li>
                            </c:if>
                            <c:if test="${sessionScope.rol eq 'ADMIN'}">
                                <li class="mb-3">
                                    <small class="text-muted d-block">Cliente</small>
                                    <strong>${pedido.usuario.nombre}</strong>
                                </li>
                            </c:if>
                            <li>
                                <small class="text-muted d-block">Total pagado</small>
                                <span class="total-final">
                                    S/ <fmt:formatNumber value="${pedido.total}"
                                                         minFractionDigits="2"
                                                         maxFractionDigits="2"/>
                                </span>
                            </li>
                        </ul>

                        <hr>

                        <div class="d-grid gap-2">
                            <a href="${ctx}/pedidos?accion=mis-pedidos"
                               class="btn btn-custom">
                                <i class="bi bi-arrow-left"></i> Volver a mis pedidos
                            </a>
                            <a href="${ctx}/menu" class="btn btn-outline-secondary">
                                <i class="bi bi-journal-text"></i> Ver el menú
                            </a>
                        </div>

                    </div>
                </div>
            </div>

        </div>
    </div>
</main>

<footer class="footer py-3 mt-auto">
    <div class="container text-center">
        <small>&copy; 2025 Sopa de Muy Muy. Todos los derechos reservados.</small>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>