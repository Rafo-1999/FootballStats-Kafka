package org.football;

import org.football.kafka.FootballStatsProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FootballStatsProducerModule implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(FootballStatsProducerModule.class);
  }

  @Autowired
  private FootballStatsProducer footballStatsProducer;

  @Override
  public void run(String ...args)throws Exception{
    footballStatsProducer.sendMessage();
  }
}