FROM openjdk:8
VOLUME /tmp
ADD lunchagator-server.jar app.jar
RUN sh -c 'touch /app.jar'
COPY entrypoint.sh /usr/local/bin/
ENV JAVA_OPTS=""
ENTRYPOINT ["entrypoint.sh"]
