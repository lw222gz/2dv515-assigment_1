package application.dataset.handler;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;

import application.objects.Rating;
import application.objects.User;
import org.springframework.stereotype.Component;

@Component
public class DatasetHandler {

	private Map<Long, User> usersById;
	private ClassLoader classLoader;

	@PostConstruct
	void setup(){
		usersById = new HashMap<>();
		classLoader = getClass().getClassLoader();

		parseDatasetFile("users.csv", this::parseUser);
		parseDatasetFile("ratings.csv", this::parseRating);
	}

	public List<User> getUsers(){
		return new ArrayList<>(usersById.values());
	}

	void parseDatasetFile(String filePath, Consumer<String> parser){

		File movies = new File(classLoader.getResource(filePath).getFile());

		try(BufferedReader br = new BufferedReader(new FileReader(movies))){
			String line;
			while(nonNull(line = br.readLine())){
				parser.accept(line);
			}
		} catch (IOException ex){
			throw new RuntimeException("Could not parse dataset");
		}
	}

	void parseUser(String line){
		String[] vals = line.split(";", 2);
		Long userId = parseLong(vals[1]);
		usersById.put(userId, new User(userId, vals[0]));
	}

	void parseRating(String line){
		String[] vals = line.split(";", 3);
		Long userId = parseLong(vals[0]);

		ofNullable(usersById.get(userId))
				.ifPresent(user -> user.addRating(new Rating(vals[1], parseDouble(vals[2]), user)));
	}

	public User getUserById(long userId) {
		return usersById.get(userId);
	}
}
