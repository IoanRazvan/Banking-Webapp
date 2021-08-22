const signUpForm = document.getElementById("signUpForm");
const submitButton = document.querySelector("button[type='submit']");
const phoneNumber = document.getElementById("phoneNumber");
const addAlertDiv = addAlertDivUtil(signUpForm, submitButton);

phoneNumber.addEventListener("input", function () {
    if (!phoneNumber.validity.patternMismatch)
        phoneNumber.setCustomValidity("");
})

submitButton.addEventListener("click", function () {
    if (phoneNumber.validity.patternMismatch)
        phoneNumber.setCustomValidity("Not a valid romanian phone number");
    else
        phoneNumber.setCustomValidity("");
});

signUpForm.addEventListener("submit", function (event) {
    event.preventDefault();
    let requestContent = {
        method: 'POST',
        body: new FormData(signUpForm)
    }

    fetch("http://localhost:8080/BankingApplication/signUp", requestContent)
        .then(response => {
            console.log('error');
            if (!response.ok)
                response.json().then(data => addAlertDiv(data.message));
            else
                window.location.href = response.url;
        });
});