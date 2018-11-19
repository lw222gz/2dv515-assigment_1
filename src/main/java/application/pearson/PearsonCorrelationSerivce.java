package application.pearson;

import static java.lang.Double.compare;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.List;

import application.dataset.handler.DatasetHandler;
import application.objects.Rating;
import application.objects.User;
import application.objects.UserMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PearsonCorrelationSerivce {

	private static final double POWER_OF_TWO = 2.0;

	@Autowired
	private DatasetHandler datasetHandler;

	public List<UserMatch> calculatePearsonCorrelationScore(User user){
		List<User> users = datasetHandler.getUsers();

		return users.stream()
				.filter(otherUser -> otherUser != user)
				.map(otherUser -> new UserMatch(otherUser, pearson(user, otherUser)))
				.sorted((userA, userB) -> compare(userB.getMatchScore(), userA.getMatchScore()))
				.collect(toList());
	}

	private double pearson(User userA, User userB) {

		double userASum = 0, userBSum = 0, userAsq = 0, userBsq = 0, pSum = 0;
		int sharedAmountOfRatings = 0;

		for(Rating userARating : userA.getRatings()){
			for(Rating userBRating : userB.getRatings()){
				if(userARating.getMovie().equals(userBRating.getMovie())){
					double userAScore = userARating.getScore(), userBScore = userBRating.getScore();

					userASum += userAScore;
					userBSum += userBScore;

					userAsq += Math.pow(userAScore, POWER_OF_TWO);
					userBsq += Math.pow(userBScore, POWER_OF_TWO);

					pSum += userAScore * userBScore;

					sharedAmountOfRatings++;

					//Each user is only allowed 1 rating per movie
					break;
				}
			}
		}

		if(sharedAmountOfRatings == 0){
			return 0;
		}

		double numerator = pSum - ((userASum * userBSum) / sharedAmountOfRatings);

		double userAResult = userAsq - (Math.pow(userASum, POWER_OF_TWO) / sharedAmountOfRatings);
		double userBResult = userBsq - (Math.pow(userBSum, POWER_OF_TWO) / sharedAmountOfRatings);
		double denominator = Math.sqrt(userAResult * userBResult);

		return new BigDecimal(numerator / denominator).setScale(3, ROUND_HALF_UP).doubleValue();
	}
}
