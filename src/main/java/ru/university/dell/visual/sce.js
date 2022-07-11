function createNodesTable() {
    let url = "http://localhost:8080/metric/nodes";
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        let response = JSON.parse(xhr.responseText);
        let table = document.getElementById("sce-data-table");
        for (const responseKey in response) {
            let tr = document.createElement("tr");
            let td = document.createElement("th");
            td.innerText = responseKey;
            tr.appendChild(td);
            for (const tdKey in response[responseKey]) {
                let td = document.createElement("td");
                let myTZO = new Date().getTimezoneOffset();
                if (response[responseKey][tdKey] !== null)
                    td.innerText = String(new Date(new Date(response[responseKey][tdKey]).getTime() + (60000 *
                        (new Date(response[responseKey][tdKey]).getTimezoneOffset() - myTZO))));
                else
                    td.innerText = "broke";
                tr.appendChild(td);
            }
            table.appendChild(tr);
        }
    }
    xhr.open("GET", url, false);
    xhr.send()
}

function getSCEMetric() {
    let input = document.getElementById("chosen-id")
    let arr;
    if (input.value === "") {
        alert("Enter numbers pls")
        return;
    }

    arr = input.value.split(',');
    // try {
    //     arr = Array.from(input.value);
    // } catch (SyntaxError) {
    //     alert("Enter numbers separated by commas pls")
    //     return;
    // }

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
        url += "id="+arr[i] + "&type=" + type;
        xhrSCE.onreadystatechange = function () {
            let p = document.getElementById("result-sce-" + i);
            if (p === null) {
                let p = document.createElement("p");
                p.id = "result-sce-" + i;
                p.innerText = "Result of SCE metric for " + arr[i] + " ids is " + xhrSCE.responseText + " for " + type;
                divSCE.appendChild(p);
            } else {
                p.innerText = "Result of SCE metric for " + arr[i] + " ids is " + xhrSCE.responseText + " for " + type;
            }
        }

        xhrSCE.open("GET", url, false);
        xhrSCE.send();
    }
}