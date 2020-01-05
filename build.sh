mvn package -DskipTests
docker build -t wasteless-1.0.0 .
$(aws ecr get-login --no-include-email --region us-east-2)
docker tag  wasteless-1.0.0:latest 225348022872.dkr.ecr.us-east-2.amazonaws.com/wasteless:latest
docker push 225348022872.dkr.ecr.us-east-2.amazonaws.com/wasteless:latest

