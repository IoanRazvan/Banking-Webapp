const signUpForm = document.getElementById("signUpForm");
const submitButton = document.querySelector("button[type='submit']");
const phoneNumber = document.getElementById("phoneNumber");

submitButton.addEventListener("click", function () {
    if (phoneNumber.validity.patternMismatch)
        phoneNumber.setCustomValidity("Not a valid romanian phone number");
    else
        phoneNumber.setCustomValidity("");
});

let addAlertDiv = addAlertDivUtil(signUpForm, submitButton);