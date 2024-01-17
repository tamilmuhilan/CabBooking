document.addEventListener('DOMContentLoaded', getCabBookings);

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


function getCabBookings() {
    const id = getCookie('Login_Id');
         console.log('User id:',id);
   const cabBookingsEndpoint = `api/v1/bookings/customer/${id}`;

    fetch(cabBookingsEndpoint)
        .then(response => {
            return response.json();
        })
        .then(data => {
            console.log('Server Response:', data);

            // Check if the data is an array and not empty
            const bookingsList = Array.isArray(data) ? data : [];
            console.log('List of cab bookings:', bookingsList);

            const bookingsListContainer = document.getElementById('bookingsListContainer');
            bookingsListContainer.innerHTML = '';

            bookingsList.forEach(booking => {
                const bookingItem = document.createElement('div');
                bookingItem.innerHTML = `
                    <p>Booking Id: ${booking.Bookid}</p>
                    <p>Pickup Point: ${booking['Pickup Point']}</p>
                    <p>Drop Point: ${booking['Drop Point']}</p>
                    <p>Cab No: ${booking['Cab No']}</p>
                    <p>Driver Name: ${booking['Driver Name']}</p>
                    <p>Driver No: ${booking['Driver No']}</p>
                    <p>Booking Time: ${booking['Booking Time']}</p>
                    <hr>
                `;
                bookingsListContainer.appendChild(bookingItem);
            });
        })
        .catch(error => console.error('Error fetching cab bookings:', error));
}

function redirectToBookingForm() {
    window.location.href = 'booking.html';
}
