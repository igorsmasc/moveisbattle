package com.letscode.moveisbattle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MoveisbattleApplication {

//	@Bean
//	public WebClient webClient(WebClient.Builder builder) {
//		return builder.baseUrl("http://img.omdbapi.com/").build();
//	}

	public static void main(String[] args) {
		SpringApplication.run(MoveisbattleApplication.class, args);
	}

}
