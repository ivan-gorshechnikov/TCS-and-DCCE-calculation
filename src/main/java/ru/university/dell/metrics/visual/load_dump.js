window.chartColors = {
    red: 'rgb(255, 99, 132)',
    orange: 'rgb(255, 159, 64)',
    yellow: 'rgb(255, 205, 86)',
    green: 'rgb(75, 192, 192)',
    blue: 'rgb(54, 162, 235)',
    purple: 'rgb(153, 102, 255)',
    grey: 'rgb(201, 203, 207)'
};
var timeFormat = 'MM/DD/YYYY HH:mm';
var configCPU = {
    type: 'line',
    data: {
        datasets: [{
            label: "CPU",
            backgroundColor: window.chartColors.blue,
            borderColor: window.chartColors.blue,
            fill: false,
        }]
    },
    options: {
        scales: {
            xAxes: [{
                type: "time",
                time: {
                    format: timeFormat,
                    // round: 'day'
                    tooltipFormat: 'll HH:mm'
                },
                scaleLabel: {
                    display: true,
                    labelString: 'Date'
                }
            },],
            yAxes: [{
                scaleLabel: {
                    display: true,
                    labelString: 'value'
                }
            }]
        },
    }
};

var configRAM = {
    type: 'line',
    data: {
        datasets: [{
            label: "RAM",
            backgroundColor: window.chartColors.red,
            borderColor: window.chartColors.red,
            fill: false,
        }]
    },
    options: {
        scales: {
            xAxes: [{
                type: "time",
                time: {
                    format: timeFormat,
                    // round: 'day'
                    tooltipFormat: 'll HH:mm'
                },
                scaleLabel: {
                    display: true,
                    labelString: 'Date'
                }
            },],
            yAxes: [{
                scaleLabel: {
                    display: true,
                    labelString: 'value'
                }
            }]
        },
    }
};


let interval;

function startMonitoring() {
    let url = "http://localhost:8081/monitoring/start?interval=1"
    let xhrStart = new XMLHttpRequest()
    configCPU.data.datasets[0].data = []
    configRAM.data.datasets[0].data = []
    configCPU.data.labels = []
    configRAM.data.labels = []
    xhrStart.onreadystatechange = function () {
        if (xhrStart.status === 200) {
            interval = setInterval(getLoad, 1000);
        }
    }
    xhrStart.open("GET", url, false);
    try {
        xhrStart.send();
    } catch (DOMException) {
        alert("Monitoring server does not working")
    }

}

function endMonitoring() {
    let url = "http://localhost:8081/monitoring/end"
    let xhrStart = new XMLHttpRequest();

    xhrStart.onreadystatechange = function () {

    }
    xhrStart.open("GET", url, false);
    try {
        xhrStart.send();
    } catch (DOMException) {
        alert("Monitoring server does not working")
    }
    clearInterval(interval);
}


window.onload = function () {
    var ctxCPU = document.getElementById("cpu-canvas").getContext("2d");
    window.CPULine = new Chart(ctxCPU, configCPU);
    var ctxRAM = document.getElementById("ram-canvas").getContext("2d");
    window.RAMLine = new Chart(ctxRAM, configRAM);
}

let i = 0;

function getLoad() {

    let url = "http://localhost:8081/monitoring/get"
    let xhrStart = new XMLHttpRequest()

    xhrStart.onreadystatechange = function () {
        if (xhrStart.status === 200) {
            let response = JSON.parse(xhrStart.responseText)
            addData(new Date(response["Time"]), response["CPU"], response["RAM"])
        }
    }

    xhrStart.open("GET", url, false);
    xhrStart.send();

}

function convertToCurrentTZO_(time) {
    let myTZO = new Date().getTimezoneOffset();
    return new Date(new Date(time).getTime() + (60000 *
        (new Date(time).getTimezoneOffset() - myTZO)))
}

function addData(time, cpu, ram) {

    let maxElem = 100;

    if (configRAM.data.labels.length > maxElem - 1) {
        for (let j = 1; j < maxElem; j++) {
            configCPU.data.labels[j - 1] = configCPU.data.labels[j]
            configRAM.data.labels[j - 1] = configRAM.data.labels[j]
            configRAM.data.datasets[0].data[j - 1] = configRAM.data.datasets[0].data[j]
            configCPU.data.datasets[0].data[j - 1] = configCPU.data.datasets[0].data[j]
        }
        configCPU.data.labels[maxElem - 1] = convertToCurrentTZO_(time);
        configRAM.data.labels[maxElem - 1] = convertToCurrentTZO_(time);
        configCPU.data.datasets[0].data[maxElem - 1] = cpu
        configRAM.data.datasets[0].data[maxElem - 1] = ram
    } else {
        configCPU.data.labels.push(convertToCurrentTZO_(time));
        configRAM.data.labels.push(convertToCurrentTZO_(time));
        configCPU.data.datasets[0].data.push(cpu);
        configRAM.data.datasets[0].data.push(ram);
    }
    i++;
    window.CPULine.update();
    window.RAMLine.update();

}
