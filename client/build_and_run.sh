docker rmi $(docker images -q)
docker build -t client-image . 
./run