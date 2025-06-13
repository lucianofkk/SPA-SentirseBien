document.addEventListener('DOMContentLoaded', function() {
    // Carrusel (tu código actual)
    const carousel = document.querySelector('.carousel-fondo');
    const slides = document.querySelectorAll('.carousel-slide');
    let currentIndex = 0;
    const intervalTime = 12000;
    let intervalId;

    function nextSlide() {
        slides[currentIndex].classList.remove('active');
        currentIndex = (currentIndex + 1) % slides.length;
        slides[currentIndex].classList.add('active');
    }

    function startCarousel() {
        intervalId = setInterval(nextSlide, intervalTime);
    }

    if (slides.length > 0) {
        slides[0].classList.add('active');
        startCarousel();
    }

    // Lógica de login/perfil
    const loginForm = document.getElementById('loginForm');
    const profileBox = document.getElementById('profile-box');
    const loginBox = document.getElementById('login-form');
    const profileLink = document.getElementById('profile-link');
    const logoutBtn = document.getElementById('logout-btn');

    // Verificar si hay usuario logueado
    const user = JSON.parse(localStorage.getItem('spaUser'));

    if (user) {
        showProfile(user);
    }

    // Manejar login
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Simular login (en producción, usa una API)
            const fakeUser = {
                nombre: "Franco",
                apellido: "Tofanelli",
                dni: "12345678",
                telefono: "3624492683",
                email: document.getElementById('email').value
            };

            localStorage.setItem('spaUser', JSON.stringify(fakeUser));
            showProfile(fakeUser);
        });
    }

    // Mostrar perfil
    function showProfile(user) {
        if (profileBox && loginBox) {
            loginBox.style.display = 'none';
            profileBox.style.display = 'block';
            
            document.getElementById('profile-nombre').textContent = user.nombre;
            document.getElementById('profile-apellido').textContent = user.apellido;
            document.getElementById('profile-dni').textContent = user.dni;
            document.getElementById('profile-telefono').textContent = user.telefono;
            document.getElementById('profile-email').textContent = user.email;
        }

        if (profileLink) {
            profileLink.textContent = "Perfil";
        }
    }

    // Cerrar sesión
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function() {
            localStorage.removeItem('spaUser');
            profileBox.style.display = 'none';
            loginBox.style.display = 'block';
            profileLink.textContent = "Iniciar Sesión";
        });
    }
});