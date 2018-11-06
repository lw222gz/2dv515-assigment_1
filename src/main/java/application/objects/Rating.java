package application.objects;

public class Rating {

	private final String movie;
	private final double score;

	public Rating(String movie, double score){
		this.movie = movie;
		this.score = score;
	}

	public double getScore() {
		return score;
	}

	public String getMovie() {
		return movie;
	}
}
