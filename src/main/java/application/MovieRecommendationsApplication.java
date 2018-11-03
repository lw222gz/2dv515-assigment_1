package application;

import application.euclidean.EuclideanDistanceService;
import application.objects.UserMatchesVO;
import application.pearson.PearsonCorrelationSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MovieRecommendationsApplication {

	@Autowired
	private EuclideanDistanceService euclideanDistanceService;

	@Autowired
	private PearsonCorrelationSerivce pearsonCorrelationSerivce;

	public static void main(String[] args){
		SpringApplication.run(MovieRecommendationsApplication.class, args);
	}


	@GetMapping("/euclidean/{userId}")
	public UserMatchesVO getEuclidean(@PathVariable Long userId){
		return euclideanDistanceService.calculateEuclideanDistanceForUser(userId);
	}

	@GetMapping("/pearson/{userId}")
	public UserMatchesVO getPearson(@PathVariable Long userId){
		return pearsonCorrelationSerivce.calculatePearsonCorrelationScore(userId);
	}
}
