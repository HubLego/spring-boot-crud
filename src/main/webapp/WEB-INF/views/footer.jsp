<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
</div> <!-- Закрытие container -->

<!-- Футер -->
<footer class="bg-light text-center text-muted py-3 mt-5">
    <div class="container">
        <p class="mb-0">
            <small>&copy; 2025 Система управления пользователями. Все права защищены.</small>
        </p>
    </div>
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- Дополнительные скрипты -->
<script>
    // Автоматическое скрытие алертов через 5 секунд
    document.addEventListener('DOMContentLoaded', function() {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(function(alert) {
            setTimeout(function() {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            }, 5000);
        });
    });
</script>
</body>
</html>