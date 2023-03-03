window.chartColors = {
    red: 'rgb(255, 99, 132)',
    orange: 'rgb(255, 159, 64)',
    yellow: 'rgb(255, 205, 86)',
    green: 'rgb(75, 192, 192)',
    blue: 'rgb(54, 162, 235)',
    purple: 'rgb(153, 102, 255)',
    grey: 'rgb(201, 203, 207)'
};

colors = [window.chartColors.blue, window.chartColors.green, window.chartColors.orange, window.chartColors.purple, window.chartColors.red, window.chartColors.yellow, window.chartColors.grey]

function createPieChart(jsonDataTCS) {
    let categories = getCategories();

    let datasets = [];
    for (let i = 0; i < categories.length; i++) {
        let datas = [];
        for (const key in jsonDataTCS) {
            datas.push(jsonDataTCS[key]["categories"][i + 1]);
        }
        datasets.push({label: categories[i], backgroundColor: colors[i % colors.length], stack: 'Stack 0', data: datas})
    }

    var barChartData = {
        labels: Object.keys(jsonDataTCS),
        datasets: datasets
    };
    var tcs = document.getElementById("test-result")
    let child = document.getElementById("canvas");
    if (child) {
        tcs.removeChild(child);
    }

    var ctx = document.createElement("canvas");
    ctx.id = "canvas"
    tcs.appendChild(ctx)

    window.myBar = new Chart(ctx.getContext("2d"), {
        type: 'bar',
        data: barChartData,
        options: {
            title: {
                display: true,
                text: "Detailed TCS metrics chart for selected months"
            },
            tooltips: {
                mode: 'index',
                intersect: false
            },
            responsive: true,
            scales: {
                xAxes: [{
                    stacked: true,
                    scaleLabel: [{
                        display: true,
                        labelString: 'Month'
                    }]
                }],
                yAxes: [{
                    stacked: true,
                    scaleLabel: [{
                        display: true,
                        labelString: 'Sums, rubles'
                    }]
                }]
            }
        }
    });
    window.myBar.options
    console.log(window.myBar.options.scales)
}

function getCategories() {
    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8080/metric/tcs/categories"

    let result = [];
    xhr.onreadystatechange = function () {
        let response = JSON.parse(xhr.responseText);
        let i = 1;
        while (response["category_" + i]) {
            result.push(String(response["category_" + i]["name"]));
            i++;
        }
    }

    xhr.open("GET", url, false);
    xhr.send()

    return result;
}