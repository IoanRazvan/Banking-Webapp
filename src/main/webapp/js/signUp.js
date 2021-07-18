const signUpForm = document.getElementById("signUpForm");
const submitButton = document.querySelector("button[type='submit']");
const phoneNumber = document.getElementById("phoneNumber");

submitButton.addEventListener("click", function () {
    console.log("hello");
    if (phoneNumber.validity.patternMismatch)
        phoneNumber.setCustomValidity("Not a valid romanian phone number");
    else
        phoneNumber.setCustomValidity("");
});

function addAlertDiv(message) {
    clearPreviousAlerts();
    if (message) {
        let alertDiv = createAlertDiv(message);
        signUpForm.insertBefore(alertDiv, submitButton);
    }
}

function clearPreviousAlerts() {
    while (submitButton.previousElementSibling.className !== "form-group") {
        submitButton.previousElementSibling.remove();
    }
}

function createAlertDiv(message) {
    let alertDiv = document.createElement("div");
    alertDiv.className = "alert alert-danger alert-dismissible fade show";
    alertDiv.setAttribute("role", "alert");
    alertDiv.appendChild(document.createTextNode(message));
    let alertButton = createAlertButton();
    alertButton.appendChild(createAlertSpan());
    alertDiv.appendChild(alertButton);
    return alertDiv;
}

function createAlertButton() {
    let alertButton = document.createElement("button");
    alertButton.className = "close";
    alertButton.setAttribute("data-dismiss", "alert");
    alertButton.setAttribute("aria-label", "Close");
    return alertButton;
}

function createAlertSpan() {
    let alertSpan = document.createElement("span");
    alertSpan.setAttribute("aria-hidden", "true");
    alertSpan.innerHTML = "&times;";
    return alertSpan;
}