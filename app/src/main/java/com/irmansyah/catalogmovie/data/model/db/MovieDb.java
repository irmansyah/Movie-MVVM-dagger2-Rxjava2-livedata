package com.irmansyah.catalogmovie.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;

import com.irmansyah.catalogmovie.utils.AppConstants;

/**
 * Created by irmansyah on 12/03/18.
 */
@Entity(tableName = AppConstants.TABLE_DB_NAME)
public class MovieDb {

    /** The name of the Cheese table. */
    public static final String TABLE_NAME = AppConstants.TABLE_DB_NAME;

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    /** The name of the name column. */
    public static final String COLUMN_NAME = "title";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

//    @PrimaryKey
    public Integer mId;

    @ColumnInfo(name = COLUMN_NAME)
    public String title;

    public String overview;

    public String imageUrl;

    public String releaseDate;

    public boolean isFavourite;

    @ColumnInfo(name = "created_at")
    public String createdAt;

    @ColumnInfo(name = "updated_at")
    public String updatedAt;

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static MovieDb fromContentValues(ContentValues values) {
        final MovieDb movieDb = new MovieDb();
        if (values.containsKey(COLUMN_ID)) {
            movieDb.id = values.getAsLong(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_NAME)) {
            movieDb.title = values.getAsString(COLUMN_NAME);
        }
        return movieDb;
    }
}
