// Revenue Chart
document.addEventListener('DOMContentLoaded', function() {
    // Initialize Revenue Chart
    const ctx = document.getElementById('revenueChart').getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            datasets: [{
                label: 'Revenue 2024 (Million VND)',
                data: [150, 180, 220, 200, 250, 280, 300, 320, 270, 240, 290, 350],
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 2,
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Monthly Revenue 2025'
                }
            }
        }
    });

        const calendarEl = document.getElementById('calendar');
        const calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            headerToolbar: {
                left: 'prev,next',
                center: 'title',
                right: 'today'
            },
            events: [
                {
                    title: 'New Product Launch',
                    start: '2024-03-15',
                    color: '#4e73df'
                },
                {
                    title: 'Staff Meeting', 
                    start: '2024-03-20',
                    color: '#1cc88a'
                },
                {
                    title: 'Sale Event',
                    start: '2024-03-25',
                    end: '2024-03-27',
                    color: '#f6c23e'
                },
                {
                    title: 'Inventory Check',
                    start: '2024-03-10',
                    color: '#e74a3b'
                }
            ]
        });
        calendar.render();
    });