package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.database.WatchlistEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.WatchListCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WatchlistController implements Initializable {
    @FXML
    public JFXListView movieListView;
    private WatchlistRepository watchlistRepository = new WatchlistRepository();

    public List<Movie> allMovies = new ArrayList<>();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes
    @FXML
    public Button homeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<WatchlistEntity> watchlistEntities = watchlistRepository.getWatchlist();
            MovieRepository movieRepository = new MovieRepository();

            for (WatchlistEntity entity: watchlistEntities) {
                MovieEntity foundMovie = movieRepository.getMovie(entity.apiId);
                if(foundMovie != null){
                    observableMovies.add(new Movie(foundMovie));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new WatchListCell()); // use custom cell factory to display data
        homeButton.setOnAction(actionEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 890, 620);
                Stage root = (Stage) homeButton.getScene().getWindow();
                root.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}