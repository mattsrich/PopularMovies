package com.crowdaround.android.nanodegree.popularmovies;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Matt on 11/2/2015.
 * This class is presently not used but may be used in a future iteration of the project if I decide
 * to maintain the list of movies in a singleton instead of "parceling" them around between screens.
 */
public class MovieSet {
    /*private ArrayList<Movie> mMovies;

    private static MovieSet sMovieSet;
    private Context mAppContext;

    private MovieSet(Context appContext) {
        mAppContext = appContext;
        mMovies = new ArrayList<Movie>();
    }

    public static MovieSet get(Context c) {
        if (sMovieSet == null) {
            sMovieSet = new MovieSet(c.getApplicationContext());
        }
        return sMovieSet;
    }

    public ArrayList<Movie> getMovies() {
        return mMovies;
    }

    public Movie getMovie(UUID id) {
        for (Movie m : mMovies) {
            if (m.getId().equals(id))
                return m;
        }
        return null;
    }*/
}
