import fetch from 'node-fetch';
import * as fs from 'fs';
import { parse } from 'csv-parse';
import { exit } from 'process';
import PDFDocument from 'pdfkit-table';

var reportOutputs = [];

const modelOneUsers = [
        {"user": "A", "type": "CGNR", "model":"one", "file": "../model1/G-1.csv", "N": 64, "S": 794, "gain": true},
        {"user": "B", "type": "CGNR", "model":"one", "file": "../model1/G-1.csv", "N": 64, "S": 794, "gain": false},
        {"user": "C", "type": "CGNR", "model":"one", "file": "../model2/G-2.csv", "N": 64, "S": 794, "gain": true},
        {"user": "D", "type": "CGNR", "model":"one", "file": "../model2/G-2.csv", "N": 64, "S": 794, "gain": false},
        {"user": "I", "type": "CGNE", "model":"one", "file": "../model1/G-1.csv", "N": 64, "S": 794, "gain": true},
        {"user": "J", "type": "CGNE", "model":"one", "file": "../model1/G-1.csv", "N": 64, "S": 794, "gain": false},
        {"user": "K", "type": "CGNE", "model":"one", "file": "../model2/G-2.csv", "N": 64, "S": 794, "gain": true},
        {"user": "L", "type": "CGNE", "model":"one", "file": "../model2/G-2.csv", "N": 64, "S": 794, "gain": false}
];
const modelTwoUsers = [
    {"user": "E", "type": "CGNR", "model":"two", "file": "../model2/g-30x30-1.csv", "N": 64, "S": 436, "gain": true},
    {"user": "F", "type": "CGNR", "model":"two", "file": "../model2/g-30x30-1.csv", "N": 64, "S": 436, "gain": false},
    {"user": "G", "type": "CGNR", "model":"two", "file": "../model2/g-30x30-2.csv", "N": 64, "S": 436, "gain": true},
    {"user": "H", "type": "CGNR", "model":"two", "file": "../model2/g-30x30-2.csv", "N": 64, "S": 436, "gain": false},
    {"user": "M", "type": "CGNE", "model":"two", "file": "../model2/g-30x30-1.csv", "N": 64, "S": 436, "gain": true},
    {"user": "N", "type": "CGNE", "model":"two", "file": "../model2/g-30x30-1.csv", "N": 64, "S": 436, "gain": false},
    {"user": "O", "type": "CGNE", "model":"two", "file": "../model2/g-30x30-2.csv", "N": 64, "S": 436, "gain": true},
    {"user": "P", "type": "CGNE", "model":"two", "file": "../model2/g-30x30-2.csv", "N": 64, "S": 436, "gain": false}
];

function getRandomModelOneUser() {
    const randomIndex = Math.floor(Math.random() * modelOneUsers.length);
    console.log(randomIndex);
    const user = modelOneUsers[4];
    return user;
}

function getRandomModelTwoUser() {
    const randomIndex = Math.floor(Math.random() * modelTwoUsers.length);
    console.log(randomIndex);
    const user = modelTwoUsers[4];
    return user;
}

function signalGain(arrayG, N, S) {
    console.log("Gain!");
    for(let i = 0; i < N; i++) {
        for(let j = 0; j < S; j++) {
            let gain = 100 + (1/20) * j * Math.sqrt(j);
            let index = j + (i*S);
            arrayG[index] = arrayG[index] * gain;
        }
    }
}

async function getImage(imageModel) {
    const url = "http://localhost:5777/process"
    let user;
    if(imageModel == "one") {
        user = getRandomModelOneUser();
    } else {
        user = getRandomModelTwoUser();
    }
    const userName = user.user;
    const type = user.type;
    const model = user.model;

    let arrayG = []

    console.log(type);
    let saveFile = true;

    fs.createReadStream(user.file)
    .pipe(parse({ delimiter: "," }))
    .on("data", function (row) {
        arrayG.push(row[0]);
    })
    .on("end", async function () {
        if(user.gain) {
            signalGain(arrayG, user.N, user.S);
        }
        await fetch(url, {
            method: 'POST',
            headers:  {'Content-Type': 'application/json'},
            json: true,
            body: JSON.stringify({userName, arrayG, type, model, saveFile})
        })
        .then(response => response.json())
        .then(jsonResponse => {
            reportOutputs.push(jsonResponse);
            //Java heap space exception (it should not occur)
            if(jsonResponse.status == 500) {
                console.log(jsonResponse);
                exit(1);
            }
        });
    })
    .on("error", function (error) {
        console.log(error.message);
        exit(1);
    });
}

