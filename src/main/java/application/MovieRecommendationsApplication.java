package application;

import application.dataset.handler.DatasetHandler;
import application.euclidean.EuclideanDistanceService;
import application.movie.recommendations.MovieRecommendationsService;
import application.objects.MovieMatchesVO;
import application.objects.User;
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
	private DatasetHandler datasetHandler;

	@Autowired
	private EuclideanDistanceService euclideanDistanceService;

	@Autowired
	private PearsonCorrelationSerivce pearsonCorrelationSerivce;

	@Autowired
	private MovieRecommendationsService movieRecommendationsService;

	public static void main(String[] args){
		SpringApplication.run(MovieRecommendationsApplication.class, args);
	}


	@GetMapping("/euclidean/{userId}")
	public UserMatchesVO getEuclidean(@PathVariable Long userId){
		User user = datasetHandler.getUserById(userId);
		return new UserMatchesVO(user, euclideanDistanceService.calculateEuclideanDistanceForUser(user));
	}

	@GetMapping("/pearson/{userId}")
	public UserMatchesVO getPearson(@PathVariable Long userId){
		User user = datasetHandler.getUserById(userId);
		return new UserMatchesVO(user, pearsonCorrelationSerivce.calculatePearsonCorrelationScore(user));
	}

	@GetMapping("/pearson/movies/{userId}")
	public MovieMatchesVO getPearsonMovieMatches(@PathVariable Long userId){
		User user = datasetHandler.getUserById(userId);
		return new MovieMatchesVO(user, movieRecommendationsService.getRecommendations(user, pearsonCorrelationSerivce.calculatePearsonCorrelationScore(user)));
	}

	@GetMapping("/euclidean/movies/{userId}")
	public MovieMatchesVO getEuclideanMovieMatches(@PathVariable Long userId){
		User user = datasetHandler.getUserById(userId);
		return new MovieMatchesVO(user, movieRecommendationsService.getRecommendations(user, euclideanDistanceService.calculateEuclideanDistanceForUser(user)));
	}
}
