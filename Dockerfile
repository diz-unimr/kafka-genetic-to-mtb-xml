
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /home/gradle/src
ENV GRADLE_USER_HOME=/gradle

COPY . .
RUN ./gradlew clean build --info && \
    java -Djarmode=layertools -jar build/libs/*.jar extract

FROM gcr.io/distroless/java21:nonroot

USER root
USER nonroot

WORKDIR /opt/kafka-genetic-to-mtb-xml
COPY --from=build /home/gradle/src/dependencies/ ./
COPY --from=build /home/gradle/src/spring-boot-loader/ ./docker
COPY --from=build /home/gradle/src/application/ ./
COPY HealthCheck.java .

USER 65532
ARG GIT_REF=""
ARG GIT_URL=""
ARG BUILD_TIME=""
ARG VERSION=0.0.0
ENV APP_VERSION=${VERSION} \
    SPRING_PROFILES_ACTIVE="prod"
EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=85", "org.springframework.boot.loader.launch.JarLauncher"]

HEALTHCHECK --interval=25s --timeout=3s --retries=2 CMD ["java", "HealthCheck.java", "||", "exit", "1"]


LABEL org.opencontainers.image.created=${BUILD_TIME} \
    org.opencontainers.image.authors="K. Karki" \
    org.opencontainers.image.source=${GIT_URL} \
    org.opencontainers.image.version=${VERSION} \
    org.opencontainers.image.revision=${GIT_REF} \
    org.opencontainers.image.vendor="diz.uni-marburg.de" \
    org.opencontainers.image.title="kafka-genetic-to-mtb-xml" \
    org.opencontainers.image.description="Create XML file and send to Onkostar"