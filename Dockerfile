FROM maven:3.8.4-openjdk-17
WORKDIR /app-store
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src
EXPOSE 6000
CMD ["mvn", "spring-boot:run"]
