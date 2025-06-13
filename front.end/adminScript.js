document.addEventListener('DOMContentLoaded', function() {
    // Datos de ejemplo (en un sistema real estos vendrían de una base de datos)
    let services = JSON.parse(localStorage.getItem('services')) || [
        { id: 1, name: "Masaje relajante", category: "masajes", duration: 60, price: 5000, professional: "Profesional 1", description: "Alivia la tensión y promueve la relajación profunda" },
        { id: 2, name: "Facial rejuvenecedor", category: "faciales", duration: 45, price: 4000, professional: "Profesional 2", description: "Tratamiento facial para rejuvenecer la piel" }
    ];
    
    let appointments = JSON.parse(localStorage.getItem('appointments')) || [
        { id: 1, serviceId: 1, client: "Cliente 1", date: formatDate(new Date()), time: "10:00", professional: "Profesional 1", status: "confirmed", phone: "3624123456", email: "cliente1@example.com", notes: "", payment: 5000 },
        { id: 2, serviceId: 2, client: "Cliente 2", date: formatDate(new Date()), time: "14:00", professional: "Profesional 2", status: "pending", phone: "3624654321", email: "cliente2@example.com", notes: "Alergia a algunos aceites", payment: 4000 }
    ];
    
    // Funciones de utilidad
    function formatDate(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }
    
    function formatCurrency(amount) {
        return '$' + amount.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
    }
    
    // Funciones para manejar las pestañas
    const tabs = document.querySelectorAll('.admin-tab');
    const tabContents = document.querySelectorAll('.tab-content');
    
    tabs.forEach(tab => {
        tab.addEventListener('click', function() {
            const tabId = this.getAttribute('data-tab');
            
            // Remover clase active de todas las pestañas y contenidos
            tabs.forEach(t => t.classList.remove('active'));
            tabContents.forEach(c => c.classList.remove('active'));
            
            // Agregar clase active a la pestaña y contenido seleccionados
            this.classList.add('active');
            document.getElementById(tabId).classList.add('active');
            
            // Cargar datos específicos según la pestaña
            if (tabId === 'services') {
                renderServicesList();
            } else if (tabId === 'appointments') {
                renderAppointmentsList();
                loadServicesForAppointments();
            }
        });
    });
    
    // Gestión de Servicios
    const serviceForm = document.getElementById('service-form');
    
    function renderServicesList() {
        const servicesList = document.getElementById('services-list');
        servicesList.innerHTML = '';
        
        services.forEach(service => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${service.name}</td>
                <td>${service.category}</td>
                <td>${service.duration} min</td>
                <td>${formatCurrency(service.price)}</td>
                <td>${service.professional}</td>
                <td>
                    <button class="print-btn" data-id="${service.id}"><i class="fas fa-edit"></i> Editar</button>
                    <button class="print-btn" data-id="${service.id}"><i class="fas fa-trash"></i> Eliminar</button>
                </td>
            `;
            servicesList.appendChild(row);
        });
    }
    
    serviceForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const newService = {
            id: services.length > 0 ? Math.max(...services.map(s => s.id)) + 1 : 1,
            name: document.getElementById('service-name').value,
            category: document.getElementById('service-category').value,
            duration: parseInt(document.getElementById('service-duration').value),
            price: parseFloat(document.getElementById('service-price').value),
            professional: document.getElementById('service-professional').value,
            description: document.getElementById('service-description').value
        };
        
        services.push(newService);
        localStorage.setItem('services', JSON.stringify(services));
        renderServicesList();
        serviceForm.reset();
        
        alert('Servicio creado exitosamente!');
    });
    
    // Gestión de Turnos
    function loadServicesForAppointments() {
        const serviceSelect = document.getElementById('appointment-service');
        serviceSelect.innerHTML = '<option value="">Seleccionar servicio</option>';
        
        services.forEach(service => {
            const option = document.createElement('option');
            option.value = service.id;
            option.textContent = `${service.name} (${service.professional})`;
            serviceSelect.appendChild(option);
        });
    }
    
    function renderAppointmentsList(filterDate = null) {
        const appointmentsList = document.getElementById('appointments-list');
        appointmentsList.innerHTML = '';
        
        let filteredAppointments = appointments;
        
        if (filterDate) {
            filteredAppointments = appointments.filter(app => app.date === filterDate);
        }
        
        filteredAppointments.forEach(appointment => {
            const service = services.find(s => s.id === appointment.serviceId);
            const serviceName = service ? service.name : 'Servicio no encontrado';
            
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${appointment.time}</td>
                <td>${appointment.client}</td>
                <td>${serviceName}</td>
                <td>${appointment.professional}</td>
                <td><span class="badge badge-${appointment.status}">${
                    appointment.status === 'confirmed' ? 'Confirmado' : 
                    appointment.status === 'pending' ? 'Pendiente' : 'Cancelado'
                }</span></td>
                <td>
                    <button class="print-btn" data-id="${appointment.id}"><i class="fas fa-print"></i> Imprimir</button>
                </td>
            `;
            appointmentsList.appendChild(row);
        });
    }
    
    const appointmentForm = document.getElementById('appointment-form');
    
    appointmentForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const serviceId = parseInt(document.getElementById('appointment-service').value);
        const service = services.find(s => s.id === serviceId);
        
        if (!service) {
            alert('Por favor seleccione un servicio válido');
            return;
        }
        
        const newAppointment = {
            id: appointments.length > 0 ? Math.max(...appointments.map(a => a.id)) + 1 : 1,
            serviceId: serviceId,
            client: document.getElementById('appointment-client').value,
            date: document.getElementById('appointment-date').value,
            time: document.getElementById('appointment-time').value,
            professional: service.professional,
            status: document.getElementById('appointment-status').value,
            phone: document.getElementById('appointment-phone').value,
            email: document.getElementById('appointment-email').value,
            notes: document.getElementById('appointment-notes').value,
            payment: service.price
        };
        
        appointments.push(newAppointment);
        localStorage.setItem('appointments', JSON.stringify(appointments));
        renderAppointmentsList();
        appointmentForm.reset();
        
        alert('Turno creado exitosamente!');
    });
    
    // Filtro de fecha para turnos
    const appointmentsFilterDate = document.getElementById('appointments-filter-date');
    
    appointmentsFilterDate.addEventListener('change', function() {
        renderAppointmentsList(this.value);
    });
    
    // Reportes por Servicio
    const generateServiceReportBtn = document.getElementById('generate-service-report');
    const serviceReportResults = document.getElementById('service-report-results');
    
    generateServiceReportBtn.addEventListener('click', function() {
        const startDate = document.getElementById('report-service-start').value;
        const endDate = document.getElementById('report-service-end').value;
        
        if (!startDate || !endDate) {
            alert('Por favor seleccione ambas fechas');
            return;
        }
        
        // Filtrar turnos confirmados en el rango de fechas
        const filteredAppointments = appointments.filter(app => 
            app.status === 'confirmed' && 
            app.date >= startDate && 
            app.date <= endDate
        );
        
        // Agrupar por servicio
        const reportData = {};
        
        filteredAppointments.forEach(app => {
            const service = services.find(s => s.id === app.serviceId);
            const serviceName = service ? service.name : 'Servicio no encontrado';
            
            if (!reportData[serviceName]) {
                reportData[serviceName] = {
                    count: 0,
                    total: 0
                };
            }
            
            reportData[serviceName].count++;
            reportData[serviceName].total += app.payment;
        });
        
        // Generar HTML del reporte
        let html = `
            <h4>Reporte del ${startDate} al ${endDate}</h4>
            <table class="report-table">
                <thead>
                    <tr>
                        <th>Servicio</th>
                        <th>Cantidad de Turnos</th>
                        <th>Total Recaudado</th>
                    </tr>
                </thead>
                <tbody>
        `;
        
        let grandTotal = 0;
        let grandCount = 0;
        
        for (const [serviceName, data] of Object.entries(reportData)) {
            html += `
                <tr>
                    <td>${serviceName}</td>
                    <td>${data.count}</td>
                    <td>${formatCurrency(data.total)}</td>
                </tr>
            `;
            
            grandTotal += data.total;
            grandCount += data.count;
        }
        
        html += `
                <tr class="report-total">
                    <td>TOTAL</td>
                    <td>${grandCount}</td>
                    <td>${formatCurrency(grandTotal)}</td>
                </tr>
            </tbody>
            </table>
            <div style="margin-top: 20px;">
                <button class="btn" onclick="window.print()"><i class="fas fa-print"></i> Imprimir Reporte</button>
            </div>
        `;
        
        serviceReportResults.innerHTML = html;
    });
    
    // Reportes por Profesional
    const generateProfessionalReportBtn = document.getElementById('generate-professional-report');
    const professionalReportResults = document.getElementById('professional-report-results');
    
    generateProfessionalReportBtn.addEventListener('click', function() {
        const startDate = document.getElementById('report-professional-start').value;
        const endDate = document.getElementById('report-professional-end').value;
        
        if (!startDate || !endDate) {
            alert('Por favor seleccione ambas fechas');
            return;
        }
        
        // Filtrar turnos confirmados en el rango de fechas
        const filteredAppointments = appointments.filter(app => 
            app.status === 'confirmed' && 
            app.date >= startDate && 
            app.date <= endDate
        );
        
        // Agrupar por profesional
        const reportData = {};
        
        filteredAppointments.forEach(app => {
            if (!reportData[app.professional]) {
                reportData[app.professional] = {
                    count: 0,
                    total: 0
                };
            }
            
            reportData[app.professional].count++;
            reportData[app.professional].total += app.payment;
        });
        
        // Generar HTML del reporte
        let html = `
            <h4>Reporte del ${startDate} al ${endDate}</h4>
            <table class="report-table">
                <thead>
                    <tr>
                        <th>Profesional</th>
                        <th>Cantidad de Turnos</th>
                        <th>Total Recaudado</th>
                    </tr>
                </thead>
                <tbody>
        `;
        
        let grandTotal = 0;
        let grandCount = 0;
        
        for (const [professional, data] of Object.entries(reportData)) {
            html += `
                <tr>
                    <td>${professional}</td>
                    <td>${data.count}</td>
                    <td>${formatCurrency(data.total)}</td>
                </tr>
            `;
            
            grandTotal += data.total;
            grandCount += data.count;
        }
        
        html += `
                <tr class="report-total">
                    <td>TOTAL</td>
                    <td>${grandCount}</td>
                    <td>${formatCurrency(grandTotal)}</td>
                </tr>
            </tbody>
            </table>
            <div style="margin-top: 20px;">
                <button class="btn" onclick="window.print()"><i class="fas fa-print"></i> Imprimir Reporte</button>
            </div>
        `;
        
        professionalReportResults.innerHTML = html;
    });
    
    // Inicialización
    renderServicesList();
    
    // Establecer fecha predeterminada para el filtro de turnos
    const today = formatDate(new Date());
    document.getElementById('appointments-filter-date').value = today;
    document.getElementById('appointment-date').value = today;
    
    // Establecer fechas predeterminadas para reportes (mes actual)
    const firstDay = new Date();
    firstDay.setDate(1);
    document.getElementById('report-service-start').value = formatDate(firstDay);
    document.getElementById('report-professional-start').value = formatDate(firstDay);
    
    const lastDay = new Date(firstDay);
    lastDay.setMonth(lastDay.getMonth() + 1);
    lastDay.setDate(0);
    document.getElementById('report-service-end').value = formatDate(lastDay);
    document.getElementById('report-professional-end').value = formatDate(lastDay);
});