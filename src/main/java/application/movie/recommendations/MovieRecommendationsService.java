package application.movie.recommendations;

import static java.lang.Double.compare;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.dataset.handler.DatasetHandler;
import application.objects.MovieMatch;
import application.objects.Rating;
import application.objects.User;
import application.objects.UserMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieRecommendationsService {

	@Autowired
	private DatasetHandler datasetHandler;

	public List<MovieMatch> getRecommendations(User user, List<UserMatch> userMatches) {
		List<User> users = datasetHandler.getUsers();

		Map<User, UserMatch> userToUserMatch = userMatches.stream().collect(toMap(UserMatch::getUser, identity()));

		Map<String, List<Rating>> movieToRatings = new HashMap<>();
		for(User otherUser : users){
			if(otherUser != user){
				List<Rating> userRatings = otherUser.getRatings().stream()
						.filter(rating -> hasUserNotSeenRatedMovie(user, rating)).collect(toList());

				userRatings.forEach(rating -> {
					if(movieToRatings.get(rating.getMovie()) == null){
						List<Rating> ratingList = new ArrayList<>();
						ratingList.add(rating);
						movieToRatings.put(rating.getMovie(), ratingList);
					} else {
						movieToRatings.get(rating.getMovie()).add(rating);
					}
				});
			}
		}

		List<MovieMatch> matches = new ArrayList<>();

		for(Map.Entry<String, List<Rating>> movieRatings : movieToRatings.entrySet()){
			String movie = movieRatings.getKey();
			MovieMatch movieMatch = new MovieMatch(movie);

			double totalRatingScore = 0;
			double totalMatchScore = 0;

			for(Rating rating : movieRatings.getValue()){
				UserMatch match = userToUserMatch.get(rating.getUser());
				totalRatingScore += rating.getScore() * match.getMatchScore();
				totalMatchScore += match.getMatchScore();
			}

			movieMatch.setMatchScore(totalRatingScore / totalMatchScore);
			matches.add(movieMatch);
		}

		return matches.stream()
				.sorted((matchA, matchB) -> compare(matchB.getMatchScore(), matchA.getMatchScore()))
				.collect(toList());
	}

	private boolean hasUserNotSeenRatedMovie(User user, Rating rating) {
		return user.getRatings().stream().noneMatch(userRating -> userRating.getMovie().equals(rating.getMovie()));
	}
}
