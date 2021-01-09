# Backend server
This repo contains all the code for the backend server of our project. In this readme I will explain how to install and 
run the server.

## Hardware Requirements
* Minimum of 4 GB RAM
* Minimum of 25 GB of diskspace

## Software Requirements
* Linux or Windows OS
* Java (minimum version 11)
* A MySQL 8 database

## Config
Before making a build of the code. It is important that you set the correct data in the configuration file. The 
`application.properties` file for the server can be found in `src/main/resources` folder. In this file you can change
the database config and the mail config to your requirements.

## How to build and run the backend server
1. If you have a server which is in compliance with the requirements, and it has Linux or Windows installed. You need to
install the correct Java version. [Download here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
2. Install the MySQL database server. [Download here](https://dev.mysql.com/downloads/mysql/)
3. Now make a build of the backend server. To achieve this run `./gradlew build`. A .jar file will be created in the 
following folder `/build/libs.
4. Upload the jar to the server and run the following command `java -jar path/to/build/name-of-build.jar`
5. Go to the ip of the server and add the correct port behind it
