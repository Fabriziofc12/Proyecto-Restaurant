<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Sopa de Muy Muy</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background-color: #f8f4ee;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
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

        .login-section {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 15px;
        }

        .login-card {
            max-width: 440px;
            width: 100%;
            border: none;
            border-radius: 16px;
            box-shadow: 0 8px 30px rgba(78, 52, 46, 0.12);
        }

        .login-header {
            background-color: #4e342e;
            color: #fff8e1;
            text-align: center;
            padding: 30px 20px;
            border-radius: 16px 16px 0 0;
        }

        .login-header i {
            font-size: 3rem;
            margin-bottom: 10px;
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
            box-shadow: 0 0 0 0.2rem rgba(212, 163, 115, 0.25);
        }

        .link-cafe {
            color: #4e342e;
            font-weight: 600;
        }

        .link-cafe:hover {
            color: #d4a373;
        }

        .footer {
            background-color: #3e2723;
            color: white;
        }
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
            <a href="${ctx}/registro" class="text-crema fs-5" title="Registrarse">
                <i class="bi bi-person-plus"></i>
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
            <hr class="border-light">
            <li class="nav-item">
                <a class="nav-link" href="${ctx}/login">
                    <i class="bi bi-box-arrow-in-right"></i> Iniciar sesión
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${ctx}/registro">
                    <i class="bi bi-person-plus-fill"></i> Registrarse
                </a>
            </li>
        </ul>
    </div>
</div>

<section class="login-section">
    <div class="card login-card">

        <div class="login-header">
            <i class="bi bi-person-circle"></i>
            <h3 class="fw-bold mb-0">Iniciar Sesión</h3>
            <p class="mb-0 mt-1">Ingresa a tu cuenta para continuar</p>
        </div>

        <div class="card-body p-4">

            <c:if test="${not empty error}">
                <div class="alert alert-danger d-flex align-items-center" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    <span>${error}</span>
                </div>
            </c:if>

            <c:if test="${not empty exito}">
                <div class="alert alert-success d-flex align-items-center" role="alert">
                    <i class="bi bi-check-circle-fill me-2"></i>
                    <span>${exito}</span>
                </div>
            </c:if>

            <form action="${ctx}/login" method="post">

                <div class="mb-3">
                    <label for="correo" class="form-label fw-semibold">
                        <i class="bi bi-envelope-fill"></i> Correo electrónico
                    </label>
                    <input type="email"
                           class="form-control form-control-lg"
                           id="correo"
                           name="correo"
                           placeholder="correo@ejemplo.com"
                           required>
                </div>

                <div class="mb-4">
                    <label for="contrasenia" class="form-label fw-semibold">
                        <i class="bi bi-lock-fill"></i> Contraseña
                    </label>
                    <div class="input-group">
                        <input type="password"
                               class="form-control form-control-lg"
                               id="contrasenia"
                               name="contrasenia"
                               placeholder="Tu contraseña"
                               required>
                        <button class="btn btn-outline-secondary"
                                type="button"
                                onclick="togglePassword()">
                            <i class="bi bi-eye" id="iconoOjo"></i>
                        </button>
                    </div>
                </div>

                <div class="d-grid mb-3">
                    <button type="submit" class="btn btn-custom btn-lg">
                        <i class="bi bi-box-arrow-in-right"></i> Ingresar
                    </button>
                </div>

            </form>

            <div class="text-center">
                <p class="mb-0">
                    ¿No tienes cuenta?
                    <a href="${ctx}/registro" class="link-cafe">Regístrate aquí</a>
                </p>
            </div>

        </div>

        <div class="card-footer bg-transparent text-center py-3">
            <a href="${ctx}/index.jsp" class="text-muted text-decoration-none">
                <i class="bi bi-arrow-left"></i> Volver al inicio
            </a>
        </div>

    </div>
</section>

<footer class="footer py-3">
    <div class="container text-center">
        <small>&copy; 2025 Sopa de Muy Muy. Todos los derechos reservados.</small>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function togglePassword() {
        var input = document.getElementById('contrasenia');
        var icono = document.getElementById('iconoOjo');

        if (input.type === 'password') {
            input.type = 'text';
            icono.classList.remove('bi-eye');
            icono.classList.add('bi-eye-slash');
        } else {
            input.type = 'password';
            icono.classList.remove('bi-eye-slash');
            icono.classList.add('bi-eye');
        }
    }
</script>

</body>
</html>