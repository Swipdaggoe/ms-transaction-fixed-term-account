FROM openjdk:11
VOLUME /tmp
EXPOSE 8112
ADD ./target/ms-transaction-fixed-term-account-0.0.1-SNAPSHOT.jar ms-transaction-fixed-term-account.jar
ENTRYPOINT ["java","-jar","ms-transaction-fixed-term-account.jar"]