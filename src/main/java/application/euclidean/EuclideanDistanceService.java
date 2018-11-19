package application.euclidean;

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
public class EuclideanDistanceService {

	private static final double POWER_OF_TWO = 2.0;

	@Autowired
	private DatasetHandler datasetHandler;

	public List<UserMatch> calculateEuclideanDistanceForUser(User user){
		List<User> users = datasetHandler.getUsers();

		return users.stream()
				.filter(otherUser -> otherUser != user)
				.map(otherUser -> new UserMatch(otherUser, euclidean(user, otherUser)))
				.sorted((userA, userB) -> compare(userB.getMatchScore(), userA.getMatchScore()))
				.collect(toList());
	}

	private double euclidean(User userA, User userB) {
		double sum = 0;

		for(Rating userARating : userA.getRatings()){
			for (Rating userBRating : userB.getRatings()){
				if(userARating.getMovie().equals(userBRating.getMovie())){
					sum += Math.pow(userARating.getScore() - userBRating.getScore(), POWER_OF_TWO);
					//Each user is only allowed 1 rating per movie
					break;
				}
			}
		}

		if(sum == 0){
			return 0;
		}

		return new BigDecimal(1.0 / (1.0 + sum))
				.setScale(3, ROUND_HALF_UP)
				.doubleValue();
	}
}