async function getReport() {
    const url = "http://localhost:5777/reports"
    let output;
    await fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        },
    }).then(response => response.json())
    .then(jsonResponse => {output = jsonResponse});
    return output;
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function writeImageReportToFile(output) {
    let line;

    for (const key in output) {
        line += `${key} : ${output[key]} \n`;
    }
    line += '\n';
    console.log(line);

    fs.appendFile("./relatorioImagens.txt", line, function(err) {
        if(err) {
            return console.log(err);
        }
    });
}

async function writeReportToFile(output) {
    let line;

    for (const key in output) {
        line += `${key} : ${output[key]} \n`;
    }
    line += '\n';

    fs.appendFile("./relatorio.txt", line, function(err) {
        if(err) {
            return console.log(err);
        }
    });
}

async function getReportPeriodically() {
    while(true) {
        let output = await getReport();
        generateImagesReport();
        //generatePerformanceReport();
        await sleep(5000);
    }
}

const memoryToProcessModelOne = 950;
const memoryToProcessModelTwo = 200;

async function stress() {
    let i = 0;
    while(true) {
        let output = await getReport();
        let freeMemoryMb = output.freeMemoryMb;
        console.log("Available memory:" + output.freeMemoryMb);

        if(freeMemoryMb > memoryToProcessModelOne) {
            getImage("one");
        } else if(freeMemoryMb > memoryToProcessModelTwo) {
            getImage("two");
        }
        await sleep(300);
    }
}
getImage("one");
//getReportPeriodically();
//stress();

function generateImagesReport() {
    // init document
    let doc = new PDFDocument({ margin: 30, size: 'A4' });
    // save document
    doc.pipe(fs.createWriteStream("./report_imagens.pdf"));

    ;(async function(){
        // renderer function inside json file
        const tableJson = { 
        "headers": [
            { "label":"Nome imagem", "property":"image", "width": 150 },
            { "label":"Date", "property":"date", "width": 55 },
            { "label":"Tempo inicio", "property":"startTime", "width": 55 },
            { "label":"Tempo fim", "property":"endTime", "width": 55 },
            { "label":"Algorithm", "property":"algorithm", "width": 55 },
            { "label":"Iterations", "property":"totalIterations", "width": 55 },
            { "label":"Time to complete", "property":"timeToComplete", "width": 55 }
        ],
        "datas": reportOutputs,
        "options": {
            "width": 300
        }
        };
        // the magic
        doc.table(tableJson);
        // done!
        doc.end();
    })();
}

function generatePerformanceReport() {
    // init document
    let doc = new PDFDocument({ margin: 30, size: 'A4' });
    // save document
    doc.pipe(fs.createWriteStream("./report_performance.pdf"));

    ;(async function(){
        // renderer function inside json file
        const tableJson = { 
        "headers": [
            { "label":"Nome imagem", "property":"image", "width": 150 },
            { "label":"Date", "property":"date", "width": 55 },
            { "label":"Tempo inicio", "property":"startTime", "width": 55 },
            { "label":"Tempo fim", "property":"endTime", "width": 55 },
            { "label":"Algorithm", "property":"algorithm", "width": 55 },
            { "label":"Iterations", "property":"totalIterations", "width": 55 },
            { "label":"Time to complete", "property":"timeToComplete", "width": 55 }
        ],
        "datas": reportOutputs,
        "options": {
            "width": 300
        }
        };
        // the magic
        doc.table(tableJson);
        // done!
        doc.end();
    })();
}