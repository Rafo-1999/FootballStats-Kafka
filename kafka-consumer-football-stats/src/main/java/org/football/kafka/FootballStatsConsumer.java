package org.football.kafka;

import org.football.interfaces.FootballStatsInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class FootballStatsConsumer implements FootballStatsInterface {

  private static final Logger LOGGER = LoggerFactory.getLogger(FootballStatsConsumer.class);

  private MongoTemplate mongoTemplate;

  public FootballStatsConsumer( MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  @KafkaListener(topics = "football-stats", groupId = "football")
  public void consumer(String message) {
    try {
      LOGGER.info(String.format("Message received -> %s", message));
      JSONObject jsonObject = new JSONObject(message);

      if (jsonObject.has("rounds")) {
        JSONArray roundsArray = jsonObject.getJSONArray("rounds");
        saveToDatabase(roundsArray);
      } else {
        LOGGER.error("Error: not separated JSON message");
      }
    } catch (Exception e) {
      LOGGER.error("Error processing Kafka message: " + e.getMessage());
    }
  }

  private void saveToDatabase(JSONArray roundsArray) {
    try {
      for (Object roundObject : roundsArray) {
        Document document = Document.parse(roundObject.toString());
        mongoTemplate.insert(document, "premierLeague");
      }
    } catch (Exception e) {
      LOGGER.error("Error data not added to database: " + e.getMessage());
    }
  }

}
