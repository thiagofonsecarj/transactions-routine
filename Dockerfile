FROM openjdk:11
ADD target/transaction_routine-0.0.1-SNAPSHOT.jar transaction_routine-0.0.1-SNAPSHOT.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar","active.profile", "transaction_routine-0.0.1-SNAPSHOT.jar"]
