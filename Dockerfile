FROM maven:eclipse-temurin as builder
WORKDIR /app
COPY mvnw pom.xml ./
COPY ./src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/target/*.jar app/*.jar
ENTRYPOINT ["java","-jar","app/*.jar"]


FROM postgres:16
ENV POSTGRES_DB1=badge_keeper_vol2_db
ENV POSTGRES_DB2=badge_keeper_logger
