document.addEventListener('DOMContentLoaded', function() {
    const carousel = document.querySelector('.carousel-fondo');
    const slides = document.querySelectorAll('.carousel-slide');
    let currentIndex = 0;
    const intervalTime = 12000; // Tiempo en milisegundos entre cada cambio de imagen (3 segundos)
    let intervalId;

    function nextSlide() {
        slides[currentIndex].classList.remove('active');
        currentIndex = (currentIndex + 1) % slides.length;
        slides[currentIndex].classList.add('active');
    }

    function startCarousel() {
        intervalId = setInterval(nextSlide, intervalTime);
    }

    function stopCarousel() {
        clearInterval(intervalId);
    }

    // Inicializar el carrusel
    if (slides.length > 0) {
        slides[0].classList.add('active'); // Mostrar la primera imagen al cargar
        startCarousel();
    }

    // Opcional: Pausar el carrusel al pasar el mouse (puedes descomentar esto si lo deseas)
    /*
    carousel.addEventListener('mouseenter', stopCarousel);
    carousel.addEventListener('mouseleave', startCarousel);
    */
});