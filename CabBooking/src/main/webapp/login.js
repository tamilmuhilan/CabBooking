const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

document.getElementById('signUp').addEventListener('click', function () {
    document.getElementById('container').classList.add('right-panel-active');
});

document.getElementById('signIn').addEventListener('click', function () {
    document.getElementById('container').classList.remove('right-panel-active');
});

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



function addUser() {

       var regMobile = document.getElementById('regMobile').value;
           var regMobilePattern = /^[0-9]{10}$/;

           if (!regMobilePattern.test(regMobile)) {
               alert('Please enter a valid 10-digit mobile number.');
               return false;
           }

           var regPassword = document.getElementById('password').value;
           var regPasswordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,16}$/;

           if (!regPasswordPattern.test(regPassword)) {
               alert('Please enter a valid password with at least one lowercase letter, one uppercase letter, and one digit (8-16 characters).');
               return false;
           }


    const form = document.getElementById('addUserForm');
    const formData = new FormData(form);
    const jsonData = {};

    formData.forEach((value, key) => {
        jsonData[key] = value;
    });

    fetch('api/v1/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body:JSON.stringify(jsonData),
    })
    .then(response => {
        console.log('Response Status:', response.status);

      /*  if (!response.ok) {
            signUp.innerHTML = `${data.message}`
            throw new Error('Server responded with an error');
        }*/
        return response.json();
    })
    .then(data => {
         if(data.status==='Failed'){
         signUpButton.innerHTML = `${data.message}`
         window.alert('The username or mobile number already exists!');
         }
         else{
         signUpButton.innerHTML = `${data.message}`
        document.getElementById('container').classList.remove('right-panel-active');
        }
        form.reset();
    })
    .catch((error) => {
        console.error('Fetch Error:', error);
        signUpButton.innerHTML = `${data.message}`
        // Handle other errors, e.g., show an error message
    });

    // Prevent the default form submission
    return false;
}

function loginUser() {
    var loginMobile = document.getElementById('loginMobile').value;
    var loginPassword = document.getElementById('loginPassword').value;

        var regMobilePattern = /^[0-9]{10}$/;

               if (!regMobilePattern.test(loginMobile)) {
                   alert('Please enter a valid 10-digit mobile number.');
                   return false;
               }

                var regPasswordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,16}$/;

                          if (!regPasswordPattern.test(loginPassword)) {
                              alert('Please enter a valid password with at least one lowercase letter, one uppercase letter, and one digit (8-16 characters).');
                              return false;
                          }

    // Add validation logic if needed
    const formData = {
            Mobileno: loginMobile,
            Password: loginPassword,
        };

    fetch('api/v1/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body:JSON.stringify(formData),
    })
    .then(response => {
        console.log('Response Status:', response.status);
        return response.json();
    })
    .then(data => {
          const role = getCookie('role');
               console.log('role:',role);

        if (data.status === 'Failed') {
            signInButton.innerHTML = `${data.message}`;
            window.alert(data.message);
           // window.location.href = '/';
        } else {
            window.alert(data.message);
           redirectToRolePage();
        }
    })
    .catch((error) => {
        console.error('Fetch Error:', error);
        signInButton.innerHTML = 'Login Error';
        // Handle other errors, e.g., show an error message
    });

             const role = getCookie('role');
                   console.log('role1:',role);
    // Prevent the default form submission
    return false;
}

function redirectToRolePage() {
    // Read the role from the cookie after the fetch request is complete
    const role = getCookie('role');
    console.log('role:', role);

    if (role === 'customer') {
        window.location.href = '/booking.html';
    } else if (role === 'admin') {
        window.location.href = '/dashboard.html';
    }
}


function showInfo(infoId) {
        var infoElement = document.getElementById(infoId);
        infoElement.style.display = "block";
    }

    function hideInfo(infoId) {
        var infoElement = document.getElementById(infoId);
        infoElement.style.display = "none";
    }