// Carrusel idéntico al de inicio.html
document.addEventListener('DOMContentLoaded', function() {
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

    // Inicializar el carrusel
    if (slides.length > 0) {
        slides[0].classList.add('active');
        startCarousel();
    }
});