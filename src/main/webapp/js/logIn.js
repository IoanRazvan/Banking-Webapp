const logInForm = document.getElementById("logInForm");
const submitButton = document.querySelector("button[type='submit']")
const addAlertDiv = addAlertDivUtil(logInForm, submitButton);

logInForm.addEventListener("submit", function (event) {
    event.preventDefault();
    let requestContent = {
        method: "POST",
        "Content-Type": "multipart/form-data",
        body: new FormData(logInForm)
    };
    fetch("http://localhost:8080/BankingApplication/logIn", requestContent)
        .then(response => {
            if (!response.ok)
                response.json().then(data => addAlertDiv(data.message));
            else
                window.location.href = response.url;
        })
        .catch(err => console.log(err));
});