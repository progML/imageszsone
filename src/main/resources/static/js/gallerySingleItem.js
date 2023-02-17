var cropper;
var topLeft;
var bottomRight;

function findByTag(tag) {
    window.location = '/?tag=' + tag;
}

function markZone() {
    cropper = new Cropper(document.getElementById('IMAGE'), {
        viewMode: 1
    });
    document.getElementById('turnOnSelection').style.display = "none";
    document.getElementById('confirmOnSelection').style.display = "block";
}

function confirmMarkZone() {
    //592 360
    let naturalHeight = cropper.canvasData.naturalHeight;
    let naturalWidth = cropper.canvasData.naturalWidth;

    //553 336
    let clientWidth = cropper.canvas.clientWidth;
    let clientHeight = cropper.canvas.clientHeight;

    let scaleX = naturalWidth / clientWidth;
    let scaleY = naturalHeight / clientHeight;

    let canvasLeftOffset = cropper.canvasData.left;  //img zone left offset
    let canvasTopOffset = cropper.canvasData.top;    //img zone top offset

    let cropBoxTop = cropper.cropBoxData.top; // zone from browser
    let cropBoxLeft = cropper.cropBoxData.left; //zone from browser


    topLeft = {
        x: cropBoxLeft - canvasLeftOffset < 0 ? 0 : cropBoxLeft - canvasLeftOffset,
        y: cropBoxTop - canvasTopOffset
    };

    bottomRight = {
        x: topLeft.x + cropper.cropBoxData.width,
        y: topLeft.y + cropper.cropBoxData.height
    };

    console.log(topLeft);
    console.log(bottomRight);
    console.log("---");

    topLeft.x = topLeft.x * scaleX;
    topLeft.y = topLeft.y * scaleY;
    bottomRight.x = bottomRight.x * scaleX;
    bottomRight.y = bottomRight.y * scaleY;

    console.log(topLeft);
    console.log(bottomRight);
    console.log("---");

    showTags();
}

function showTags() {
    document.getElementById('tagsInputDiv').style.display = "block";
}

function addNewTag() {
    let el = document.createElement('div');
    el.innerHTML = "<input type='text' class='tagsInput'>";
    let placeForInput = document.getElementById('placeForInput');
    placeForInput.parentNode.insertBefore(el, placeForInput);
}

function getBodyJSON() {
    let elArr = document.getElementsByClassName('tagsInput');
    let arr = [];
    for (let i = 0; i < elArr.length; i++) {
        arr.push(elArr[i].value);
    }
    return JSON.stringify({
        'tags': arr,
        'imageId': document.getElementById('imageId').innerText,
        'coords': {
            'leftTopX': topLeft.x,
            'leftTopY': topLeft.y,
            'rightBottomX': bottomRight.x,
            'rightBottomY': bottomRight.y
        }
    });
}

function sendRequest() {
    let request = new XMLHttpRequest();
    request.open("POST", "/markedZone/add");
    request.onload = function (ev) {
        if (request.status === 200) {
            alert("success");
            window.location.reload();
        }
    };
    request.send(getBodyJSON())
}


let idImageZone;
document.addEventListener('DOMContentLoaded', function () {
    let list = document.getElementsByClassName('zones');
    for (let i = 0; i < list.length; i++) {
        list[i].onmouseover = function () {
            let divs = list[i].getElementsByTagName('div');
            let divsL = divs.length;

            idImageZone = document.createElement('div');
            idImageZone.classList.add('selectionZone');
            idImageZone.style.position = "absolute";
            idImageZone.style.width = divs[divsL - 2].innerHTML + "px";
            idImageZone.style.height = divs[divsL - 1].innerHTML + "px";

            let num1 = list[i].getElementsByClassName('offset')[0].innerHTML;
            let num2 = parseInt(list[i].getElementsByClassName('offset')[1].innerHTML) + 38;
            idImageZone.style.left = num1 + "px";
            idImageZone.style.top = num2 + "px";

            let image = document.getElementById('IMAGE');
            image.parentNode.insertBefore(idImageZone, image);
        };

        list[i].onmouseout = function () {
            try {
                idImageZone.remove();
            } catch (e) {
                console.log(e);
            }
        }
    }
});