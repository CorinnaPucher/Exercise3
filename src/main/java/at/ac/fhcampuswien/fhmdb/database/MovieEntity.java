package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.List;

public class MovieEntity {
    @DatabaseField(generatedId = true)
    public long id;
    @DatabaseField(unique = true)
    public String apiId;
    @DatabaseField
    public String title;
    @DatabaseField
    public String genres;
    @DatabaseField
    public int releaseYear;
    @DatabaseField
    public String description;
    @DatabaseField
    public String imgUrl;
    @DatabaseField
    public int lengthInMinutes;
    @DatabaseField
    public double rating;

    public MovieEntity() {}

    public MovieEntity(Movie movie) {
        this.apiId = movie.id;
        this.title = movie.title;
        this.genres = genresToString(movie.genres);
        this.releaseYear = movie.releaseYear;
        this.description = movie.description;
        this.imgUrl = movie.imgUrl;
        this.lengthInMinutes = movie.lengthInMinutes;
        this.rating = movie.rating;
    }

    public static String genresToString (String[] genres) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String genre : genres) {
            stringBuilder.append(genre + ",");
        }
        stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length()-1);
        return stringBuilder.toString();
    }

    public static List<MovieEntity> fromMovies (List<Movie> movies) {
        List<MovieEntity> movieEntityList = new ArrayList<>();
        for (Movie movie : movies) {
            MovieEntity movieEntity = new MovieEntity(movie);
            movieEntityList.add(movieEntity);
        }
        return movieEntityList;
    }

    public static List<Movie> toMovies (List<MovieEntity> movieEntities) {
        List<Movie> movieList = new ArrayList<>();
        for (MovieEntity movieEntity : movieEntities) {
            Movie movie = new Movie(movieEntity);
            movieList.add(movie);
        }
        return movieList;
    }
}
