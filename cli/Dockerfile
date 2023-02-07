# Multi-stage build
FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw -B dependency:go-offline
COPY ./src ./src
RUN ./mvnw -B verify

FROM eclipse-temurin:17-jre-jammy
LABEL org.opencontainers.image.authors="Philippe Collet philippe.collet@univ-cotedazur.fr"
ARG JAR_FILE
RUN addgroup dockergroup; adduser --ingroup dockergroup --disabled-password --system --shell /bin/false dockeruser
WORKDIR /opt/app
COPY wait-for-it.sh ./wait-for-it.sh
COPY --from=builder /opt/app/target/${JAR_FILE} ./app.jar
RUN chown -R dockeruser:dockergroup /opt/app && chmod a+x ./wait-for-it.sh
# Starting the service (shell form of ENTRYPOINT used for substitution)
USER dockeruser
ENTRYPOINT java -jar /opt/app/app.jar --tcf.host.baseurl=$SERVER_URL
