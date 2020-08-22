function post(path, params, method) {
    const form = document.createElement('form');
    form.method = method;
    form.action = path;
    for (const key in params) {
      if (params.hasOwnProperty(key)) {
        const hiddenField = document.createElement('input');
        hiddenField.type = 'hidden';
        hiddenField.name = key;
        hiddenField.value = params[key];
        form.appendChild(hiddenField);
      }
    }
    document.body.appendChild(form);
    form.submit();
}

function addHiddenInput(id, parameterValue, parameterName) {
    const form = document.getElementById(id);
    const hiddenField = document.createElement('input');
    hiddenField.type = 'hidden';
    hiddenField.name = parameterName;
    hiddenField.value = parameterValue;
    if (form[parameterName] !== undefined)
        form[parameterName].value = parameterValue;
    else
        form.appendChild(hiddenField);
}

function addMultipleHiddenInputs(id, params) {
    const form = document.getElementById(id);
    for (const key in params) {
        if (params.hasOwnProperty(key)) {
            const hiddenField = document.createElement('input');
            hiddenField.type = 'hidden';
            hiddenField.name = key;
            hiddenField.value = params[key];
            if (form[key] !== undefined)
                form[key].value = params[key];
            else
                form.appendChild(hiddenField);
        }
    }
}

function httpGet(url, elementId) {
    const xmlHttp = new XMLHttpRequest();
    let e = document.getElementById(elementId);
    let choice = e.options[e.selectedIndex].value;
    url += '?option=' + choice;
    xmlHttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("unique").innerHTML = xmlHttp.responseText;
        }
    }
    xmlHttp.open("GET", url, true);
    xmlHttp.send();
}

function httpGetJson(url, data) {
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            // document.getElementById("unique").innerHTML = xmlHttp.responseText;
            data = JSON.parse(xmlHttp.responseText);
        }
    }
    xmlHttp.open("GET", url, true);
    xmlHttp.send();
}