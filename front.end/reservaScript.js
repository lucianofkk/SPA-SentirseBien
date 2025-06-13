window.addEventListener('DOMContentLoaded', () => {
  // Recuperar datos del localStorage
  const storedName = localStorage.getItem('userName') || '';
  const storedEmail = localStorage.getItem('userEmail') || '';
  const storedPhone = localStorage.getItem('userPhone') || '';

  // Setear valores en los inputs sólo si existen
  if (storedName) document.getElementById('client-name').value = storedName;
  if (storedEmail) document.getElementById('client-email').value = storedEmail;
  if (storedPhone) document.getElementById('client-phone').value = storedPhone;
});

document.addEventListener('DOMContentLoaded', function() {
    // Cargar carrito desde localStorage con verificación robusta
    let cart = [];
    try {
        const cartData = localStorage.getItem('carrito');
        if (cartData) {
            const parsed = JSON.parse(cartData);
            if (Array.isArray(parsed)) {
                cart = parsed;
            } else {
                console.warn('Datos inválidos en carrito, inicializando nuevo');
                localStorage.removeItem('carrito');
            }
        }
    } catch (e) {
        console.error('Error al cargar carrito:', e);
        localStorage.removeItem('carrito');
    }

    const cartItemsList = document.getElementById('cart-items-list');
    const appointmentDate = document.getElementById('appointment-date');
    const appointmentTime = document.getElementById('appointment-time');
    const timeError = document.getElementById('time-error');
    const payNow = document.getElementById('pay-now');
    const payLater = document.getElementById('pay-later');
    const cardDetails = document.getElementById('card-details');
    const subtotalElement = document.getElementById('subtotal');
    const discountElement = document.getElementById('discount');
    const totalElement = document.getElementById('total');
    const confirmBtn = document.getElementById('confirm-reservation');
    const reservationError = document.getElementById('reservation-error');

    // Función para guardar el carrito en localStorage
    function saveCart() {
        localStorage.setItem('carrito', JSON.stringify(cart));
    }

    // Función para limpiar el carrito
    function clearCart() {
        cart = [];
        saveCart();
    }

    // Autocompletar datos si hay usuario logueado
    const usuarioLogueado = JSON.parse(localStorage.getItem('usuarioLogueado'));
    if (usuarioLogueado) {
        const nameInput = document.getElementById('client-name');
        const emailInput = document.getElementById('client-email');
        const phoneInput = document.getElementById('client-phone');
        if (nameInput && emailInput && phoneInput) {
            nameInput.value = usuarioLogueado.nombre || '';
            emailInput.value = usuarioLogueado.email || '';
            phoneInput.value = usuarioLogueado.telefono || '';
        }
    }

    const today = new Date();
    const minDate = new Date(today);
    minDate.setDate(today.getDate() + 2);

    const formatDate = (date) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    };

    appointmentDate.min = formatDate(minDate);

    payNow.addEventListener('change', () => {
        cardDetails.style.display = payNow.checked ? 'block' : 'none';
        updatePrices();
    });

    payLater.addEventListener('change', () => {
        cardDetails.style.display = payNow.checked ? 'block' : 'none';
        updatePrices();
    });

    appointmentDate.addEventListener('change', updatePrices);

    function renderCartItems() {
        if (cart.length === 0) {
            cartItemsList.innerHTML = `
                <div class="empty-cart">
                    <i class="fas fa-shopping-cart"></i>
                    <p>No hay servicios en tu carrito</p>
                    <a href="servicios.html" class="btn">Ver Servicios</a>
                </div>
            `;
            return;
        }

        let html = '';
        cart.forEach((item, index) => {
            html += `
                <div class="cart-item" data-id="${item.id}">
                    <div class="item-info">
                        <h3>${item.nombre}</h3>
                        <p>${item.duracion}</p>
                    </div>
                    <div class="item-price">
                        ${item.precio}
                    </div>
                    <div class="remove-item" data-index="${index}">
                        <i class="fas fa-trash"></i>
                    </div>
                </div>
            `;
        });

        cartItemsList.innerHTML = html;

        document.querySelectorAll('.remove-item').forEach(btn => {
            btn.addEventListener('click', function() {
                const index = parseInt(this.getAttribute('data-index'));
                if (index >= 0 && index < cart.length) {
                    cart.splice(index, 1);
                    saveCart();
                    renderCartItems();
                    updatePrices();
                }
            });
        });

        updatePrices();
    }

    function updatePrices() {
        if (cart.length === 0) {
            subtotalElement.textContent = '$0';
            discountElement.textContent = '-$0';
            totalElement.textContent = '$0';
            return;
        }

        let subtotal = 0;
        cart.forEach(item => {
            const price = parseFloat(item.precio.replace('$', '').replace('.', '').replace(',', '.'));
            subtotal += isNaN(price) ? 0 : price;
        });

        let discount = 0;
        let total = subtotal;

        if (payNow.checked && appointmentDate.value) {
            const selectedDate = new Date(appointmentDate.value);
            const now = new Date();
            const diffTime = selectedDate - now;
            const diffHours = diffTime / (1000 * 60 * 60);

            if (diffHours > 48) {
                discount = subtotal * 0.15;
                total = subtotal - discount;
            }
        }

        subtotalElement.textContent = `$${subtotal.toFixed(2)}`;
        discountElement.textContent = discount > 0 
            ? `-$${discount.toFixed(2)} <span class="discount-badge">15% OFF</span>` 
            : `-$0`;
        totalElement.textContent = `$${total.toFixed(2)}`;
    }

    // Función para generar PDF
    async function generarComprobantePDF(turno) {
        try {
            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();
            
            // Logo del spa
            const logoUrl = 'images/spa.png';
            const logoResponse = await fetch(logoUrl);
            const logoBlob = await logoResponse.blob();
            const logoDataUrl = await new Promise((resolve) => {
                const reader = new FileReader();
                reader.onload = () => resolve(reader.result);
                reader.readAsDataURL(logoBlob);
            });
            
            doc.addImage(logoDataUrl, 'PNG', 10, 10, 30, 30);
            doc.setFontSize(20);
            doc.setTextColor(40, 40, 40);
            doc.text('Comprobante de Turno', 105, 20, { align: 'center' });
            
            // Información del turno
            doc.setFontSize(12);
            doc.text(`N° de Turno: ${turno.id || 'N/A'}`, 15, 50);
            doc.text(`Fecha: ${turno.fecha}`, 15, 60);
            doc.text(`Hora: ${turno.horaInicio} - ${turno.horaFin}`, 15, 70);
            doc.text(`Estado: ${turno.estado}`, 15, 80);
            
            // Detalles de pago
            doc.text(`Método de pago: ${turno.metodoPago}`, 15, 95);
            doc.text(`Pagado: ${turno.pagado ? 'Sí' : 'No'}`, 15, 105);
            doc.text(`Monto: $${turno.monto.toFixed(2)}`, 15, 115);
            
            // Detalle del servicio
            doc.text(`Detalle: ${turno.detalle}`, 15, 130);
            
            doc.save(`turno_${turno.id || 'nuevo'}.pdf`);
            return true;
        } catch (error) {
            console.error('Error al generar PDF:', error);
            return false;
        }
    }

    // Función auxiliar para calcular horaFin basada en la duración del servicio
    function calcularHoraFin(horaInicio, duracion) {
        if (!horaInicio || !duracion) return horaInicio;
        
        const [horasStr, minutosStr] = duracion.split(' ')[0].split(':');
        const horas = parseInt(horasStr) || 0;
        const minutos = parseInt(minutosStr) || 0;
        
        const [hora, minuto] = horaInicio.split(':');
        const fecha = new Date();
        fecha.setHours(parseInt(hora), fecha.setMinutes(parseInt(minuto)));
        fecha.setHours(fecha.getHours() + horas);
        fecha.setMinutes(fecha.getMinutes() + minutos);
        
        return `${String(fecha.getHours()).padStart(2, '0')}:${String(fecha.getMinutes()).padStart(2, '0')}:00`;
    }

   confirmBtn.addEventListener('click', async function() {
    reservationError.style.display = "none";

    // 1. Primero obtener el token JWT
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        reservationError.textContent = "No estás autenticado. Por favor, inicia sesión primero.";
        reservationError.style.display = "block";
        setTimeout(() => {
            window.location.href = 'login.html';
        }, 3000);
        return;
    }

    // 2. Validaciones del formulario (tu código existente)
    if (cart.length === 0) {
        reservationError.textContent = "No hay servicios en tu carrito";
        reservationError.style.display = "block";
        return;
    }
    // ... (resto de validaciones)

    try {
        // 3. Obtener datos del usuario
        let usuarioLogueado;
        try {
            const usuarioData = localStorage.getItem('usuarioLogueado');
            if (usuarioData) {
                usuarioLogueado = JSON.parse(usuarioData);
            } else {
                // Si no está en localStorage, obtener del backend
                const response = await fetch('https://backendspa-2.onrender.com/api/auth/me', {
                    headers: {
                        'Authorization': `Bearer ${token}`  // Usamos el token definido arriba
                    }
                });
                
                if (response.ok) {
                    usuarioLogueado = await response.json();
                    localStorage.setItem('usuarioLogueado', JSON.stringify(usuarioLogueado));
                }
            }
        } catch (e) {
            console.error('Error al obtener usuario:', e);
        }

        // 4. Crear el objeto turnoDTO
        const turnoDTO = {
            clienteId: usuarioLogueado?.id || null,
            // ... (resto de propiedades)
        };

        // 5. Enviar al backend
        const response = await fetch('/api/turnos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`  // Usamos el mismo token
            },
            body: JSON.stringify(turnoDTO)
        });

        // ... (resto del código)

    } catch (error) {
        console.error('Error al confirmar reserva:', error);
        // ... (manejo de errores)
    }
});
    // Inicializar
    renderCartItems();
    updatePrices();
});

// Función global para agregar al carrito desde otras páginas
window.addToCart = function(service) {
    let cart = JSON.parse(localStorage.getItem('carrito')) || [];
    cart.push(service);
    localStorage.setItem('carrito', JSON.stringify(cart));
    alert(`Servicio ${service.nombre} agregado al carrito`);
};