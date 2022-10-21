const request = require('request');
const fs = require('fs');

ip = 'host.docker.internal';

port = 5777;

console.log(ip);

const imageDataMap = new Map([
    [1, { "image": "g-30x30-1.csv", "model": "one", "S": 1, "N": 1}],
    [2, { "image": "g-30x30-2.csv", "model": "one", "S": 1, "N": 1 }],
    [3, { "image": "G-1.csv", "model": "two", "S": 1, "N": 1 }]
]);

//TODO read array from csv
function csvToArray(str, delimiter = ",") {
    const headers = str.slice(0, str.indexOf("\n")).split(delimiter);
    const rows = str.slice(str.indexOf("\n") + 1).split("\n");
    const arr = rows.map(function (row) {
      const values = row.split(delimiter);
      const el = headers.reduce(function (object, header, index) {
        object[header] = values[index];
        return object;
      }, {});
      return el;
    });
    return arr;
}

async function lala(data) {
    fs.readFile("../../model" + model == "one" ? "1" : "2" + image, 'utf8', (err, data) => {
        if (err) {
          console.error(err);
          return;
        }
        
    });
    let CSVToArray = (data, delimiter = ',', omitFirstRow = false) =>
        data
        .slice(omitFirstRow ? data.indexOf('\n') + 1 : 0)
        .split('\n')
        .map(v => v.split(delimiter));
}

function runSignalGain(array, S, N) {
    for(let c = 0; c < N; c++) {
        for(let l = 0; l < S; l++) {
            let gain = 100 + (1/20) * l * Math.sqrt(l);
            array[c+(l*S)] = array[c+(l*S)] * gain;
        }
    }
}

//generates from 0 to max
function generateRandomInteger(max) {
    return Math.floor(Math.random() * max);
}

function randomizeDuo(first, second) {
    console.log("first!");
    return generateRandomInteger(2) == 1 ? first : second;
}

function randomize(array) {
    return array[generateRandomInteger(array.length)];
}

function randomizeUser() {
    return randomize(["Lucas", "Vitor", "Leila", "Guto", "Arroz", "Peter", "Park", "Urubu", "Bitcoin", "Abigail", "Leo",
                    "Giulia", "Flamingo", "Post", "Regia", "Oister"]);
}

function randomizeImage(signalGain) {
    let imageData = imageDataMap.get(generateRandomInteger(3) + 1);
    if(signalGain) {
        runSignalGain(imageData.array, imageData.S, imageData.N);
    }
    let arrayG = imageData.image;
    let model = imageData.model;
    return { arrayG, model };
}

function buildRequest(mapping) {
    let user = randomizeUser();
    let algorithm = randomizeDuo("CGNE", "CGNR");
    let signalGain = randomize([ true, false ]);
    let { arrayG, model } = randomizeImage(signalGain);
    return {
        url: "http://" + ip + ":" + port + "/" + mapping,
        method: 'POST',
        headers: 'Content-Type: application/json',
        json: true,
        body: {
            user,
            arrayG,
            algorithm,
            model
        }
    };
}

//TODO
function randomizeTimeout() {
    return 1000;
}

async function doRequest() {
    let current_request = buildRequest("jblas");
    console.log(current_request);
    request(current_request, function (error, response, body) {
        if(error) {
            return -1;
        } else if(response) {
            console.log("response status: " + response.statusCode);
        }
        return response.statusCode;
    });
}

async function waitServerRun() {
    while(true) {
        await doRequest();
        await new Promise(resolve => setTimeout(resolve, randomizeTimeout()));
    }
}

waitServerRun();