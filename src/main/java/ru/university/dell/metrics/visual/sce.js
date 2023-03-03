function createNodesTable(idTable) {
    let url = "http://localhost:8080/metric/nodes";
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.status === 200) {
            let response = JSON.parse(xhr.responseText)["nodesInfo"];
            let table = document.getElementById(idTable);
            for (const responseKey in response) {
                let tr = document.createElement("tr");
                let td = document.createElement("th");
                td.innerText = responseKey;
                tr.appendChild(td);
                for (const tdKey in response[responseKey]) {
                    let td = document.createElement("td");
                    if (response[responseKey][tdKey] !== null)
                        td.innerText = String(convertToCurrentTZO(response[responseKey][tdKey]));
                    else
                        td.innerText = "working...";
                    tr.appendChild(td);
                }
                table.appendChild(tr);
            }
        }
    }
    xhr.open("GET", url, false);
    xhr.send()
}


function convertToCurrentTZO(time)
{
    let myTZO = new Date().getTimezoneOffset();
    return new Date(new Date(time).getTime() + (60000 *
        (new Date(time).getTimezoneOffset() - myTZO)))
}

function getSCEMetric() {
    let input = document.getElementById("chosen-id")
    let arr;
    if (input.value === "") {
        alert("Enter numbers pls")
        return;
    }

    arr = input.value.split(',');

    var selection = document.getElementById("load-selector");
    var type = selection.value;

    let divSCE = document.getElementById("div-sce-result");
    let parent = divSCE.parentNode;
    parent.removeChild(divSCE);
    divSCE = document.createElement("div");
    divSCE.id = "div-sce-result";
    parent.appendChild(divSCE);

    for (let i = 0; i < arr.length; i++) {
        let xhrSCE = new XMLHttpRequest();
        let url = "http://localhost:8080/metric/sce?";
        console.log(arr[i]);
        url += "id=" + arr[i] + "&type=" + type;
        xhrSCE.onreadystatechange = function () {
            if (xhrSCE.status === 200) {
                let p = document.getElementById("result-sce-" + i);
                if (p === null) {
                    let p = document.createElement("p");
                    p.id = "result-sce-" + i;
                    p.innerText = "Result of SCE metric for " + arr[i] + " id is " + xhrSCE.responseText + " for " + type;
                    divSCE.appendChild(p);
                } else {
                    p.innerText = "Result of SCE metric for " + arr[i] + " id is " + xhrSCE.responseText + " for " + type;
                }
            }
        }

        xhrSCE.open("GET", url, false);
        xhrSCE.send();
    }
}