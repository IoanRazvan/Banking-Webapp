const romanianPhoneNumberRegexp = /^07\d{8}$/;

const signUpForm = document.getElementById("signUpForm");
const submitButton = document.querySelector("button[type='submit']");
const phoneNumberField = document.querySelector("input[name='phoneNumber']");

signUpForm.addEventListener("submit", event => {
    clearPreviousAlerts();
    let phoneNumber = phoneNumberField.value;
    if (!romanianPhoneNumberRegexp.test(phoneNumber)) {
        addAlertDiv("Invalid phone number");
        event.preventDefault();
    }
});

function clearPreviousAlerts() {
    while (submitButton.previousElementSibling.className !== "form-group") {
        submitButton.previousElementSibling.remove();
    }
}

function addAlertDiv(message) {
    if (message) {
        let alertDiv = createAlertDiv(message);
        signUpForm.insertBefore(alertDiv, submitButton);
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