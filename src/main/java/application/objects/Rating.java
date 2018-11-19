package application.objects;

public class Rating {

	private final String movie;
	private final double score;
	private final User user;

	public Rating(String movie, double score, User user){
		this.movie = movie;
		this.score = score;
		this.user = user;
	}

	public double getScore() {
		return score;
	}

	public String getMovie() {
		return movie;
	}

	public User getUser() {
		return user;
	}
}
