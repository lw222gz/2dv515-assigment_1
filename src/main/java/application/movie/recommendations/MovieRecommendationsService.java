package application.movie.recommendations;

import static com.google.common.collect.MultimapBuilder.hashKeys;
import static com.google.common.collect.Multimaps.toMultimap;
import static java.lang.Double.compare;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import application.dataset.handler.DatasetHandler;
import application.objects.MovieMatch;
import application.objects.Rating;
import application.objects.User;
import application.objects.UserMatch;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieRecommendationsService {

	@Autowired
	private DatasetHandler datasetHandler;

	public List<MovieMatch> getRecommendations(User user, List<UserMatch> userMatches) {
		List<User> users = datasetHandler.getUsers();

		Map<User, UserMatch> userToUserMatch = userMatches.stream().collect(toMap(UserMatch::getUser, identity()));

		Multimap<String, Rating> movieToRatings = users.stream()
				.flatMap(otherUser -> otherUser.getRatings().stream())
				.filter(rating -> hasUserNotSeenRatedMovie(user, rating))
				.collect(toMultimap(Rating::getMovie, identity(), hashKeys().arrayListValues()::build));

		List<MovieMatch> matches = new ArrayList<>();

		for(String movie : movieToRatings.keySet()){
			Collection<Rating> movieRatings =  movieToRatings.get(movie);
			MovieMatch movieMatch = new MovieMatch(movie);

			double totalRatingScore = 0;
			double totalMatchScore = 0;

			for(Rating rating : movieRatings){
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
