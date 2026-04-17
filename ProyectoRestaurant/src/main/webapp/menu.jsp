<div class="btn-group mb-4">
    <a href="ProductoServlet?accion=listar" class="btn btn-outline-primary">Todos</a>
    <c:forEach var="cat" items="${categorias}">
        <a href="ProductoServlet?accion=filtrar&idCat=${cat.id}" class="btn btn-outline-primary">${cat.nombre}</a>
    </c:forEach>
</div>

<div class="row">
    <c:forEach var="p" items="${productos}">
        <div class="col-md-4 mb-3">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">${p.nombre}</h5>
                    <p class="card-text text-muted">${p.categoria.nombre}</p>
                    <p class="card-text">${p.descripcion}</p>
                    <h6 class="text-primary">$ ${p.precio}</h6>
                </div>
                <div class="card-footer text-center">
                    <a href="CarritoServlet?accion=agregar&id=${p.id}" class="btn btn-success">Agregar al carrito</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>