package application.objects;

import java.util.List;

public class MovieMatchesVO {
	private String title;
	private List<MovieMatch> movieMatches;

	public MovieMatchesVO(User user, List<MovieMatch> movieMatches){
		this.title = "Movie recommendations for the user " + user.getName();
		this.movieMatches = movieMatches;
	}

	public String getTitle() {
		return title;
	}

	public List<MovieMatch> getMovieMatches() {
		return movieMatches;
	}
}
