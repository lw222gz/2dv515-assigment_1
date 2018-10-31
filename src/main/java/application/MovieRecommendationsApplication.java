package application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.euclidean.EuclideanDistanceService;
import application.objects.UserMatchVO;
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
	public Map<String, List<UserMatchVO>> getEuclidean(@PathVariable Long userId){
		HashMap<String, List<UserMatchVO>> result = new HashMap<>();
		result.put("The given user gave the following resutl towards other users", euclideanDistanceService.calculateEuclideanDistanceForUser(userId));
		return result;
		//return euclideanDistanceService.calculateEuclideanDistanceForUser(userId);
	}

	@GetMapping("/pearson/{userId}")
	public List<UserMatchVO> getPearson(@PathVariable Long userId){
		return pearsonCorrelationSerivce.calculatePearsonCorrelationScore(userId);
	}
}
