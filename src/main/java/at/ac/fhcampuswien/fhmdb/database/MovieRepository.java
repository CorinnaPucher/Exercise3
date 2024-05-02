package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    Dao<MovieEntity, Long> dao;

    public MovieRepository() {
        this.dao = DatabaseManager.getDatabase().getMovieDao();
    }

    public int addAllMovies(List<Movie> movies) throws SQLException {
        List<MovieEntity> entities = MovieEntity.fromMovies(movies);
        for (MovieEntity movieentity : entities) {
            dao.createIfNotExists(movieentity);
        }
        return entities.size();
    }

    public int removeAll () throws SQLException {
        dao.delete(dao.queryForAll());
        return 0;
    }

    public List<MovieEntity> getAllMovies() throws SQLException {
        return dao.queryForAll();
    }

    public MovieEntity getMovie(String apiId) throws SQLException {
        List<MovieEntity> movieEntityList = dao.queryForAll();
        for (MovieEntity movieEntity : movieEntityList) {
            if(movieEntity.apiId.equals(apiId)) return movieEntity;
        }
        return null;
    }
}
