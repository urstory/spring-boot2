FROM urstory/oraclejava8:0.1
VOLUME /tmp
ADD simpleboard-1.0-SNAPSHOT.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djavadecurity.egd=file:/dev/./urandom","-jar","/app.jar"]