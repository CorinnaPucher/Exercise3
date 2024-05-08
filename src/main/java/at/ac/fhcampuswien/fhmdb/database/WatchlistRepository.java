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
        try{
            dao.createIfNotExists(movie);
        }catch (java.sql.SQLException e){
            System.out.println("Already Exists");
        }
        return 0;
    }
    public int removeFromWatchlist (String apiID) throws SQLException {
        DeleteBuilder<WatchlistEntity, Long> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("apiId", apiID);
        deleteBuilder.delete();
        return 0;
    }
    public List<WatchlistEntity> getWatchlist() throws SQLException {
        List<WatchlistEntity> s= dao.queryForAll();
        for (WatchlistEntity e : s){
            System.out.println(e.apiId);
        }
        return dao.queryForAll();
    }
}
