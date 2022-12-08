var table = document.getElementById("processes-table");

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

async function doPostRequest(requestType, body, onLoad) {
    let request = new XMLHttpRequest();
    let url = buildUrl(requestType);
    request.open('POST', url);
    request.responsetype = 'json';
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(body));
    request.onload = function() {
        let jsonObject = JSON.parse(request.response);
        onLoad(jsonObject);
    }
}

function createExpandButton(pid) {
    let tableCell = document.createElement("td");
    let buttonTemplate = document.getElementById("button-template");
    let button = buttonTemplate.cloneNode(true);

    button.href = "#collapse-" + pid
    button.innerHTML = "Expand";
    button.onclick = function() { expandButtonEvent("collapse-" + pid) };
    tableCell.appendChild(button);

    return tableCell;
}

function removePid(pid) {
    console.log(pid);
    doPostRequest("pid_kill", { "pid": pid }, function(jsonObject) {
        console.log(jsonObject);
        alert("Killed process " + pid + ", output: " + jsonObject);
    });
}

function createTableLine(pid, pidObject) {
    let tableLine = document.createElement("tr");
    tableLine.appendChild(createTableCell(pid));
    tableLine.appendChild(createTableCell(pidObject.Command));
    tableLine.appendChild(createTableCell(pidObject.User));
    tableLine.appendChild(createTableCell(pidObject.Time));
    tableLine.appendChild(createTableCell(pidObject.Cpu + "%"));
    tableLine.appendChild(createTableCell(pidObject.Mem + "%"));
    tableLine.appendChild(createExpandButton(pid));
    let tableCell = createTableCell("");
    let button = document.getElementById("button-trash-template").cloneNode(true);
    button.id = "button-" + pid;
    button.onclick = function() {
        if(confirm('Are you sure that you want to kill process ' + pid + "?")) {
            removePid(pid);
        }
    };
    tableCell.appendChild(button)
    tableLine.appendChild(tableCell);

    return tableLine;
}

function createTableCollapseLine(pid) {
    let tableLine = document.getElementById("tr-template").cloneNode(true);
    tableLine.id = "collapse-" + pid;
    let children = tableLine.children;
    for(let i = 0; i < children.length; i++) {
        children[i].id = children[i].id + pid;
    }
    return tableLine;
}

function createPtag(content) {
    let p = document.createAttribute("td");
    p.innerHTML = content;
    return p;
}

function copyCmdLine(element) {
    console.log(element.children[1].innerHTML);
    navigator.clipboard.writeText(element.children[1].innerHTML);
}

async function expandButtonEvent(pid) {
    pid = pid.split("-")[1];
    let collapsedDiv = document.getElementById("collapse-" + pid);
    if(!collapsedDiv.classList.contains("collapse")) {
        console.log("removing");
        collapsedDiv.classList.add("collapse");
        return;
    }
    collapsedDiv.classList.remove("collapse");
    await doPostRequest("pid", { "pid": pid }, function(jsonObject) {
        collapsedDiv.children[0].children[1].innerHTML = jsonObject.cmdLine;
        collapsedDiv.children[1].children[1].innerHTML = jsonObject.execFilename;
        collapsedDiv.children[2].children[1].innerHTML = jsonObject.nice;
        collapsedDiv.children[3].children[1].innerHTML = jsonObject.state;
        collapsedDiv.children[4].children[1].innerHTML = jsonObject.parentPid;
        collapsedDiv.children[5].children[1].innerHTML = jsonObject.priority;
        collapsedDiv.children[6].children[1].innerHTML = jsonObject.threads;
        collapsedDiv.children[7].children[1].innerHTML = jsonObject.threads_pids.toString();
    });
}

function populateTable(jsonObject) {
    const pids = Object.entries(jsonObject.pids);
    table.innerHTML = "";
    for(let i = pids.length - 1; i >= 0; i--) {
        let a = pids[i];
        table.appendChild(createTableLine(a[0], a[1]));
        table.appendChild(createTableCollapseLine(a[0]));
    }
}

function onLoad() {
    doRequest("top", populateTable);
}

onLoad();