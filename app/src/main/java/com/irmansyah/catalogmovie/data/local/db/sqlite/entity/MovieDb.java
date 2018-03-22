package com.irmansyah.catalogmovie.data.local.db.sqlite.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.getColumnInt;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.getColumnString;

/**
 * Created by irmansyah on 12/03/18.
 */
public class MovieDb implements Parcelable {

    private Integer mId;
    private String title;
    private String overview;
    private String imageUrl;
    private String releaseDate;
    private Boolean isFavourite;
    private String createdAt;
    private String updatedAt;

    protected MovieDb(Parcel in) {
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readInt();
        }
        title = in.readString();
        overview = in.readString();
        imageUrl = in.readString();
        releaseDate = in.readString();
        isFavourite = in.readByte() != 0;
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<MovieDb> CREATOR = new Creator<MovieDb>() {
        @Override
        public MovieDb createFromParcel(Parcel in) {
            return new MovieDb(in);
        }

        @Override
        public MovieDb[] newArray(int size) {
            return new MovieDb[size];
        }
    };

    public MovieDb() {
    }

    public MovieDb(Cursor cursor) {
        this.mId = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
        this.imageUrl = getColumnString(cursor, DatabaseContract.MovieColumns.POSTER_PATH);
        this.releaseDate = getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE_DATE);
        this.isFavourite = Boolean.valueOf(getColumnString(cursor, DatabaseContract.MovieColumns.IS_FAVOURITE));
//        this.createdAt = getColumnString(cursor, DatabaseContract.MovieColumns.CREATE_AT);
//        this.updatedAt = getColumnString(cursor, DatabaseContract.MovieColumns.UPDATE_AT);
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (mId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mId);
        }
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(imageUrl);
        parcel.writeString(releaseDate);
        parcel.writeByte((byte) (isFavourite ? 1 : 0));
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
    }
}
