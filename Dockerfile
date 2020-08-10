FROM java:8
WORKDIR /
VOLUME /tmp
ADD target/store-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT java -jar app.jar
