cd /Users/sidvijay/projects/Wasteless
export PATH=$PATH:/Applications/apache-maven-3.6.3/bin
export PATH=$PATH:/Users/latheesh/Desktop/wasteless\ release\ JUNE\ 2020/apache-maven-3.6.3/bin
mvn package -DskipTests
docker build -t wasteless-1.0.0 .
$(aws ecr get-login --no-include-email --region us-east-2)
docker tag  wasteless-1.0.0:latest 225348022872.dkr.ecr.us-east-2.amazonaws.com/wasteless:latest
docker push 225348022872.dkr.ecr.us-east-2.amazonaws.com/wasteless:latest
