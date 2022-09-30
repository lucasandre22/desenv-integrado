FROM node:16

WORKDIR /usr/src/client

#Copy node project packages declarations
COPY package*.json ./

#Install app dependencies
RUN npm install

#Copy source to imag
COPY source .

#Command to run the app
CMD [ "node", "index.js" ]

