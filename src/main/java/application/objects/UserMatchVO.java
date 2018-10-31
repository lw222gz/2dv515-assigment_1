package application.objects;

public class UserMatchVO {
	private final String name;
	private final double matchScore;

	public UserMatchVO(String name, double matchScore){
		this.name = name;
		this.matchScore = matchScore;
	}

	public double getMatchScore() {
		return matchScore;
	}

	public String getName() {
		return name;
	}
}
