package application.objects;

import java.util.List;

public class UserMatchesVO {
	private List<UserMatchVO> matches;
	private final String title;

	public UserMatchesVO(User comparedUser, List<UserMatchVO> matches){
		this.matches = matches;
		title = "The user " + comparedUser.getName() + " has the following matching scores.";
	}

	public String getTitle() {
		return title;
	}

	public List<UserMatchVO> getMatches() {
		return matches;
	}
}
