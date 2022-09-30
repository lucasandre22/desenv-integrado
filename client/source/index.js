const request = require('request');

ip = 'host.docker.internal';

port = 5777;

console.log(ip);

function generateRandomInteger(max) {
    return Math.floor(Math.random() * max);
}

function randomize(first, second) {
    return generateRandomInteger(2) == 1 ? first : second;
}

function randomize(array) {
    return array[generateRandomInteger(array.length)];
}

function randomizeUser() {
    return randomize(["Lucas", "Vitor", "Leila", "Guto", "Arroz"]);
}

function algorithmPararandomizeAlgorithmParameters() {
    image = "teste";
    algorithmN = 24;
    algorithmS = 24;
    arrayG = [ 0.1234, 0.312512, 0.941924 ];
    return {
        image,
        algorithmN,
        algorithmS,
        arrayG
    }
}

function buildRequest(mapping) {
    let user = randomizeUser();
    let algorithm = randomize("CGNE", "CGNR");
    let signalGain = randomize([ true, false ]);
    let { image, algorithmN, algorithmS, arrayG } = algorithmPararandomizeAlgorithmParameters();
    return {
        url: "http://" + ip + ":" + port + "/" + mapping,
        method: 'POST',
        headers: 'Content-Type: application/json',
        json: true,
        body: {
            user,
            arrayG,
            image,
            algorithm,
            algorithmN,
            algorithmS,
            signalGain
        }
    };
}

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