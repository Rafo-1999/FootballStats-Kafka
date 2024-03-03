package org.football.kafka;

import org.football.exception.HttpErrorException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FootballStatsProducer {

  private KafkaTemplate<String, String> kafkaTemplate;

  private static final Logger LOGGER= LoggerFactory.getLogger(FootballStatsProducer.class);
  private static final HttpClient httpClient = HttpClient.newHttpClient();

  public FootballStatsProducer( KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void fetchDataAndSendMessage() {
    try {
      String jsonData = fetchDataFromURL();
      sendMessageToKafka(jsonData);
    } catch (Exception e) {
      LOGGER.error("Error data not send " + e.getMessage());
    }
  }

  private String fetchDataFromURL() throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://raw.githubusercontent.com/openfootball/football.json/master/2015-16/en.1.json"))
        .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() != 200) {
      throw new HttpErrorException("HTTP request failed with status code: " + response.statusCode());
    }
    return response.body();
  }

  private void sendMessageToKafka(String jsonData) {
    LOGGER.info("Football stats -> {}", jsonData);
    Message<String> message = MessageBuilder
        .withPayload(jsonData)
        .setHeader(KafkaHeaders.TOPIC, "football-stats")
        .build();
    kafkaTemplate.send(message);
  }

}
