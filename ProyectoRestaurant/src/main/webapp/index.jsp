<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sopa de Muy Muy</title>

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
        .nav-link,
        .text-crema {
            color: #fff8e1 !important;
        }

        .hero {
            background: linear-gradient(rgba(78,52,46,.75), rgba(78,52,46,.75)),
                        url('https://images.unsplash.com/photo-1544025162-d76694265947?auto=format&fit=crop&w=1400&q=80')
                        center/cover no-repeat;
            min-height: 85vh;
            display: flex;
            align-items: center;
            color: white;
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

        .section-title {
            color: #4e342e;
            font-weight: bold;
        }

        .card-product img {
            height: 220px;
            object-fit: cover;
        }

        .footer {
            background-color: #3e2723;
            color: white;
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
    </style>
</head>
<body>

<nav class="navbar navbar-dark navbar-custom sticky-top">
    <div class="container">
        <button class="navbar-toggler border-0" type="button" data-bs-toggle="offcanvas" data-bs-target="#menuLateral" aria-controls="menuLateral">
            <span class="navbar-toggler-icon"></span>
        </button>

        <a class="navbar-brand fw-bold mx-auto" href="index.jsp">
            <i class="bi bi-cup-hot-fill"></i> Sopa de Muy Muy
        </a>

        <div>
            <c:choose>
                <c:when test="${not empty sessionScope.usuario}">
                    <a href="carrito?accion=ver" class="text-crema me-3 fs-5">
                        <i class="bi bi-cart3"></i>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="login" class="text-crema fs-5">
                        <i class="bi bi-person-circle"></i>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<div class="offcanvas offcanvas-start offcanvas-custom text-white" tabindex="-1" id="menuLateral" aria-labelledby="menuLateralLabel">
    <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="menuLateralLabel">
            <i class="bi bi-list"></i> Menú
        </h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Cerrar"></button>
    </div>

    <div class="offcanvas-body">
        <c:if test="${not empty sessionScope.usuario}">
            <p class="mb-4">
                Bienvenido, <strong>${sessionScope.usuario.nombre}</strong>
            </p>
        </c:if>

        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="index.jsp"><i class="bi bi-house-door-fill"></i> Inicio</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/menu"><i class="bi bi-journal-text"></i> Menú</a>
            </li>

            <c:if test="${not empty sessionScope.usuario}">
                <li class="nav-item">
                    <a class="nav-link" href="carrito?accion=ver"><i class="bi bi-cart-fill"></i> Mi carrito</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="pedidos?accion=mis-pedidos"><i class="bi bi-bag-check-fill"></i> Mis pedidos</a>
                </li>
            </c:if>

            <c:if test="${sessionScope.rol == 'ADMIN'}">
                <hr class="border-light">
                <li class="nav-item">
                    <a class="nav-link" href="productos?accion=listar"><i class="bi bi-box-seam"></i> Administrar productos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="usuarios"><i class="bi bi-people-fill"></i> Administrar usuarios</a>
                </li>
            </c:if>

            <hr class="border-light">

            <c:choose>
                <c:when test="${not empty sessionScope.usuario}">
                    <li class="nav-item">
                        <a class="nav-link" href="logout"><i class="bi bi-box-arrow-right"></i> Cerrar sesión</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link" href="login"><i class="bi bi-box-arrow-in-right"></i> Iniciar sesión</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="registro"><i class="bi bi-person-plus-fill"></i> Registrarse</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>

<section class="hero">
    <div class="container text-center">
        <h1 class="display-4 fw-bold">Bienvenido a Sopa de Muy Muy</h1>
        <p class="lead mb-4">
            Sabores caseros, sopas tradicionales y platos que reconfortan el alma.
        </p>
        <a href="${pageContext.request.contextPath}/menu" class="btn btn-custom btn-lg me-2">
            <i class="bi bi-journal-text"></i> Ver menú
        </a>
        <c:if test="${empty sessionScope.usuario}">
            <a href="registro" class="btn btn-outline-light btn-lg">
                <i class="bi bi-person-plus"></i> Crear cuenta
            </a>
        </c:if>
    </div>
</section>

<section class="py-5">
    <div class="container">
        <div class="text-center mb-5">
            <h2 class="section-title">Nuestros destacados</h2>
            <p class="text-muted">Disfruta de los platos favoritos de nuestros clientes</p>
        </div>

        <div class="row g-4">
            <div class="col-md-4">
                <div class="card shadow-sm h-100 card-product">
                    <img src="https://images.unsplash.com/photo-1604908176997-125f25cc6f3d?auto=format&fit=crop&w=800&q=80" class="card-img-top" alt="Sopa">
                    <div class="card-body">
                        <h5 class="card-title">Sopa de Muy Muy</h5>
                        <p class="card-text">Nuestra especialidad de la casa, preparada con ingredientes frescos y mucho sabor.</p>
                        <p class="fw-bold text-success">S/ 18.90</p>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card shadow-sm h-100 card-product">
                    <img src="https://images.unsplash.com/photo-1515003197210-e0cd71810b5f?auto=format&fit=crop&w=800&q=80" class="card-img-top" alt="Segundo">
                    <div class="card-body">
                        <h5 class="card-title">Lomo Saltado</h5>
                        <p class="card-text">Uno de los segundos más pedidos, con carne jugosa, papas fritas y arroz.</p>
                        <p class="fw-bold text-success">S/ 22.00</p>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card shadow-sm h-100 card-product">
                    <img src="https://images.unsplash.com/photo-1551024506-0bccd828d307?auto=format&fit=crop&w=800&q=80" class="card-img-top" alt="Postre">
                    <div class="card-body">
                        <h5 class="card-title">Arroz con Leche</h5>
                        <p class="card-text">Un postre tradicional cremoso y dulce para cerrar tu comida de la mejor forma.</p>
                        <p class="fw-bold text-success">S/ 7.00</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="py-5 bg-white">
    <div class="container">
        <div class="row align-items-center g-4">
            <div class="col-md-6">
                <img src="https://images.unsplash.com/photo-1552566626-52f8b828add9?auto=format&fit=crop&w=1000&q=80"
                     class="img-fluid rounded shadow" alt="Restaurante">
            </div>
            <div class="col-md-6">
                <h2 class="section-title">Tradición y sabor en cada plato</h2>
                <p class="text-muted">
                    En <strong>Sopa de Muy Muy</strong> nos especializamos en ofrecer comida casera con el auténtico sabor de hogar.
                    Desde sopas calientes hasta segundos contundentes y postres deliciosos.
                </p>
                <p class="text-muted">
                    Nuestro objetivo es que cada cliente disfrute una experiencia cálida, rápida y deliciosa.
                </p>
                <a href="${pageContext.request.contextPath}/menu" class="btn btn-custom">
                    <i class="bi bi-arrow-right-circle"></i> Explorar menú
                </a>
            </div>
        </div>
    </div>
</section>

<footer class="footer py-4 mt-5">
    <div class="container text-center">
        <p class="mb-1 fw-bold">Sopa de Muy Muy</p>
        <p class="mb-1">Comida tradicional • Sopas • Segundos • Postres</p>
        <small>&copy; 2025 Todos los derechos reservados</small>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>