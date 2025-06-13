document.addEventListener('DOMContentLoaded', function() {
    // Cargar carrito desde localStorage
    let cart = JSON.parse(localStorage.getItem('carrito')) || [];
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
    
    // Establecer fecha mínima (hoy + 2 días)
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
    
    // Mostrar/ocultar detalles de tarjeta según método de pago
    payNow.addEventListener('change', () => {
        cardDetails.style.display = payNow.checked ? 'block' : 'none';
        updatePrices();
    });
    
    payLater.addEventListener('change', () => {
        cardDetails.style.display = payNow.checked ? 'block' : 'none';
        updatePrices();
    });
    
    // Actualizar precios cuando cambia la fecha o método de pago
    appointmentDate.addEventListener('change', updatePrices);
    
    // Renderizar items del carrito
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
        
        // Agregar event listeners a los botones de eliminar
        document.querySelectorAll('.remove-item').forEach(btn => {
            btn.addEventListener('click', function() {
                const index = this.getAttribute('data-index');
                cart.splice(index, 1);
                localStorage.setItem('carrito', JSON.stringify(cart));
                renderCartItems();
                updatePrices();
            });
        });
        
        updatePrices();
    }
    
    // Actualizar precios y descuentos
    function updatePrices() {
        if (cart.length === 0) return;
        
        // Calcular subtotal (sumar todos los precios)
        let subtotal = 0;
        cart.forEach(item => {
            const price = parseFloat(item.precio.replace('$', '').replace('.', '').replace(',', '.'));
            subtotal += price;
        });
        
        // Verificar si aplica descuento (pago online y más de 48 horas antes)
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
        
        // Actualizar UI
        subtotalElement.textContent = `$${subtotal.toFixed(2)}`;
        discountElement.textContent = `-$${discount.toFixed(2)}`;
        totalElement.textContent = `$${total.toFixed(2)}`;
        
        if (discount > 0) {
            discountElement.innerHTML = `-$${discount.toFixed(2)} <span class="discount-badge">15% OFF</span>`;
        } else {
            discountElement.innerHTML = `-$0`;
        }
    }
    
    // Validar y confirmar reserva
    confirmBtn.addEventListener('click', function() {
        if (cart.length === 0) {
            reservationError.textContent = "No hay servicios en tu carrito";
            reservationError.style.display = "block";
            return;
        }
        
        if (!appointmentDate.value) {
            reservationError.textContent = "Por favor seleccione una fecha";
            reservationError.style.display = "block";
            return;
        }
        
        if (!appointmentTime.value) {
            timeError.style.display = "block";
            reservationError.textContent = "Por favor complete todos los campos";
            reservationError.style.display = "block";
            return;
        } else {
            timeError.style.display = "none";
        }
        
        if (!document.getElementById('client-name').value || 
            !document.getElementById('client-email').value || 
            !document.getElementById('client-phone').value) {
            reservationError.textContent = "Por favor complete todos los campos";
            reservationError.style.display = "block";
            return;
        }
        
        if (payNow.checked) {
            if (!document.getElementById('card-number').value || 
                !document.getElementById('card-name').value || 
                !document.getElementById('card-expiry').value || 
                !document.getElementById('card-cvv').value) {
                reservationError.textContent = "Por favor complete los datos de la tarjeta";
                reservationError.style.display = "block";
                return;
            }
        }
        
        // Todo validado, proceder con la reserva
        reservationError.style.display = "none";
        
        // Crear objeto de reserva
        const reservation = {
            services: cart,
            date: appointmentDate.value,
            time: appointmentTime.value,
            client: {
                name: document.getElementById('client-name').value,
                email: document.getElementById('client-email').value,
                phone: document.getElementById('client-phone').value
            },
            payment: {
                method: payNow.checked ? 'online' : 'onsite',
                amount: totalElement.textContent,
                discount: payNow.checked ? '15%' : '0%'
            },
            status: 'pending',
            createdAt: new Date().toISOString()
        };
        
        // Guardar reserva (en un sistema real aquí harías una petición al backend)
        let reservations = JSON.parse(localStorage.getItem('reservations')) || [];
        reservations.push(reservation);
        localStorage.setItem('reservations', JSON.stringify(reservations));
        
        // Vaciar carrito
        localStorage.removeItem('carrito');
        cart = [];
        
        // Mostrar confirmación (en un sistema real enviarías el email aquí)
        alert(`¡Reserva confirmada!\n\nSe ha enviado un comprobante a ${reservation.client.email}`);
        window.location.href = 'perfil.html'; // Redirigir al perfil o página de confirmación
    });
    
    // Inicializar
    renderCartItems();
});