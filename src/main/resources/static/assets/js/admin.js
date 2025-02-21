// Wait for the page to load
    document.addEventListener("DOMContentLoaded", function() {
        // Get the alert message element
        var alertMessage = document.getElementById("alertMessage");
        
        // If it exists, make it disappear after 3 seconds (3000ms)
        if (alertMessage) {
            setTimeout(function() {
                alertMessage.style.transition = "opacity 0.2s ease";
                alertMessage.style.opacity = "0";
                
                // Remove the element from DOM after fade-out
                setTimeout(function() {
                    alertMessage.style.display = "none";
                }, 300);
            }, 1000);
        }
    });