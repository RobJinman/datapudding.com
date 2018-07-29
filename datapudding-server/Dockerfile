FROM java:8-alpine
MAINTAINER Rob Jinman <jinmanr@gmail.com>

ADD target/datapudding-server-0.0.1-SNAPSHOT-standalone.jar /datapudding-server/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/datapudding-server/app.jar"]
