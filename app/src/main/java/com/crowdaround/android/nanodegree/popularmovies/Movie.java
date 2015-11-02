package com.crowdaround.android.nanodegree.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Matt on 10/23/2015.
 */
public class Movie implements Parcelable {

    private String mTitle;
    private String mOverview;
    private String mReleaseDate;
    private String mUserRating;
    private String mPosterURI;

    public Movie() {}

    public Movie(String title,
                 String overview,
                 String releaseDate,
                 String userRating,
                 String mPosterURI) {
        this.mTitle = title;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mUserRating = userRating;
        this.mPosterURI = mPosterURI;
    }

    private Movie(Parcel in) {
        mPosterURI = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return mPosterURI;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mPosterURI);
    }

    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getPosterURI() {
        return mPosterURI;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }
}
