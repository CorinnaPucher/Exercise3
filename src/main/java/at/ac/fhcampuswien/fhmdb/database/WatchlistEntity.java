package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.field.DatabaseField;

public class WatchlistEntity {
    @DatabaseField(generatedId = true)
    public long id;
    @DatabaseField
    public String apiId;

    public WatchlistEntity() {}
    public WatchlistEntity(String apiId) {
        this.apiId = apiId;
    }
}
