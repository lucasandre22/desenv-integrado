
function buildUrl(requestType) {
    return 'http://127.0.0.1:7777/' + requestType;
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function doRequest(requestType, onLoad) {
    let request = new XMLHttpRequest();
    let url = buildUrl(requestType);
    request.open('GET', url);
    request.responsetype = 'json';
    request.setRequestHeader("Access-Control-Allow-Origin", "origin-list");
    request.send();
    request.onload = function() {
        let jsonObject = JSON.parse(request.response);
        onLoad(jsonObject);
    }
}

function createTableCell(content) {
    createTableCell(content, "", null);
}

function createTableCell(content, id, classes) {
    let tableCell = document.createElement("td");
    if(id) {
        tableCell.id = id;
    }
    if(classes) {
        classes = classes.split(' ');
        for(let i = 0; i < classes.length; i++) {
            table.classList.add(classes[i]);
        }
    }
    tableCell.innerHTML = content;
    return tableCell;
}

function createTableLine() {
    let tableLine = document.createElement("tr");
    return tableLine;
}

function copyTag(id) {
    copyTag(id, '');
}

function copyTag(id, inner) {
    let node = document.getElementById(id).cloneNode(true);
    console.log(node);
    node.innerHTML = inner;
    return node;
}

function getId(id) {
    return document.getElementById(id);
}

function drawCpuChart(value) {
    var data = google.visualization.arrayToDataTable([
        ['Label', 'Value'],
        ['CPU (%)', value]
    ]);
    var options = {
        width: 355,
        height: 355,
        greenFrom: 0,
        greenTo: 80,
        redFrom: 90,
        redTo: 100,
        yellowFrom: 75,
        yellowTo: 90,
        minorTicks: 5
    };
    var chart = new google.visualization.Gauge(document.getElementById('chart-gauge-cpu'));
    chart.draw(data, options);
    /*setTimeout(function() {
        console.log("lala");
        doRequest('cpu_usage', function (jsonObject) {
            var data = google.visualization.arrayToDataTable([
                ['Label', 'Value'],
                ['CPU (%)', jsonObject.cpuPercentage],
            ]);
            chart.draw(data, options);
        });
        
    }, 1000);*/
}
