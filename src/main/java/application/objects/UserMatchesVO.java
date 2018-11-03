package application.objects;

import java.util.List;

/**
 * Copyright (c) 2018 IST - International Software Technology. All rights reserved.
 */
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
