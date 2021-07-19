function addAlertDivUtil(formElement, refElement) {
    return (message) => {
        let clearPreviousAlerts = clearPreviousAlertsUtil(refElement);
        clearPreviousAlerts();
        if (message) {
            let alertDiv = createAlertDiv(message);
            formElement.insertBefore(alertDiv, refElement);
        }
    }
}

function clearPreviousAlertsUtil(refElement) {
    return () => {
        while (refElement.previousElementSibling.className !== "form-group")
            refElement.previousElementSibling.remove();
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