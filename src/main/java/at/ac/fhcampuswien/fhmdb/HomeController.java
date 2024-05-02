package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;
    @FXML
    public TextField yearSearchField;
    @FXML
    public TextField ratingSearchField;
    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        // add genre filter items
        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.setPromptText("Filter by Genre");

        // add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // Sort button:
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                // sort observableMovies ascending
                Collections.sort(observableMovies);
                sortBtn.setText("Sort (desc)");
            } else {
                // sort observableMovies descending
                Collections.reverse(observableMovies);
                sortBtn.setText("Sort (asc)");
            }
        });

        // Filter button:
        searchBtn.setOnAction(actionEvent -> {
            filter();
        });
    }

    /**
     * Parses all Fields and filters the Movielist accordingly
     */
    public void filter() {
        observableMovies.clear();
        String query = searchField.getText();
        String genre = "";
        int releaseYear = -1;
        double ratingFrom = -1;
        if (genreComboBox.getValue() != null) {
            genre = genreComboBox.getValue().toString();
        }
        if (!yearSearchField.getText().equals("")) {
            releaseYear = Integer.parseInt(yearSearchField.getText());
        }
        if (!ratingSearchField.getText().equals("")) {
            ratingFrom = Double.parseDouble(ratingSearchField.getText());
        }
        List<Movie> filteredMovies = JSONAction.parseJSON(MovieAPI.sendRequest(query, genre, releaseYear, ratingFrom));
        observableMovies.addAll(filteredMovies);
    }

    public void filter(String query, String genre, int releaseYear, double ratingFrom) {
        observableMovies.clear();

        List<Movie> filteredMovies = JSONAction.parseJSON(MovieAPI.sendRequest(query, genre, releaseYear, ratingFrom));
        observableMovies.addAll(filteredMovies);
    }

    /**
     * Return actor who most often was in the main cast
     * @param movies all movies
     * @return actor with highest count
     */
    public String getMostPopularActor(List<Movie> movies) {
        Optional<Map.Entry<String, Long>> highestEntry = movies.stream()
                .flatMap(movie -> Arrays.stream(movie.mainCast)) // All mainCastArrays merge into one stream
                .collect(Collectors.groupingBy(e -> e, Collectors.counting())) // Group by Occurences
                .entrySet()  // Maps have to be converted to a Set to convert to Stream
                .stream()
                .max(Map.Entry.comparingByValue());

        if(highestEntry.isPresent()) return highestEntry.get().getKey();
        else return "";
    }

    /**
     * Return title length of the movie with the longest title
     * @param movies all movies
     * @return title length
     */
    public int getLongestMovieTitle(List<Movie> movies) {
        Stream<Movie> streamFromList = movies.stream();

        return streamFromList
                .mapToInt(m -> m.getTitleLength())
                .max()
                .orElseThrow(NoSuchElementException::new);
    }

    /**
     * Return count of movies for specified director
     * @param movies all movies
     * @param director specific director
     * @return count of movies
     */
    public long countMoviesFrom(List<Movie> movies, String director) {
        Stream<Movie> streamFromList = movies.stream();

        return streamFromList
                .filter(movie -> movie.directorIsInsideArray(director))
                .count();
    }

    /**
     * Return movie list with release year between two given years
     * @param movies all movies
     * @param startYear search value greater or equal
     * @param endYear search value less or equal
     * @return filtered movie list
     */
    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        Stream<Movie> streamFromList = movies.stream();

        return streamFromList
                .filter(movie -> (movie.releaseYear >= startYear && movie.releaseYear <= endYear))
                .toList();
    }

    public ObservableList<Movie> getObservableMovies() {
        return observableMovies;
    }
}