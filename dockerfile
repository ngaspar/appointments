FROM maven:3.6-jdk-8-alpine

COPY pom.xml /appointments_service/
COPY src /appointments_service/src/
WORKDIR /appointments_service/
RUN mvn clean package

ENTRYPOINT java -jar target/appointments-1.0.0.jar --spring.datasource.url=jdbc:postgresql://db:5432/appointmentsdb
