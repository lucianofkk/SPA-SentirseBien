// Obtener el carrito del localStorage o inicializar vacío
let carrito = JSON.parse(localStorage.getItem("carritoServicios")) || [];

// Función para manejar el clic de agregar
function agregarAlCarrito(boton) {
  const card = boton.closest(".servicio-card") || boton.closest(".grupo-card");
  const nombre = card.querySelector("h3").innerText;

  let precioTexto = card.querySelector(".servicio-precio, .grupo-precio").innerText;
  let precio = parseFloat(precioTexto.replace(/[^0-9.-]+/g, ""));

  let duracionTexto = card.querySelector(".servicio-duracion, .grupo-duracion").innerText;
  let duracion = parseInt(duracionTexto.replace(/[^0-9]/g, ""));

  const servicio = { nombre, precio, duracion };

  carrito.push(servicio);
  localStorage.setItem("carritoServicios", JSON.stringify(carrito));

  alert(`"${nombre}" fue agregado al carrito.`);
}

// Asignar el evento a los botones estáticos
document.querySelectorAll(".btn-agregar").forEach((boton) => {
  boton.addEventListener("click", () => agregarAlCarrito(boton));
});

// ================================
// Cargar servicios dinámicos desde backend
// ================================
document.addEventListener("DOMContentLoaded", () => {
  const contenedor = document.getElementById("servicios-dinamicos");
  const URL_API = "http://localhost:8080/api/servicios";

  fetch(URL_API)
    .then((res) => {
      if (!res.ok) throw new Error("Error al obtener los servicios");
      return res.json();
    })
    .then((servicios) => {
      if (!servicios.length) {
        contenedor.innerHTML = "<p style='text-align:center;'>No hay servicios disponibles actualmente.</p>";
        return;
      }

      servicios.forEach((servicio) => {
        const card = document.createElement("div");
        card.className = "servicio-card";
        card.innerHTML = `
          <div class="servicio-imagen" style="background-image: url('images/default.jpg');"></div>
          <div class="servicio-contenido">
            <h3>${servicio.nombre}</h3>
            <p style="min-height: 60px;">${servicio.descripcion || "Servicio de spa"}</p>
            <div class="servicio-info">
              <div class="servicio-precio">$${servicio.precio}</div>
              <div class="servicio-duracion"><i class="far fa-clock"></i> 45 min</div>
            </div>
            <button class="btn-agregar"><i class="fas fa-cart-plus"></i> Agregar al carrito</button>
          </div>
        `;
        contenedor.appendChild(card);
      });

      // Reasignar eventos a los nuevos botones dinámicos
      document.querySelectorAll("#servicios-dinamicos .btn-agregar").forEach((boton) => {
        boton.addEventListener("click", () => agregarAlCarrito(boton));
      });
    })
    .catch((error) => {
      console.error(error);
      contenedor.innerHTML = `<p style="color:red;text-align:center;">${error.message}</p>`;
    });
});
