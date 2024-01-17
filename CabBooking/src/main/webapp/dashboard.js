// dashboard.js

function showTab(tabName) {
    // Hide all tabs
    const tabs = document.querySelectorAll('.tab-content');
    tabs.forEach(tab => {
        tab.style.display = 'none';
    });

    // Show the selected tab
    const selectedTab = document.getElementById(tabName);
    if (selectedTab) {
        selectedTab.style.display = 'block';
    }
}

document.addEventListener('DOMContentLoaded', function () {
    // Populate the cab list in the dropdown on page load
    populateCabListDropdown();
});

function populateCabListDropdown() {
        const cabDropdown = document.getElementById('driverCabNo');

        // Fetch cab list from the server using a GET request
        fetch('/api/v1/cabs/available', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                // Add any other headers if needed
            },
            // You can add additional options as needed
        })
        .then(response => {
            return response.json();
        })
        .then(data => {
        console.log(data);
            // Clear existing options
            cabDropdown.innerHTML = '';

            // Populate dropdown options
            data.forEach(cab => {
                const option = document.createElement('option');
                option.value = cab.id;
                option.text = `Cab No: ${cab.cabNo}`;
                cabDropdown.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching cab list:', error);
        });
}


function displayCabInfo(selectedCabId, cabList) {
    // Find the selected cab in the cab list
    const selectedCab = cabList.find(cab => cab.id == selectedCabId);

    // Display the cab information
    const cabInfoContainer = document.getElementById('cabInfoContainer');
    cabInfoContainer.innerHTML = '';

    if (selectedCab) {
       validateKeyPress();
    } else {
        cabInfoContainer.innerHTML = 'Invalid Cab ID';
    }
}
