FROM openjdk:11
EXPOSE 8080
ADD /build/libs/ega-0.0.1.jar ega.jar
ENTRYPOINT ["java", "-jar", "ega.jar"]