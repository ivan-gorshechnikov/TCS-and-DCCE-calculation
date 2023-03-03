function getDCCEMetric() {
    let input = document.getElementById("chosen-id-dcce")
    let arr;
    if (input.value === "") {
        alert("Enter numbers pls")
        return;
    }

    arr = input.value.split(',');

    var selection = document.getElementById("load-selector");
    var type = selection.value;

    let divDCCE = document.getElementById("div-dcce-result");
    let parent = divDCCE.parentNode;
    parent.removeChild(divDCCE);
    divDCCE = document.createElement("div");
    divDCCE.id = "div-dcce-result";
    parent.appendChild(divDCCE);

    let url = "http://localhost:8080/metric/dcce?";
    for (let i = 0; i < arr.length; i++) {
        if (!isNaN(parseFloat(arr[i])) && isFinite(arr[i]))
            url += "ids=" + arr[i] + "&";
        else {
            alert("Enter only numbers pls")
            return;
        }
    }
    url += "type=" + type;
    console.log(url);
    let xhrDCCE = new XMLHttpRequest();
    xhrDCCE.onreadystatechange = function () {
        if (xhrDCCE.status === 200) {
            let p = document.getElementById("result-dcce");
            if (p === null) {
                let p = document.createElement("p");
                p.id = "result-dcce";
                p.innerText = "Result of DCCE metric for " + arr + " ids is " + xhrDCCE.responseText + " for " + type;
                divDCCE.appendChild(p);
            } else {
                p.innerText = "Result of DCCE metric for " + arr + " ids is " + xhrDCCE.responseText + " for " + type;
            }
        }
    }
    xhrDCCE.open("GET", url, false);
    xhrDCCE.send();
}