package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    Dao<WatchlistEntity, Long> dao;

    public WatchlistRepository() {
        this.dao = DatabaseManager.getDatabase().getWatchlistDao();
    }
    public int addToWatchlist(WatchlistEntity movie) throws SQLException {
        // TODO: Fix Duplicate
        dao.createIfNotExists(movie);
        return 0;
    }
    public int removeFromWatchlist (String apiID) throws SQLException {
        DeleteBuilder<WatchlistEntity, Long> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("apiId", apiID);
        deleteBuilder.delete();
        return 0;
    }
    public List<WatchlistEntity> getWatchlist() throws SQLException {
        return dao.queryForAll();
    }
}
