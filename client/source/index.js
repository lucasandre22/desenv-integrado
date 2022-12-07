import fetch from 'node-fetch';
import * as fs from 'fs';
import { parse } from 'csv-parse';
import { exit } from 'process';


const users = [
        {"user": "A", "algorithm": "CGNR", "model":"one", "file": "../model1/G-1.csv", "N": 64, "S": 794, "gain": true},
        {"user": "B", "algorithm": "CGNR", "model":"one", "file": "../model1/G-1.csv", "N": 64, "S": 794, "gain": false},
        {"user": "C", "algorithm": "CGNR", "model":"one", "file": "../model2/G-2.csv", "N": 64, "S": 794, "gain": true},
        {"user": "D", "algorithm": "CGNR", "model":"one", "file": "../model2/G-2.csv", "N": 64, "S": 794, "gain": false},
        {"user": "I", "algorithm": "CGNE", "model":"one", "file": "../model1/G-1.csv", "N": 64, "S": 794, "gain": true},
        {"user": "J", "algorithm": "CGNE", "model":"one", "file": "../model1/G-1.csv", "N": 64, "S": 794, "gain": false},
        {"user": "K", "algorithm": "CGNE", "model":"one", "file": "../model2/G-2.csv", "N": 64, "S": 794, "gain": true},
        {"user": "L", "algorithm": "CGNE", "model":"one", "file": "../model2/G-2.csv", "N": 64, "S": 794, "gain": false},
        {"user": "E", "algorithm": "CGNR", "model":"two", "file": "../model2/g-30x30-1.csv", "N": 64, "S": 436, "gain": true},
        {"user": "F", "algorithm": "CGNR", "model":"two", "file": "../model2/g-30x30-1.csv", "N": 64, "S": 436, "gain": false},
        {"user": "G", "algorithm": "CGNR", "model":"two", "file": "../model2/g-30x30-2.csv", "N": 64, "S": 436, "gain": true},
        {"user": "H", "algorithm": "CGNR", "model":"two", "file": "../model2/g-30x30-2.csv", "N": 64, "S": 436, "gain": false},
        {"user": "M", "algorithm": "CGNE", "model":"two", "file": "../model2/g-30x30-1.csv", "N": 64, "S": 436, "gain": true},
        {"user": "N", "algorithm": "CGNE", "model":"two", "file": "../model2/g-30x30-1.csv", "N": 64, "S": 436, "gain": false},
        {"user": "O", "algorithm": "CGNE", "model":"two", "file": "../model2/g-30x30-2.csv", "N": 64, "S": 436, "gain": true},
        {"user": "P", "algorithm": "CGNE", "model":"two", "file": "../model2/g-30x30-2.csv", "N": 64, "S": 436, "gain": false}
]

function getRandomUser() {
    const randomIndex = Math.floor(Math.random() * users.length);
    const user = users[randomIndex];
    return user;
}

function getRandomModelOneUser() {
    const randomIndex = Math.floor(Math.random() * 8);
    const user = users[randomIndex];
    return user;
}

function getRandomModelTwoUser() {
    const randomIndex = Math.floor((Math.random() * 8) + 8);
    const user = users[randomIndex];
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
    } else if(imageModel == "two") {
        user = getRandomModelTwoUser();
    } else {
        user = getRandomUser();
    }
    const userName = user.user;
    const algorithm = user.algorithm;
    const model = user.model;

    let arrayG = []

    console.log(user.model);
    let saveFile = false;

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
            body: JSON.stringify({userName, arrayG, algorithm, model, saveFile})
        })
        .then(response => response.json())
        .then(jsonResponse => {
            console.log("response");
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
    }).then(response => response.text())
    .then(jsonResponse => {output = jsonResponse});
    return output;
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

const memoryToProcessModelOne = 900;
const memoryToProcessModelTwo = 180;

async function stress() {
    let i = 0;
    while(true) {
        let freeMemoryMb = await getReport();
        freeMemoryMb = Number(freeMemoryMb.split(':')[1].split(',')[0]);
        console.log("Available memory:" + freeMemoryMb);

        if(freeMemoryMb > memoryToProcessModelOne) {
            getImage("one");
        } else if(freeMemoryMb > memoryToProcessModelTwo) {
            getImage("two");
        }
        await sleep(250);
    }
}

stress();