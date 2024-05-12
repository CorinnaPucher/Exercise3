package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    Dao<MovieEntity, Long> dao;

    public MovieRepository() throws DatabaseException {
        this.dao = DatabaseManager.getDatabase().getMovieDao();
    }

    public int addAllMovies(List<Movie> movies) {
        List<MovieEntity> entities = MovieEntity.fromMovies(movies);
        for (MovieEntity movieentity : entities) {
            try{
                dao.createIfNotExists(movieentity);
            }catch (SQLException ignored){}
        }
        return entities.size();
    }

    public int removeAll () throws DatabaseException {
        try {
            dao.delete(dao.queryForAll());
        } catch (SQLException e) {
            throw new DatabaseException("Movies cannot be removed");
        }
        return 0;
    }

    public List<MovieEntity> getAllMovies() throws DatabaseException {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't get all movies");
        }
    }

    public MovieEntity getMovie(String apiId) throws DatabaseException {
        List<MovieEntity> movieEntityList = null;
        try {
            movieEntityList = dao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException("Couldn't find associated movie");
        }
        for (MovieEntity movieEntity : movieEntityList) {
            if(movieEntity.apiId.equals(apiId)) return movieEntity;
        }
        return null;
    }
}
