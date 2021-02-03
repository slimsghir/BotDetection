FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
COPY ./target/BotDetection-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/app/
COPY access.log.txt /opt/app/
WORKDIR /opt/app
ENTRYPOINT ["java", "-jar", "BotDetection-1.0-SNAPSHOT-jar-with-dependencies.jar","access.log.txt","http://www.almhuette-raith.at/robots.txt"]

