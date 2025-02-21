/*document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('signin-form').addEventListener('submit', function (event) {
        event.preventDefault();

        // Retrieve user input
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value.trim();

       
        const storedEmail = "tipukhan2315@gmail.com";
        const storedPassword = 'password123'; 
        const storedName = 'Tipu'; 

        if (email === storedEmail && password === storedPassword) {
            // Store user data in localStorage
            localStorage.setItem('userName', storedName);
            localStorage.setItem('userEmail', email);

            // Redirect to home page after a delay
            setTimeout(() => {
                window.location.href = 'index.html';
            }, 1000); // 1-second delay
        }
    });
});
*/