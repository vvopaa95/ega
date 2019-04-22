FROM openjdk:latest
LABEL author=vvopaa
EXPOSE 8080
ADD build/libs/ega-1.jar ega-1.jar
ENTRYPOINT ["java", "-jar", "ega-1.jar"]
