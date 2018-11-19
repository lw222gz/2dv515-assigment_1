package application.objects;

public class MovieMatch {

	private double matchScore;
	private String movie;

	public MovieMatch(String movie){
		this.movie = movie;
	}

	public void setMatchScore(double matchScore) {
		this.matchScore = matchScore;
	}

	public double getMatchScore() {
		return matchScore;
	}

	public String getMovie() {
		return movie;
	}
}
