FROM maven:3.8.8-eclipse-temurin-17-alpine as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 mvn -f $HOME/pom.xml clean package

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /usr/app/target/*.jar app.jar
COPY marca_um.png marca_um.png
COPY firma.png firma.png
ENTRYPOINT ["java","-jar","/app.jar"]
