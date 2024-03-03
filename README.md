# Football Stats Project

## Description

The Football Stats project is a Java application that processes football data received from a Kafka topic, stores it in a MongoDB database, and provides functionalities for producing and consuming football statistics.

## Requirements
1. Java 8 or higher
2. Apache Kafka
3. MongoDB

## Installation

To install and run the Football Stats project, follow these steps:

1. Clone the repository to your local machine:

 `git clone https://github.com/Rafo-1999/FootballStats-Kafka.git`

2. Install Apache Kafka from official website:

`https://kafka.apache.org/`

3. Start the Kafka broker service:
   
Open  terminal session and run: `bin/kafka-server-start.sh config/server.properties`

4. Start the ZooKeeper service:
   
Open another terminal session and run: `bin/zookeeper-server-start.sh config/zookeeper.properties`

5. Install MongoDB database from official website:

`https://www.mongodb.com/try/download/community`

6. Create database and collection and connect to the database 

## Usage
1. Start the Kafka producer by running the FootballStatsProducer class. This will fetch football statistics data from the external API and publish it to the Kafka topic.

2. Start the Kafka consumer by running the FootballStatsConsumer class. This will listen to the Kafka topic, process the incoming messages, and store them in the MongoDB database.





