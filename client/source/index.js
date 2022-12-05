import fetch from 'node-fetch';
import * as fs from 'fs';
import { parse } from 'csv-parse';


const users = [
        {"user": "A", "algorithm": "CGNR", "model":"one", "file": "../model1/G-1.csv", "N": 64, "S": 794, "gain": true},
        {"user": "B", "algorithm": "CGNR", "model":"one", "file": "../model1/G-1.csv", "N": 64, "S": 794, "gain": false},
        {"user": "C", "algorithm": "CGNR", "model":"one", "file": "../model2/G-2.csv", "N": 64, "S": 794, "gain": true},
        {"user": "D", "algorithm": "CGNR", "model":"one", "file": "../model2/G-2.csv", "N": 64, "S": 794, "gain": false},
        {"user": "E", "algorithm": "CGNR", "model":"two", "file": "../model2/g-30x30-1.csv", "N": 64, "S": 436, "gain": true},
        {"user": "F", "algorithm": "CGNR", "model":"two", "file": "../model2/g-30x30-1.csv", "N": 64, "S": 436, "gain": false},
        {"user": "G", "algorithm": "CGNR", "model":"two", "file": "../model2/g-30x30-2.csv", "N": 64, "S": 436, "gain": true},
        {"user": "H", "algorithm": "CGNR", "model":"two", "file": "../model2/g-30x30-2.csv", "N": 64, "S": 436, "gain": false},
        {"user": "I", "algorithm": "CGNE", "model":"one", "file": "../model1/G-1.csv", "N": 64, "S": 794, "gain": true},
        {"user": "J", "algorithm": "CGNE", "model":"one", "file": "../model1/G-1.csv", "N": 64, "S": 794, "gain": false},
        {"user": "K", "algorithm": "CGNE", "model":"one", "file": "../model2/G-2.csv", "N": 64, "S": 794, "gain": true},
        {"user": "L", "algorithm": "CGNE", "model":"one", "file": "../model2/G-2.csv", "N": 64, "S": 794, "gain": false},
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

function signalGain(arrayG, N, S) {
    for(let i = 0; i < N; i++) {
        for(let j = 0; j < S; j++) {
            let gain = 100 + (1/20) * j * Math.sqrt(j);
            let index = i + (j*S);
            arrayG[index] = arrayG[index] * gain;
        }
    }
}

async function getImage() {
    const url = "http://localhost:5777/process"
    const user = getRandomUser();
    const userName = user.user;
    const algorithm = user.algorithm;
    const model = user.model;

    let arrayG = []

    console.log(user.model);
    fs.createReadStream(user.file)
    .pipe(parse({ delimiter: "," }))
    .on("data", function (row) {
        arrayG.push(row[0]);
    })
    .on("end", async function () {
        if(user.gain){
            //signalGain(arrayG, user.N, user.S); TODO error
        }
        await fetch(url, {
            method: 'POST',
            headers:  {'Content-Type': 'application/json'},
            json: true,
            body: JSON.stringify({userName, arrayG, algorithm, model})
        })
        .then(response => response.json())
        .then(jsonResponse => {
          console.log(jsonResponse);
        });
    })
    .on("error", function (error) {
        console.log(error.message);
    });
}

async function getReport(){
    const url = "http://localhost:5777/reports"
    await fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        },
    }).then(response => response.text())
    .then(text => console.log(text))
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function exec() {
    getReport();
    getImage();
    getImage();
    await sleep(1000);
    getReport();
}

exec();