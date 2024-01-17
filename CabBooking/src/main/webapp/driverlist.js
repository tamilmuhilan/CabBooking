document.addEventListener('DOMContentLoaded', getDriverList);

function getDriverList() {
    const driverDetailsEndpoint = 'api/v1/drivers';

    fetch(driverDetailsEndpoint)
        .then(response => {
            return response.json();
        })
        .then(data => {
            console.log('Server Response:', data);

            // Check if the data is an array and not empty
            const driverList = Array.isArray(data) ? data : [];
            console.log('List of drivers:', driverList);

            const driverListContainer = document.getElementById('driverListContainer');
            driverListContainer.innerHTML = '';

            driverList.forEach(driver => {
                const driverItem = document.createElement('div');
                driverItem.innerHTML = `
                    <p>Driver Id: ${driver.driverId}</p>
                    <p>Driver Name: ${driver.driverName}</p>
                    <p>Assigned Cab Id: ${driver.assignedCabId}</p>
                    <p>Driver No: ${driver.driverNo}</p>
                    <hr>
                `;
                driverListContainer.appendChild(driverItem);
            });
        })
        .catch(error => console.error('Error fetching driver details:', error));
}

function redirectToDriverForm() {

    window.location.href = 'dashboard.html';
}
