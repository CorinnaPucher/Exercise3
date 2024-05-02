package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {
    public static final String DB_URL = "jdbc:h2:file: ./src/main/java/at/ac/fhcampuswien/fhmdb/database/moviedb";
    public static final String USER = "cammona";
    public static final String PASSWORD = "tong";
    private static ConnectionSource connectionSource;
    Dao<MovieEntity, Long> movieDao;
    Dao<WatchlistEntity, Long> watchlistDao;
    private static DatabaseManager instance;

    private DatabaseManager() {
        try {
            createConnection();
            movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);
            watchlistDao = DaoManager.createDao(connectionSource, WatchlistEntity.class);
            createTables();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DatabaseManager getDatabase() {
        if(instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Dao<MovieEntity, Long> getMovieDao() {
        return this.movieDao;
    }

    public Dao<WatchlistEntity, Long> getWatchlistDao() {
        return this.watchlistDao;
    }

    private static void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, WatchlistEntity.class);
    }

    private static void createConnection() throws SQLException {
        connectionSource = new JdbcConnectionSource(DB_URL, USER, PASSWORD);
    }
}