package application.dataset.handler;

import static java.lang.Float.parseFloat;
import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;

import application.objects.Movie;
import application.objects.MovieReview;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Component;

@Component
public class DatasetHandler {

	private Map<Long, Movie> moviesById;
	private Multimap<String, MovieReview> userReviews;
	private ClassLoader classLoader;

	@PostConstruct
	void setup(){
		moviesById = new HashMap<>();
		userReviews = ArrayListMultimap.create();
		classLoader = getClass().getClassLoader();

		parseDatasetFile("movies.csv", this::parseMovie);
		parseDatasetFile("review.csv", this::parseReview);
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

	void parseMovie(String line){
		String[] vals = line.split(",", 2);
		long movieId = parseLong(vals[0]);
		moviesById.put(movieId, new Movie(movieId, vals[1]));
	}

	void parseReview(String line){
		String[] vals = line.split(",", 3);

		userReviews.put(vals[0], new MovieReview(parseFloat(vals[2]), moviesById.get(parseLong(vals[1]))));
	}
}
