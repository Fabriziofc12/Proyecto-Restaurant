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
    <title>Menú - Sopa de Muy Muy</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background-color: #f8f4ee;
        }

        .navbar-custom {
            background-color: #4e342e;
        }

        .navbar-brand,
        .text-crema {
            color: #fff8e1 !important;
        }

        .offcanvas-custom {
            background-color: #4e342e;
        }

        .offcanvas-custom .nav-link {
            color: #fff8e1 !important;
            font-size: 1.05rem;
            margin-bottom: 8px;
        }

        .offcanvas-custom .nav-link:hover {
            color: #ffd54f !important;
        }

        .hero-menu {
            background: linear-gradient(rgba(78,52,46,.80), rgba(78,52,46,.80)),
                        url('https://images.unsplash.com/photo-1515003197210-e0cd71810b5f?auto=format&fit=crop&w=1400&q=80')
                        center/cover no-repeat;
            color: white;
            padding: 70px 0;
        }

        .btn-custom {
            background-color: #d4a373;
            border: none;
            color: white;
        }

        .btn-custom:hover {
            background-color: #bc8a5f;
            color: white;
        }

        .category-pill {
            text-decoration: none;
            border-radius: 50px;
            padding: 10px 18px;
            border: 1px solid #d7ccc8;
            color: #4e342e;
            background: white;
            transition: 0.3s;
            display: inline-block;
            margin: 5px;
            font-weight: 500;
        }

        .category-pill:hover,
        .category-pill.active {
            background-color: #4e342e;
            color: white;
            border-color: #4e342e;
        }

        .product-card img {
            height: 220px;
            object-fit: cover;
        }

        .product-card {
            border: none;
            border-radius: 16px;
            overflow: hidden;
        }

        .precio {
            font-size: 1.2rem;
            font-weight: bold;
            color: #2e7d32;
        }

        .footer {
            background-color: #3e2723;
            color: white;
        }

        .badge-categoria {
            background-color: #efebe9;
            color: #5d4037;
            font-weight: 500;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-dark navbar-custom sticky-top">
    <div class="container">
        <button class="navbar-toggler border-0" type="button"
                data-bs-toggle="offcanvas" data-bs-target="#menuLateral"
                aria-controls="menuLateral">
            <span class="navbar-toggler-icon"></span>
        </button>

        <a class="navbar-brand fw-bold mx-auto" href="${ctx}/index.jsp">
            <i class="bi bi-cup-hot-fill"></i> Sopa de Muy Muy
        </a>

        <div>
            <c:choose>
                <c:when test="${not empty sessionScope.usuario}">
                    <a href="${ctx}/carrito?accion=ver" class="text-crema me-2 fs-5">
                        <i class="bi bi-cart3"></i>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${ctx}/login" class="text-crema fs-5">
                        <i class="bi bi-person-circle"></i>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<div class="offcanvas offcanvas-start offcanvas-custom text-white" tabindex="-1" id="menuLateral">
    <div class="offcanvas-header">
        <h5 class="offcanvas-title">
            <i class="bi bi-list"></i> Menú
        </h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas"></button>
    </div>

    <div class="offcanvas-body">
        <c:if test="${not empty sessionScope.usuario}">
            <p class="mb-4">Bienvenido, <strong>${sessionScope.usuario.nombre}</strong></p>
        </c:if>

        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="${ctx}/index.jsp"><i class="bi bi-house-door-fill"></i> Inicio</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${ctx}/menu"><i class="bi bi-journal-text"></i> Menú</a>
            </li>

            <c:if test="${not empty sessionScope.usuario}">
                <li class="nav-item">
                    <a class="nav-link" href="${ctx}/carrito?accion=ver"><i class="bi bi-cart-fill"></i> Mi carrito</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${ctx}/pedidos?accion=mis-pedidos"><i class="bi bi-bag-check-fill"></i> Mis pedidos</a>
                </li>
            </c:if>

            <c:if test="${sessionScope.rol eq 'ADMIN'}">
                <hr class="border-light">
                <li class="nav-item">
                    <a class="nav-link" href="${ctx}/productos?accion=listar"><i class="bi bi-box-seam"></i> Administrar productos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${ctx}/usuarios"><i class="bi bi-people-fill"></i> Administrar usuarios</a>
                </li>
            </c:if>

            <hr class="border-light">

            <c:choose>
                <c:when test="${not empty sessionScope.usuario}">
                    <li class="nav-item">
                        <a class="nav-link" href="${ctx}/logout"><i class="bi bi-box-arrow-right"></i> Cerrar sesión</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link" href="${ctx}/login"><i class="bi bi-box-arrow-in-right"></i> Iniciar sesión</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${ctx}/registro"><i class="bi bi-person-plus-fill"></i> Registrarse</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>

<section class="hero-menu">
    <div class="container text-center">
        <h1 class="fw-bold display-5">Nuestro Menú</h1>
        <p class="lead mb-0">Descubre sopas, segundos, bebidas y postres preparados con sabor casero</p>
    </div>
</section>

<section class="py-4">
    <div class="container text-center">
        <a href="${ctx}/menu"
           class="category-pill ${empty categoriaActiva ? 'active' : ''}">
            Todas
        </a>

        <c:forEach var="cat" items="${categorias}">
            <a href="${ctx}/menu?idCategoria=${cat.id}"
               class="category-pill ${categoriaActiva == cat.id ? 'active' : ''}">
                ${cat.nombre}
            </a>
        </c:forEach>
    </div>
</section>

<section class="pb-5">
    <div class="container">
        <div class="row g-4">

            <c:choose>
                <c:when test="${not empty productos}">
                    <c:forEach var="producto" items="${productos}">
                        <div class="col-md-6 col-lg-4">
                            <div class="card shadow-sm h-100 product-card">

                                <c:choose>
                                    <c:when test="${not empty producto.imagen}">
                                        <img src="${producto.imagen}" class="card-img-top" alt="${producto.nombre}">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="https://via.placeholder.com/600x400?text=Producto" class="card-img-top" alt="${producto.nombre}">
                                    </c:otherwise>
                                </c:choose>

                                <div class="card-body d-flex flex-column">
                                    <div class="mb-2">
                                        <span class="badge badge-categoria">
                                            <c:out value="${producto.categoria.nombre}" default="Sin categoría"/>
                                        </span>
                                    </div>

                                    <h5 class="card-title">${producto.nombre}</h5>

                                    <p class="card-text text-muted flex-grow-1">
                                        <c:out value="${producto.descripcion}" default="Delicioso plato de nuestro restaurante."/>
                                    </p>

                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <span class="precio">
                                            S/ <fmt:formatNumber value="${producto.precio}" minFractionDigits="2" maxFractionDigits="2"/>
                                        </span>
                                    </div>

                                    <c:choose>
                                        <c:when test="${not empty sessionScope.usuario}">
                                            <form action="${ctx}/carrito" method="post" class="d-grid gap-2">
                                                <input type="hidden" name="accion" value="agregar">
                                                <input type="hidden" name="idProducto" value="${producto.id}">
                                                <input type="hidden" name="cantidad" value="1">

                                                <button type="submit" class="btn btn-custom">
                                                    <i class="bi bi-cart-plus-fill"></i> Agregar al carrito
                                                </button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${ctx}/login" class="btn btn-outline-secondary w-100">
                                                <i class="bi bi-person-lock"></i> Inicia sesión para comprar
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>

                <c:otherwise>
                    <div class="col-12">
                        <div class="alert alert-warning text-center">
                            No hay productos disponibles en esta categoría.
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</section>

<footer class="footer py-4 mt-5">
    <div class="container text-center">
        <p class="mb-1 fw-bold">Sopa de Muy Muy</p>
        <p class="mb-1">Sopas • Segundos • Bebidas • Postres</p>
        <small>&copy; 2025 Todos los derechos reservados</small>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>