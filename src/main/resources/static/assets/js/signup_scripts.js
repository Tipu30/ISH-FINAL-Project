/*document.addEventListener('DOMContentLoaded', () => {
    // Attach the event listener to the signup form
    const signupForm = document.getElementById('signup-form');
    if (signupForm) {
        signupForm.addEventListener('submit', function (event) {
            event.preventDefault();

            // Retrieve user input
            const name = document.getElementById('name').value.trim();
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value;

            if (name && email && password) {
                // Store user data in localStorage
                localStorage.setItem('userName', name);
                localStorage.setItem('userEmail', email);


                // Redirect to the home page after a delay
                setTimeout(() => {
                    window.location.href = 'index.html';
                }, 2000); // 2-second delay
            }
        });
    }
});
*/