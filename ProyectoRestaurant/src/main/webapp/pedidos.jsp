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
    <title>Mis Pedidos - Sopa de Muy Muy</title>

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

        .pedido-card {
            border: none;
            border-radius: 14px;
            box-shadow: 0 3px 15px rgba(78,52,46,0.10);
            margin-bottom: 1.2rem;
            transition: transform 0.2s;
        }

        .pedido-card:hover {
            transform: translateY(-3px);
        }

        .pedido-card .card-header {
            background-color: #efebe9;
            border-radius: 14px 14px 0 0 !important;
            border-bottom: 1px solid #d7ccc8;
        }

        /* ── Badges de estado ── */
        .badge-ORDENADO    { background-color: #1565c0; color: white; }
        .badge-PREPARACION { background-color: #e65100; color: white; }
        .badge-LISTO       { background-color: #2e7d32; color: white; }
        .badge-FINALIZADO  { background-color: #546e7a; color: white; }

        .badge-estado {
            font-size: 0.8rem;
            padding: 6px 14px;
            border-radius: 50px;
        }

        .sin-pedidos {
            text-align: center;
            padding: 70px 20px;
            color: #8d6e63;
        }

        .sin-pedidos i { font-size: 5rem; margin-bottom: 1rem; }

        .total-pedido {
            font-size: 1.2rem;
            font-weight: bold;
            color: #2e7d32;
        }

        /* Admin: selector de estado */
        .select-estado {
            border-color: #d7ccc8;
            font-size: 0.9rem;
        }

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
            <c:choose>
                <c:when test="${sessionScope.rol eq 'ADMIN'}">
                    <i class="bi bi-bag-check-fill me-2"></i>Todos los Pedidos
                </c:when>
                <c:otherwise>
                    <i class="bi bi-bag-check-fill me-2"></i>Mis Pedidos
                </c:otherwise>
            </c:choose>
        </h2>
        <nav aria-label="breadcrumb" class="mt-2">
            <ol class="breadcrumb mb-0">
                <li class="breadcrumb-item">
                    <a href="${ctx}/index.jsp" class="text-crema text-decoration-none">Inicio</a>
                </li>
                <li class="breadcrumb-item active text-warning">Pedidos</li>
            </ol>
        </nav>
    </div>
</section>

<main class="flex-grow-1 py-4">
    <div class="container">

        <c:if test="${not empty exito}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i>${exito}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:choose>

            <c:when test="${empty pedidos}">
                <div class="sin-pedidos">
                    <i class="bi bi-bag-x"></i>
                    <h3>No tienes pedidos aún</h3>
                    <p>Explora nuestro menú y haz tu primer pedido.</p>
                    <a href="${ctx}/menu" class="btn btn-custom btn-lg mt-2">
                        <i class="bi bi-journal-text"></i> Ver el menú
                    </a>
                </div>
            </c:when>

            <c:otherwise>

                <p class="text-muted mb-4">
                    Total: <strong>${pedidos.size()} pedido(s)</strong>
                </p>

                <c:forEach var="pedido" items="${pedidos}">
                    <div class="card pedido-card">

                        <div class="card-header py-3">
                            <div class="row align-items-center g-2">

                                <div class="col-sm-auto">
                                    <span class="fw-bold" style="color:#4e342e;">
                                        <i class="bi bi-hash"></i> Pedido #${pedido.id}
                                    </span>
                                </div>

                                <div class="col-sm-auto">
                                    <i class="bi bi-calendar3 me-1 text-muted"></i>
                                    <small class="text-muted">${pedido.fecha}</small>
                                </div>

                                <div class="col-sm-auto">
                                    <span class="badge badge-estado badge-${pedido.estadoPedido.estado}">
                                        <c:choose>
                                            <c:when test="${pedido.estadoPedido.estado eq 'ORDENADO'}">
                                                <i class="bi bi-clock-fill"></i>
                                            </c:when>
                                            <c:when test="${pedido.estadoPedido.estado eq 'PREPARACION'}">
                                                <i class="bi bi-fire"></i>
                                            </c:when>
                                            <c:when test="${pedido.estadoPedido.estado eq 'LISTO'}">
                                                <i class="bi bi-check-circle-fill"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="bi bi-bag-check-fill"></i>
                                            </c:otherwise>
                                        </c:choose>
                                        ${pedido.estadoPedido.estado}
                                    </span>
                                </div>

                                <div class="col-sm-auto">
                                    <span class="badge bg-secondary">
                                        <i class="bi bi-bag me-1"></i>${pedido.tipoPedido.tipo}
                                    </span>
                                </div>

                                <c:if test="${sessionScope.rol eq 'ADMIN'}">
                                    <div class="col-sm-auto ms-auto">
                                        <form action="${ctx}/pedidos" method="get"
                                              class="d-flex align-items-center gap-2">
                                            <input type="hidden" name="accion"  value="cambiarEstado">
                                            <input type="hidden" name="id"      value="${pedido.id}">
                                            <select name="idEstado" class="form-select form-select-sm select-estado"
                                                    onchange="this.form.submit()">
                                                <option value="1" ${pedido.estadoPedido.id == 1 ? 'selected' : ''}>ORDENADO</option>
                                                <option value="2" ${pedido.estadoPedido.id == 2 ? 'selected' : ''}>PREPARACION</option>
                                                <option value="3" ${pedido.estadoPedido.id == 3 ? 'selected' : ''}>LISTO</option>
                                                <option value="4" ${pedido.estadoPedido.id == 4 ? 'selected' : ''}>FINALIZADO</option>
                                            </select>
                                        </form>
                                    </div>
                                </c:if>

                            </div>
                        </div>

                        <div class="card-body py-3">
                            <div class="row align-items-center g-3">

                                <div class="col-md-8">

                                    <c:if test="${sessionScope.rol eq 'ADMIN'}">
                                        <p class="mb-1">
                                            <i class="bi bi-person-fill text-muted me-1"></i>
                                            <strong>Cliente:</strong> ${pedido.usuario.nombre}
                                        </p>
                                    </c:if>

                                    <c:if test="${not empty pedido.direccionEntrega}">
                                        <p class="mb-1">
                                            <i class="bi bi-geo-alt-fill text-muted me-1"></i>
                                            <strong>Dirección:</strong> ${pedido.direccionEntrega}
                                        </p>
                                    </c:if>

                                    <p class="mb-0">
                                        <i class="bi bi-bag-fill text-muted me-1"></i>
                                        <strong>Tipo:</strong> ${pedido.tipoPedido.tipo}
                                    </p>
                                </div>

                                <div class="col-md-4 text-md-end">
                                    <p class="total-pedido mb-2">
                                        S/ <fmt:formatNumber value="${pedido.total}"
                                                             minFractionDigits="2"
                                                             maxFractionDigits="2"/>
                                    </p>
                                    <a href="${ctx}/pedidos?accion=detalle&id=${pedido.id}"
                                       class="btn btn-custom btn-sm">
                                        <i class="bi bi-eye-fill"></i> Ver detalle
                                    </a>
                                </div>

                            </div>
                        </div>

                    </div>
                </c:forEach>

            </c:otherwise>
        </c:choose>

    </div>
</main>

<!-- FOOTER -->
<footer class="footer py-3 mt-auto">
    <div class="container text-center">
        <small>&copy; 2025 Sopa de Muy Muy. Todos los derechos reservados.</small>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>