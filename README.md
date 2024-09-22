# Project is work in progress

docker stop bankx

docker rm bankx

docker run --name bankx -e POSTGRES_PASSWORD=bankx -e POSTGRES_USER=bankx -e POSTGRES_DB=bankx -d -p 5432:5432 postgres
