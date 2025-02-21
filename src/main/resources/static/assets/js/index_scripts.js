document.addEventListener('DOMContentLoaded', () => {
    // Check for user login status and update UI
    const userName = localStorage.getItem('userName');

    function updateUserUI() {
        if (userName) {
            // Show user dropdown and hide SignUp/SignIn
            const userDropdown = document.getElementById('user-dropdown');
            const signupButton = document.querySelector('#auth-section .nav-link[href="signup.html"]');
            const signinButton = document.querySelector('#auth-section .nav-link[href="signin.html"]');
            const userNameElement = document.getElementById('user-name');

            userNameElement.textContent = userName.split(' ')[0]; // First name only
            userDropdown.classList.remove('d-none');
            if (signupButton) signupButton.parentElement.style.display = 'none';
            if (signinButton) signinButton.parentElement.style.display = 'none';
        }
    }


    // Handle Sign Out
    function handleSignOut() {
        const signoutButton = document.getElementById('signout-button');
        if (signoutButton) {
            signoutButton.addEventListener('click', () => {
                localStorage.clear();
                
                // Redirect to home page after 2 seconds
                setTimeout(() => {
                    window.location.href = 'index.html';
                }, 1000);
            });
        }
    }


    // Initialize Features
    updateUserUI();
    handleSignOut();
});
