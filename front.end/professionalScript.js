document.addEventListener('DOMContentLoaded', function() {
    // Datos de ejemplo (en un sistema real estos vendrían de una base de datos)
    const professionalName = "Profesional 1"; // Esto debería venir del login
    
    let services = JSON.parse(localStorage.getItem('services')) || [
        { id: 1, name: "Masaje relajante", category: "masajes", duration: 60, price: 5000, professional: "Profesional 1", description: "Alivia la tensión y promueve la relajación profunda" },
        { id: 2, name: "Facial rejuvenecedor", category: "faciales", duration: 45, price: 4000, professional: "Profesional 2", description: "Tratamiento facial para rejuvenecer la piel" }
    ];
    
    let appointments = JSON.parse(localStorage.getItem('appointments')) || [
        { id: 1, serviceId: 1, client: "Cliente 1", date: formatDate(new Date()), time: "10:00", professional: "Profesional 1", status: "confirmed", phone: "3624123456", email: "cliente1@example.com", notes: "", payment: 5000 },
        { id: 2, serviceId: 2, client: "Cliente 2", date: formatDate(new Date()), time: "14:00", professional: "Profesional 2", status: "pending", phone: "3624654321", email: "cliente2@example.com", notes: "Alergia a algunos aceites", payment: 4000 },
        { id: 3, serviceId: 1, client: "Cliente 3", date: formatDate(getTomorrowDate()), time: "11:00", professional: "Profesional 1", status: "confirmed", phone: "3624789456", email: "cliente3@example.com", notes: "", payment: 5000 }
    ];
    
    // Funciones de utilidad
    function formatDate(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }
    
    function getTomorrowDate() {
        const tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        return tomorrow;
    }
    
    function formatTime(time) {
        const [hours, minutes] = time.split(':');
        const hour = parseInt(hours);
        return hour > 12 ? `${hour - 12}:${minutes} PM` : `${hour}:${minutes} AM`;
    }
    
    function getDayName(date) {
        const days = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
        return days[date.getDay()];
    }
    
    // Mostrar nombre del profesional en el header
    document.querySelector('.header-content p').textContent = `Bienvenido/a ${professionalName}`;
    
    // Variables para el control de fechas
    let currentDate = new Date();
    const tomorrowDate = getTomorrowDate();
    
    // Mostrar fechas
    document.getElementById('tomorrow-date').textContent = 
        `${getDayName(tomorrowDate)}, ${formatDate(tomorrowDate)}`;
    
    updateCurrentDateDisplay();
    
    // Funciones para renderizar turnos
    function renderTomorrowAppointments() {
        const tomorrow = formatDate(tomorrowDate);
        const tomorrowApps = appointments.filter(app => 
            app.date === tomorrow && 
            app.professional === professionalName
        );
        
        const container = document.getElementById('tomorrow-appointments-list');
        const noAppsMsg = document.getElementById('no-tomorrow-appointments');
        
        container.innerHTML = '';
        
        if (tomorrowApps.length === 0) {
            noAppsMsg.style.display = 'block';
            return;
        }
        
        noAppsMsg.style.display = 'none';
        
        tomorrowApps.forEach(appointment => {
            const service = services.find(s => s.id === appointment.serviceId);
            
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${formatTime(appointment.time)}</td>
                <td>${appointment.client}</td>
                <td>${service ? service.name : 'Servicio no encontrado'}</td>
                <td>${service ? service.duration + ' min' : '--'}</td>
                <td><span class="badge badge-${appointment.status}">${
                    appointment.status === 'confirmed' ? 'Confirmado' : 
                    appointment.status === 'pending' ? 'Pendiente' : 'Cancelado'
                }</span></td>
            `;
            container.appendChild(row);
        });
    }
    
    function renderDailyAppointments(date) {
        const dateStr = formatDate(date);
        const dailyApps = appointments.filter(app => 
            app.date === dateStr && 
            app.professional === professionalName
        );
        
        const container = document.getElementById('daily-appointments-list');
        const noAppsMsg = document.getElementById('no-daily-appointments');
        
        container.innerHTML = '';
        
        if (dailyApps.length === 0) {
            noAppsMsg.style.display = 'block';
            return;
        }
        
        noAppsMsg.style.display = 'none';
        
        dailyApps.forEach(appointment => {
            const service = services.find(s => s.id === appointment.serviceId);
            
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${formatTime(appointment.time)}</td>
                <td>${appointment.client}</td>
                <td>${service ? service.name : 'Servicio no encontrado'}</td>
                <td>${service ? service.duration + ' min' : '--'}</td>
                <td><span class="badge badge-${appointment.status}">${
                    appointment.status === 'confirmed' ? 'Confirmado' : 
                    appointment.status === 'pending' ? 'Pendiente' : 'Cancelado'
                }</span></td>
                <td>
                    <button class="print-btn" data-id="${appointment.id}">
                        <i class="fas fa-print"></i> Imprimir
                    </button>
                </td>
            `;
            container.appendChild(row);
        });
    }
    
    function updateCurrentDateDisplay() {
        document.getElementById('current-date').textContent = 
            `${getDayName(currentDate)}, ${formatDate(currentDate)}`;
        
        renderDailyAppointments(currentDate);
    }
    
    // Navegación por fechas
    document.getElementById('prev-day').addEventListener('click', function() {
        currentDate.setDate(currentDate.getDate() - 1);
        updateCurrentDateDisplay();
    });
    
    document.getElementById('next-day').addEventListener('click', function() {
        currentDate.setDate(currentDate.getDate() + 1);
        updateCurrentDateDisplay();
    });
    
    // Función para imprimir turnos
    function printAppointments(appointmentsToPrint, title) {
        let printWindow = window.open('', '_blank');
        
        let html = `
            <!DOCTYPE html>
            <html>
            <head>
                <title>Turnos ${title}</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    h1 { color: #333; text-align: center; }
                    h2 { color: #555; text-align: center; }
                    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
                    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                    th { background-color: #5a6f80; color: white; }
                    .header { display: flex; justify-content: space-between; margin-bottom: 20px; }
                    .logo { font-weight: bold; font-size: 18px; }
                    .date { text-align: right; }
                    .footer { margin-top: 30px; text-align: center; font-size: 12px; color: #666; }
                </style>
            </head>
            <body>
                <div class="header">
                    <div class="logo">SPA "Sentirse Bien"</div>
                    <div class="date">${new Date().toLocaleDateString()}</div>
                </div>
                <h1>Turnos ${title}</h1>
                <h2>Profesional: ${professionalName}</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Hora</th>
                            <th>Cliente</th>
                            <th>Servicio</th>
                            <th>Duración</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
        `;
        
        appointmentsToPrint.forEach(appointment => {
            const service = services.find(s => s.id === appointment.serviceId);
            
            html += `
                <tr>
                    <td>${formatTime(appointment.time)}</td>
                    <td>${appointment.client}</td>
                    <td>${service ? service.name : 'Servicio no encontrado'}</td>
                    <td>${service ? service.duration + ' min' : '--'}</td>
                    <td>${
                        appointment.status === 'confirmed' ? 'Confirmado' : 
                        appointment.status === 'pending' ? 'Pendiente' : 'Cancelado'
                    }</td>
                </tr>
            `;
        });
        
        html += `
                    </tbody>
                </table>
                <div class="footer">
                    <p>SPA "Sentirse Bien" - C. French 414, H3506 Resistencia, Chaco</p>
                </div>
            </body>
            </html>
        `;
        
        printWindow.document.write(html);
        printWindow.document.close();
        printWindow.print();
    }
    
    // Event listeners para botones de impresión
    document.getElementById('print-tomorrow-appointments').addEventListener('click', function() {
        const tomorrow = formatDate(tomorrowDate);
        const tomorrowApps = appointments.filter(app => 
            app.date === tomorrow && 
            app.professional === professionalName
        ); 
        
        if (tomorrowApps.length === 0) {
            alert('No hay turnos para imprimir');
            return;
        }
        
        printAppointments(tomorrowApps, `del ${getDayName(tomorrowDate)} ${formatDate(tomorrowDate)}`);
    });
    
    document.getElementById('print-daily-appointments').addEventListener('click', function() {
        const dateStr = formatDate(currentDate);
        const dailyApps = appointments.filter(app => 
            app.date === dateStr && 
            app.professional === professionalName
        );
        
        if (dailyApps.length === 0) {
            alert('No hay turnos para imprimir');
            return;
        }
        
        printAppointments(dailyApps, `del ${getDayName(currentDate)} ${formatDate(currentDate)}`);
    });
    
    // Inicialización
    renderTomorrowAppointments();
    renderDailyAppointments(currentDate);
});