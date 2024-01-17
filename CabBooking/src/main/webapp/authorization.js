// Assume you have a function to get the value of a cookie
function getCookie(name) {
  const cookies = document.cookie.split(';');
  for (let i = 0; i < cookies.length; i++) {
    const cookie = cookies[i].trim();
    if (cookie.startsWith(name + '=')) {
      return cookie.substring(name.length + 1);
    }
  }
  return null;
}

document.addEventListener('DOMContentLoaded', function () {
   const role = getCookie('role');
     console.log('role:',role);

     // Function to redirect based on role
     function redirectToPage() {
       if (role === 'customer') {
         window.location.href = '/booking.html';
       } else if (role === 'admin') {
         window.location.href = '/dashboard.html';
       }
     }

     // Function to check if the user has access to a specific page
     function hasAccessToPage(page) {
       if (role !== null && role === 'customer' && (page === '/booking.html' || page === '/customerbooklist.html')) {
         return true;
       } else if (role === 'admin') {
         return true;
       }
       return false;
     }

     const requestURI = window.location.pathname;
     const redirectFlag = sessionStorage.getItem('redirectFlag');

     if (requestURI === '/index.html' || requestURI === '/' || requestURI === '/unauthorized.html') {
       redirectToPage();

     } else if (hasAccessToPage(requestURI)) {
     } else {
       window.location.href = '/unauthorized.html';
     }
});