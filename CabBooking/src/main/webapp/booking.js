document.addEventListener('DOMContentLoaded', function () {
    // Populate the location options in the dropdowns on page load
    populateLocationDropdown('pickPoint');
    populateLocationDropdown('dropPoint');
});

function populateLocationDropdown(selectId) {
    const locationDropdown = document.getElementById(selectId);

    // Fetch location data from the server
    fetch('/api/v1/locations', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            // Add any other headers if needed
        },
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        locationDropdown.innerHTML = '';

        // Populate dropdown options
        data.forEach(location => {
            const option = document.createElement('option');
            option.value = location.id; // Store the location id as the value
            option.text = location.locationName; // Display the location name
            locationDropdown.appendChild(option);
        });
    })
    .catch(error => {
        console.error('Error fetching location data:', error);
    });
}






function bookRide() {
         const pickPoint = document.getElementById('pickPoint').value;
         const dropPoint = document.getElementById('dropPoint').value;

         // Check if pickPoint and dropPoint are not the same
         if (pickPoint === dropPoint) {
             bookingStatus.innerHTML = 'Pickup and drop points cannot be the same.';
             return false; // Prevent the form from submitting
         }


    const form = document.getElementById('bookingForm');
    const formData = new FormData(form);
    const jsonData = {};

    formData.forEach((value, key) => {
        jsonData[key] = value;
    });

    fetch('api/v1/bookings', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body:JSON.stringify(jsonData),
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        bookingStatus.innerHTML = `${data.message}`;
        form.reset(); // Reset the form after successful booking
        const newTab = window.open('customerbooklist.html', '_blank');
                    if (newTab) {
                        newTab.focus();
                    }
    })
    .catch(error => {
        console.error('Error booking ride:', error);
        const bookingStatus = document.getElementById('bookingStatus');
    });
}
