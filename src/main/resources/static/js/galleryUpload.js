document.addEventListener('DOMContentLoaded', function () {
    document.forms['formImageUpload'].onsubmit = function () {
        let request = new XMLHttpRequest();
        request.open("POST", "/upload");
        request.onload = function (ev) {
            if (request.status === 200) {
                changeFields(request.response);
            } else {
                alert("FAILED");
                console.log(request.response);
            }
        };
        let formData = new FormData(document.getElementsByTagName('form')[0]);
        request.send(formData);
        return false;
    };
});

function addNewTag() {
    let el = document.createElement('div');
    el.innerHTML = "<input type='text' class='tagsInput'>";
    let placeForInput = document.getElementById('placeForInput');
    placeForInput.parentNode.insertBefore(el, placeForInput);
}

function changeFields(link) {
    let el = document.createElement('div');
    el.innerHTML = "<input id='imgLink' type='text' value=" + link + " readonly>";
    document.getElementById('formImageUpload').replaceWith(el);
}

function addNewImg() {
    let request = new XMLHttpRequest();
    request.open("POST", "/addImage");
    request.onload = function (ev) {
        if (request.status === 200) {
            alert("success");
        }
    };
    request.send(getBodyJSON());
}


function getBodyJSON() {
    let elArr = document.getElementsByClassName('tagsInput');
    let arr = [];
    for (let i = 0; i < elArr.length; i++) {
        arr.push(elArr[i].value);
    }
    let link = document.getElementById('imgLink').value;
    return JSON.stringify({'tags': arr, 'link': link});
}