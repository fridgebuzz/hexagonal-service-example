FROM azul/zulu-openjdk-alpine:11-jre

# Create directories for configs and the microservice itself
RUN mkdir -p /opt/takehome/config && mkdir -p /opt/takehome/services/parking-service

# Add the service itself
#COPY ./src/main/resources/log4j2-container.xml /opt/tw-data/config/logs/log4j2-container.xml
COPY ./src/main/resources/application.yml /opt/takehome/config/parking-service.yml
COPY ./src/main/resources/rates.json /opt/takehome/services/parking-service/rates.json
COPY ./target/parking-service-*.jar /opt/takehome/services/parking-service/parking-service.jar

# Run the microservice
ENTRYPOINT [ "java" ]
CMD ["-Dparking.rate.location=/opt/takehome/services/parking-service/rates.json", "-jar", "/opt/takehome/services/parking-service/parking-service.jar", "--spring.config.location=file:/opt/takehome/config/parking-service.yml"]


