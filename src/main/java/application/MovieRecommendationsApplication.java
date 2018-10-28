package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MovieRecommendationsApplication {

	public static void main(String[] args){
		SpringApplication.run(MovieRecommendationsApplication.class, args);
	}

	@GetMapping("/")
	public String get(){
		return "hi";
	}
}
