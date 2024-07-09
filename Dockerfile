FROM ubuntu:latest
RUN apt-get update

RUN curl -sL https://deb.nodesource.com/setup_18.x | bash
RUN apt-get -y install nodejs
RUN node -v
RUN apt-get -y install openjdk-21-jdk
RUN java --version
RUN apt-get -y install git
RUN git --version
COPY DirectoryOfPersons/ /usr/src/jhipster
WORKDIR /usr/src/jhipster
CMD ["./mvnw"]
