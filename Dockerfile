FROM java:8-jre

ENV JAR_FILE build/libs/jaxrs-todo-blueprint-all.jar

ENV JAR_HOME /usr/todo-service

EXPOSE 8181

COPY $JAR_FILE $JAR_HOME/

WORKDIR $JAR_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar jaxrs-todo-blueprint-all.jar"]
