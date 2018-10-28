package application.objects;

public class Movie {
	private long id;
	private String name;

	public Movie(long id, String name){
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}