FROM openjdk:11-jdk
VOLUME /tmp
EXPOSE 8082
COPY target/user-api-0.0.1-SNAPSHOT.jar user-api.jar
CMD [ "sh", "-c", "java -jar /user-api.jar" ]
