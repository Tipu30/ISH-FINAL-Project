document.addEventListener("DOMContentLoaded", function () {
    var alertMessage = document.getElementById("alertMessage");
    
    // If it exists, make it disappear after 3 seconds (3000ms)
    if (alertMessage) {
        setTimeout(function() {
            alertMessage.style.transition = "opacity 0.5s ease";
            alertMessage.style.opacity = "0";
            
            // Remove the element from DOM after fade-out
            setTimeout(function() {
                alertMessage.style.display = "none";
            }, 300);
        }, 800);
    }
    
    const planSelect = document.getElementById("plan");
    const kidsDetails = document.getElementById("kidsDetails");
    const kidsContainer = document.getElementById("kidsContainer");

    // Show kids details section if CCAP is selected
    planSelect.addEventListener("change", function () {
        if (planSelect.value === "CCAP") {
            kidsDetails.style.display = "block";
            addKidForm(); // Automatically add the kid form
        } else {
            kidsDetails.style.display = "none";
            kidsContainer.innerHTML = ""; // Clear existing children inputs
        }
    });

    // Function to add kid entry automatically
	function addKidForm() {
	    if (kidsContainer.children.length === 0) {
	        const kidDiv = document.createElement("div");
	        kidDiv.classList.add("kid-entry");
	        kidDiv.innerHTML = `
	            <h5>Child Details</h5>
	            <div class="mb-2">
	                <label>Child's Age:</label>
	                <input type="number" class="form-control" name="kidAge" required>
	            </div>
	            <div class="mb-2">
	                <label>Child's SSN:</label>
	                <input type="text" class="form-control" name="kidSSN" required>
	            </div>
	            <button type="button" class="btn btn-danger remove-kid">Remove</button>
	        `;
	        kidsContainer.appendChild(kidDiv);

	        // Remove button functionality
	        kidDiv.querySelector(".remove-kid").addEventListener("click", function () {
	            kidsDetails.style.display = "none";
	            kidsContainer.innerHTML = ""; // Remove all inputs
	        });
	    }
	}

});
