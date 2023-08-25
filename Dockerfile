FROM eclipse-temurin:17-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY marca_um.png marca_um.png
COPY firma.png firma.png
ENTRYPOINT ["java","-jar","/app.jar"]
