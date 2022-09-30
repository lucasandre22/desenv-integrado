## How to build
$ docker build -t client-image .  
$ docker run -d --add-host=host.docker.internal:host-gateway client-image  
--add-host=host.docker.internal:host-gateway: map host ip to host.docker.internal  
-d: detached from console   

## Logs
$ docker container ls  
$ docker logs <container id>

## Enter the container
$ docker exec -it <container id> /bin/bash  

## Kill container
$ docker kill <container id>  

https://nodejs.org/en/docs/guides/nodejs-docker-webapp/