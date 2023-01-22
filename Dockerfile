FROM openjdk:latest
COPY  ./target/DislinktAuthService-0.0.1-SNAPSHOT.jar auth_service.jar
CMD ["java","-jar","/auth_service.jar"]