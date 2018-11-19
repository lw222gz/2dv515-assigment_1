package application.objects;

import static java.util.stream.Collectors.toList;

import java.util.List;

public class UserMatchesVO {
	private List<UserMatchVO> matches;
	private final String title;

	public UserMatchesVO(User comparedUser, List<UserMatch> matches){
		this.matches = matches.stream().map(UserMatchVO::new).collect(toList());
		title = "The user " + comparedUser.getName() + " has the following matching scores.";

	}

	public String getTitle() {
		return title;
	}

	public List<UserMatchVO> getMatches() {
		return matches;
	}

	private class UserMatchVO{
		private String name;
		private double score;

		UserMatchVO(UserMatch userMatch){
			this.name = userMatch.getUserName();
			this.score = userMatch.getMatchScore();
		}

		public double getScore() {
			return score;
		}

		public String getName() {
			return name;
		}
	}
}
