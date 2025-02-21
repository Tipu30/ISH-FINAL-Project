document.addEventListener('DOMContentLoaded', () => {
    const userName = localStorage.getItem('userName') || 'Guest';
    const userEmail = localStorage.getItem('userEmail') || 'Not Provided';
    const user = userName.split(' ')[0];

    document.getElementById('user-name').textContent = user;
    document.getElementById('user-full-name').textContent = userName;
    document.getElementById('user-email').textContent = userEmail;

    // Handle Sign
    const signoutButton = document.getElementById('signout-button');
    signoutButton.addEventListener('click', () => {
        
        localStorage.clear();

        // Redirect to home page after a delay
        setTimeout(() => {
            window.location.href = 'index.html';
        }, 1000); // 1-second delay
    });
});

document.addEventListener('DOMContentLoaded', () => {
});