document.addEventListener('DOMContentLoaded', getCabList);

function getCabList() {
    const cabDetailsEndpoint = 'api/v1/cabs';

    fetch(cabDetailsEndpoint)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Server Response:', data);

            // Check if the data is an array and not empty
            const cabList = Array.isArray(data) ? data : [];
            console.log('List of cabs:', cabList);

            const cabListContainer = document.getElementById('cabListContainer');
            cabListContainer.innerHTML = '';

            cabList.forEach(cab => {
                const cabItem = document.createElement('div');
                cabItem.innerHTML = `
                    <p>Cab Id: ${cab.id}</p>
                    <p>Cab No: ${cab.cabNo}</p>
                    <p>Colour: ${cab.colour}</p>
                    <p>Seating Capacity: ${cab.seatingCapacity}</p>
                    <p>Available Seat: ${cab.availableSeat}</p>
                    <p>AvailableforBooking: ${cab.availableForBooking ? 'true' : 'false'}</p>
                    <hr>
                `;
                cabListContainer.appendChild(cabItem);
            });
        })
        .catch(error => console.error('Error fetching cab details:', error));
}

function redirectToCabForm() {

    window.location.href = 'dashboard.html';
}
