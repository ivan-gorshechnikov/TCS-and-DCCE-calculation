function genMetricsChoice() {
    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8080/metric/";
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let jsonData = JSON.parse(xhr.responseText);
            let where = document.getElementById("metric-nav");
            for (let i = 0; i < jsonData["metrics"].length; i++) {
                let li = document.createElement("li");
                li.className = "metric-entry-container";
                let button = document.createElement("button");
                button.className = "custom-btn btn-13";
                button.value = jsonData["metrics"][i];
                button.innerText = jsonData["metrics"][i];
                button.type = "button";
                button.onclick = function () {
                    getMetric(jsonData["metrics"][i], jsonData["metrics"])
                };
                li.append(button)
                where.append(li)
            }
        }
    };
    xhr.onerror = function () {
        alert("Server does not work. Please start it")
    }

// Выполняем HTTP-вызов, используя переменную url
    xhr.open("GET", url, false);
    try {
        xhr.send();
    } catch (DOMException) {
        alert("Server does not work. Please start it")
    }

}

function getMetric(type, allTypes) {
    for (const typeKey in allTypes) {
        let div = document.getElementById(allTypes[typeKey]);
        if (allTypes[typeKey] === type) {
            if (!div.classList.contains("show")) {
                div.classList.remove("inner")
                div.classList.add("show");
            }
        } else {
            if (!div.classList.contains("inner")) {
                div.classList.remove("show")
                div.classList.add("inner");
            }
        }
    }
}

function createMock() {

    let xhrMock = new XMLHttpRequest();
    let url = "http://localhost:8080/metric/tcs/mock";
    xhrMock.onreadystatechange = function () {
        if (xhrMock.readyState === 4 && xhrMock.status === 200) {
            let xhrCategories = new XMLHttpRequest();
            let url = "http://localhost:8080/metric/tcs/categories";

            xhrCategories.onreadystatechange = function () {
                if (xhrCategories.readyState === 4 && xhrCategories.status === 200) {
                    createMockTable(xhrMock, xhrCategories);
                }
            }
            xhrCategories.open("GET", url, false);
            xhrCategories.send();
        }
    }

    xhrMock.open("GET", url, false);
    xhrMock.send();

}


function createMockTable(xhrMock, xhrCategories) {
    let where = document.getElementById("mock-data-table");
    let jsonDataMock = JSON.parse(xhrMock.responseText);
    let jsonDataCategories = JSON.parse(xhrCategories.responseText);
    let trFirst = document.createElement("tr");
    trFirst.id = "first-line"
    let tdFirstEmpty = document.createElement("td");
    tdFirstEmpty.innerText = "month";
    tdFirstEmpty.rowSpan = 3
    trFirst.appendChild(tdFirstEmpty);
    where.appendChild(trFirst);

    let trSecond = document.createElement("tr");
    trSecond.id = "second-line"
    where.appendChild(trSecond);

    let trThird = document.createElement("tr");
    let FirstLastValue = 0, FirstCounter = 0, SecondLastValue = 0, SecondCounter = 0;

    let tdFirst, tdSecond;
    for (let i = 1; i < jsonDataMock["categories"].length; i++) {

        let categoryNum = Number(jsonDataMock["categories"][i])
        if (FirstLastValue === Math.floor(categoryNum / 100)) {
            FirstCounter += 1;
        } else {
            if (FirstCounter !== 0) {
                tdFirst.colSpan = FirstCounter
                tdFirst = createTd(jsonDataCategories, Math.floor(categoryNum / 100));
                trFirst.appendChild(tdFirst);
                FirstLastValue = Math.floor(categoryNum / 100);
                FirstCounter = 1
            } else {
                tdFirst = createTd(jsonDataCategories, Math.floor(categoryNum / 100));
                trFirst.appendChild(tdFirst);
                FirstLastValue = Math.floor(categoryNum / 100);
                FirstCounter = 1
            }
        }

        if (SecondLastValue === Math.floor(categoryNum / 10)) {
            SecondCounter += 1;
        } else {
            if (SecondCounter !== 0) {
                tdSecond.colSpan = SecondCounter
                tdSecond = createTd(jsonDataCategories, Math.floor(categoryNum / 10));
                trSecond.appendChild(tdSecond);
                SecondLastValue = Math.floor(categoryNum / 10);
                SecondCounter = 1
            } else {
                tdSecond = createTd(jsonDataCategories, Math.floor(categoryNum / 10));
                trSecond.appendChild(tdSecond);
                SecondLastValue = Math.floor(categoryNum / 10);
                SecondCounter = 1
            }
        }

        let tdThird = document.createElement("td");
        tdThird.innerText = getCategoryName(jsonDataCategories, categoryNum);
        trThird.appendChild(tdThird);
    }
    tdFirst.colSpan = FirstLastValue
    where.appendChild(trThird);

    for (const jsonDataMockKey in jsonDataMock) {
        let tr = document.createElement("tr");
        for (let i = 0; i < jsonDataMock[jsonDataMockKey].length; i++) {
            if (jsonDataMockKey !== "categories") {
                let td = document.createElement("td");
                td.innerText = jsonDataMock[jsonDataMockKey][i];
                tr.appendChild(td);
            }
        }

        where.appendChild(tr);
    }
}


function getTCSMetric() {
    let input = document.getElementById("chosen-month")
    let arr;
    if (input.value === "") {
        alert("Enter numbers pls")
        return;
    }

    try {
        arr = JSON.parse("[" + input.value + "]");
    } catch (SyntaxError) {
        alert("Enter numbers separated by commas pls")
        return;
    }

    let xhrTCS = new XMLHttpRequest();
    let url = "http://localhost:8080/metric/tcs?";
    for (let i = 0; i < arr.length; i++) {
        url += "ids=" + arr[i];
        if (i !== arr.length - 1) {
            url += "&"
        }
    }
    xhrTCS.onreadystatechange = function () {
        if (xhrTCS.status === 200) {
            let jsonDataTCS = JSON.parse(xhrTCS.responseText);
            let p = document.getElementById("overall-result");
            if (p === null) {
                let divTCS = document.getElementById("test-result")
                let p = document.createElement("p");
                p.id = "overall-result";
                countOverallResult(p, jsonDataTCS, arr)
                divTCS.appendChild(p);
            } else {
                countOverallResult(p, jsonDataTCS, arr)
            }
            createPieChart(jsonDataTCS)
        }
    }

    xhrTCS.open("GET", url, false);
    xhrTCS.send();

}


function getCategoryName(categoryNames, idCategory) {
    if (Math.floor(idCategory / 100) === 0) {
        if (Math.floor(idCategory / 10) === 0) {
            return categoryNames["category_" + idCategory]["name"]
        }
        return categoryNames["category_" + Math.floor(idCategory / 10)]["category_" + idCategory]["name"]
    }
    return categoryNames["category_" + Math.floor(idCategory / 100)]["category_" + Math.floor(idCategory / 10)]["category_" + idCategory]["name"]
}

function countOverallResult(p, jsonDataTCS, arr) {
    let sum = 0
    for (let i = 0; i < arr.length; i++) {
        sum += Number(jsonDataTCS[arr[i]][["result"]]);
    }
    if (arr.length === 1)
        p.innerText = "Result of TCS metric for " + arr + " month is " + String(sum) + " rubles"
    else
        p.innerText = "Result of TCS metric for " + arr + " months is " + String(sum) + " rubles"
}

function createTd(jsonDataCategories, categoryId) {
    let td = document.createElement("th");
    td.innerText = getCategoryName(jsonDataCategories, categoryId);
    return td;
}