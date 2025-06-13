document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById('login-form');
  const loginBox = document.getElementById("login-box");
  const profileInfo = document.getElementById("profile-info");
  const userEmail = document.getElementById("user-email");
  const logoutBtn = document.getElementById("logout-btn");

  // Mostrar perfil o login según token y validación en backend
  async function showProfileOrLogin() {
    const token = localStorage.getItem("jwtToken");

    if (token) {
      try {
        // Petición para traer datos completos del usuario
        const response = await fetch("https://backendspa-2.onrender.com/api/auth/me", {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        });

        if (!response.ok) {
          // Token inválido o expirado: limpiar y mostrar login
          localStorage.removeItem("jwtToken");
          loginBox.style.display = "block";
          profileInfo.style.display = "none";
          return;
        }

        const userData = await response.json();

        // Ocultamos login y mostramos perfil
        loginBox.style.display = "none";
        profileInfo.style.display = "block";

        // Mostrar email o nombre
        userEmail.textContent = userData.email || userData.usuario || "Usuario";

        // Mostrar datos extra en div .extra-info
        const extraInfoDiv = profileInfo.querySelector(".extra-info");
        extraInfoDiv.innerHTML = "";

        if (userData.nombre) {
          const pNombre = document.createElement("p");
          pNombre.innerHTML = `<strong>Nombre:</strong> ${userData.nombre}`;
          extraInfoDiv.appendChild(pNombre);
        }
        if (userData.apellido) {
          const pApellido = document.createElement("p");
          pApellido.innerHTML = `<strong>Apellido:</strong> ${userData.apellido}`;
          extraInfoDiv.appendChild(pApellido);
        }
        if (userData.dni) {
          const pDni = document.createElement("p");
          pDni.innerHTML = `<strong>DNI:</strong> ${userData.dni}`;
          extraInfoDiv.appendChild(pDni);
        }
        if (userData.telefono) {
          const pTelefono = document.createElement("p");
          pTelefono.innerHTML = `<strong>Teléfono:</strong> ${userData.telefono}`;
          extraInfoDiv.appendChild(pTelefono);
        }

        // Mostrar botón admin si rol es ADMIN
        if (userData.rol === "ADMIN") {
          const adminBtn = document.createElement("button");
          adminBtn.textContent = "Ir a Panel Admin";
          adminBtn.addEventListener("click", () => {
            window.location.href = "admin.html";
          });
          extraInfoDiv.appendChild(adminBtn);
        }

      } catch (error) {
        console.error("Error al obtener perfil:", error);
        localStorage.removeItem("jwtToken");
        loginBox.style.display = "block";
        profileInfo.style.display = "none";
      }
    } else {
      // No hay token: mostrar login
      loginBox.style.display = "block";
      profileInfo.style.display = "none";
    }
  }

  // Manejo del formulario login
  loginForm.addEventListener('submit', async function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const data = { email, password };

    try {
        const response = await fetch("https://backendspa-2.onrender.com/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const result = await response.json();
            console.log("Respuesta login:", result);
            
            // Guardar token JWT
            localStorage.setItem("jwtToken", result.token);
            
            // Guardar datos básicos del usuario en localStorage
            localStorage.setItem("usuarioLogueado", JSON.stringify({
                id: result.id, // Asegúrate que el backend devuelva el ID
                email: result.email,
                nombre: result.nombre || result.usuario,
                apellido: result.apellido,
                telefono: result.telefono,
                dni: result.dni,
                rol: result.rol
            }));
            
            alert("Inicio de sesión exitoso");
            window.location.reload();
        } else {
            const errorText = await response.text();
            alert("Error al iniciar sesión: " + errorText);
        }
    } catch (error) {
        console.error("Error en la solicitud:", error);
        alert("No se pudo conectar con el servidor.");
    }
});
  // Logout: limpiar token y recargar
  logoutBtn.addEventListener("click", () => {
    localStorage.removeItem("jwtToken");
    window.location.reload();
  });

  // Ejecutar al cargar la página
  showProfileOrLogin();
});
