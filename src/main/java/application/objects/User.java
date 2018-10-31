package application.objects;

import java.util.ArrayList;
import java.util.List;

public class User {

	private final long id;
	private final String name;
	private List<Rating> ratings;


	public User(long id, String name){
		this.id = id;
		this.name = name;
		ratings = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void addRaiting(Rating rating){
		ratings.add(rating);
	}

	public List<Rating> getRatings(){
		return ratings;
	}
}
