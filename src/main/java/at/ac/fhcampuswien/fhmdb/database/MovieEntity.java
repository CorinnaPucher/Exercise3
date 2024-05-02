package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.field.DatabaseField;

public class MovieEntity {
    @DatabaseField(generatedId = true)
    public long id;
    @DatabaseField
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
}
