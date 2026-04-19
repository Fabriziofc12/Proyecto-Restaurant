<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrarse - Sopa de Muy Muy</title>

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

        .registro-section {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 15px;
        }

        .registro-card {
            max-width: 500px;
            width: 100%;
            border: none;
            border-radius: 16px;
            box-shadow: 0 8px 30px rgba(78, 52, 46, 0.12);
        }

        .registro-header {
            background-color: #4e342e;
            color: #fff8e1;
            text-align: center;
            padding: 30px 20px;
            border-radius: 16px 16px 0 0;
        }

        .registro-header i {
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

        .requisito {
            font-size: 0.85rem;
        }

        .requisito i {
            width: 16px;
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
            <a href="${ctx}/login" class="text-crema fs-5" title="Iniciar sesión">
                <i class="bi bi-box-arrow-in-right"></i>
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

<section class="registro-section">
    <div class="card registro-card">

        <div class="registro-header">
            <i class="bi bi-person-plus-fill"></i>
            <h3 class="fw-bold mb-0">Crear Cuenta</h3>
            <p class="mb-0 mt-1">Únete y disfruta de nuestros platos</p>
        </div>

        <div class="card-body p-4">

            <c:if test="${not empty error}">
                <div class="alert alert-danger d-flex align-items-center" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    <span>${error}</span>
                </div>
            </c:if>

            <form action="${ctx}/registro" method="post" id="formRegistro">

                <!-- Nombre -->
                <div class="mb-3">
                    <label for="nombre" class="form-label fw-semibold">
                        <i class="bi bi-person-fill"></i> Nombre completo
                    </label>
                    <input type="text"
                           class="form-control form-control-lg"
                           id="nombre"
                           name="nombre"
                           placeholder="Ej: Juan Pérez"
                           required>
                </div>

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

                <div class="mb-3">
                    <label for="telefono" class="form-label fw-semibold">
                        <i class="bi bi-telephone-fill"></i> Teléfono
                    </label>
                    <input type="tel"
                           class="form-control form-control-lg"
                           id="telefono"
                           name="telefono"
                           placeholder="987 654 321"
                           maxlength="20">
                </div>

                <div class="mb-3">
                    <label for="contrasenia" class="form-label fw-semibold">
                        <i class="bi bi-lock-fill"></i> Contraseña
                    </label>
                    <div class="input-group">
                        <input type="password"
                               class="form-control form-control-lg"
                               id="contrasenia"
                               name="contrasenia"
                               placeholder="Mínimo 6 caracteres"
                               minlength="6"
                               required>
                        <button class="btn btn-outline-secondary" type="button"
                                onclick="togglePass('contrasenia', 'iconoOjo1')">
                            <i class="bi bi-eye" id="iconoOjo1"></i>
                        </button>
                    </div>
                </div>

                <div class="mb-2">
                    <label for="confirmar" class="form-label fw-semibold">
                        <i class="bi bi-lock-fill"></i> Confirmar contraseña
                    </label>
                    <div class="input-group">
                        <input type="password"
                               class="form-control form-control-lg"
                               id="confirmar"
                               placeholder="Repite tu contraseña"
                               minlength="6"
                               required>
                        <button class="btn btn-outline-secondary" type="button"
                                onclick="togglePass('confirmar', 'iconoOjo2')">
                            <i class="bi bi-eye" id="iconoOjo2"></i>
                        </button>
                    </div>
                </div>

                <div id="msgConfirmar" class="mb-3"></div>

                <!-- Requisitos -->
                <div class="mb-4 p-3 rounded" style="background:#efebe9;">
                    <p class="mb-1 fw-semibold" style="font-size:0.9rem;">
                        <i class="bi bi-shield-check"></i> Requisitos:
                    </p>
                    <p class="requisito mb-1" id="reqLargo">
                        <i class="bi bi-circle text-muted"></i> Mínimo 6 caracteres
                    </p>
                    <p class="requisito mb-0" id="reqIgual">
                        <i class="bi bi-circle text-muted"></i> Las contraseñas coinciden
                    </p>
                </div>

                <div class="d-grid mb-3">
                    <button type="submit" class="btn btn-custom btn-lg" id="btnRegistro">
                        <i class="bi bi-person-check-fill"></i> Crear mi cuenta
                    </button>
                </div>

            </form>

            <div class="text-center">
                <p class="mb-0">
                    ¿Ya tienes cuenta?
                    <a href="${ctx}/login" class="link-cafe">Inicia sesión</a>
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

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function togglePass(inputId, iconId) {
        var input = document.getElementById(inputId);
        var icono = document.getElementById(iconId);
        if (input.type === 'password') {
            input.type = 'text';
            icono.classList.replace('bi-eye', 'bi-eye-slash');
        } else {
            input.type = 'password';
            icono.classList.replace('bi-eye-slash', 'bi-eye');
        }
    }

    var pass     = document.getElementById('contrasenia');
    var confirmar = document.getElementById('confirmar');
    var reqLargo  = document.getElementById('reqLargo');
    var reqIgual  = document.getElementById('reqIgual');
    var msgDiv    = document.getElementById('msgConfirmar');
    var form      = document.getElementById('formRegistro');

    function validar() {
        var p = pass.value;
        var c = confirmar.value;

        if (p.length >= 6) {
            reqLargo.innerHTML = '<i class="bi bi-check-circle-fill text-success"></i> Mínimo 6 caracteres';
        } else {
            reqLargo.innerHTML = '<i class="bi bi-circle text-muted"></i> Mínimo 6 caracteres';
        }

        if (c.length > 0 && p === c) {
            reqIgual.innerHTML = '<i class="bi bi-check-circle-fill text-success"></i> Las contraseñas coinciden';
            msgDiv.innerHTML = '';
        } else if (c.length > 0) {
            reqIgual.innerHTML = '<i class="bi bi-x-circle-fill text-danger"></i> Las contraseñas coinciden';
            msgDiv.innerHTML = '<small class="text-danger"><i class="bi bi-exclamation-circle"></i> Las contraseñas no coinciden</small>';
        } else {
            reqIgual.innerHTML = '<i class="bi bi-circle text-muted"></i> Las contraseñas coinciden';
            msgDiv.innerHTML = '';
        }
    }

    pass.addEventListener('input', validar);
    confirmar.addEventListener('input', validar);

    form.addEventListener('submit', function(e) {
        if (pass.value.length < 6) {
            e.preventDefault();
            alert('La contraseña debe tener al menos 6 caracteres.');
            return;
        }
        if (pass.value !== confirmar.value) {
            e.preventDefault();
            alert('Las contraseñas no coinciden.');
            return;
        }
    });
</script>

</body>
</html>