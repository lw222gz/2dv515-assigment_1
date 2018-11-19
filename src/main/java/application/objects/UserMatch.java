package application.objects;

public class UserMatch {
	private final User user;
	private final double matchScore;

	public UserMatch(User user, double matchScore){
		this.user = user;
		this.matchScore = matchScore;
	}

	public double getMatchScore() {
		return matchScore;
	}

	public String getUserName() {
		return user.getName();
	}

	public User getUser() {
		return user;
	}
}
