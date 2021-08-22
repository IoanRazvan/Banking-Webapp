const formElement = document.getElementById("openAccount");

formElement.addEventListener("submit", function(event) {
    event.preventDefault();
    let requestContent = {
        method: "POST",
        "Content-Type": "multipart/form-data",
        body: new FormData(formElement),
    };

    fetch("http://localhost:8080/BankingApplication/openAccount", requestContent)
        .then(response => {
            if (!response.redirected)
                response.json().then(data => {
                    $("#messageModal").find(".modal-title").text(data.message);
                    $("#messageModal").modal('show');
                })
            else
                window.location.href = response.url;
        }).
        catch(err => console.log(err));
});