package application.objects;

public class MovieReview {
	private float raiting;
	private Movie movie;


	public MovieReview (float raiting, Movie movie){
		this.raiting = raiting;
		this.movie = movie;
	}

	public float getRaiting() {
		return raiting;
	}

	public Movie getMovie() {
		return movie;
	}
}
