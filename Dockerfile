FROM eclipse-temurin:17.0.14_7-jdk-jammy AS build
WORKDIR /usr/local/app
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw clean dependency:go-offline
COPY src src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17.0.14_7-jre-jammy AS dfinal
WORKDIR /opt/app
COPY target/shrul.jar ./
ENTRYPOINT ["java", "-jar", "/opt/app/shrul.jar"]

FROM eclipse-temurin:17.0.14_7-jre-jammy AS final
WORKDIR /opt/app
COPY --from=build /usr/local/app/target/shrul.jar /opt/app/shrul.jar
ENTRYPOINT ["java", "-jar", "/opt/app/shrul.jar"]
