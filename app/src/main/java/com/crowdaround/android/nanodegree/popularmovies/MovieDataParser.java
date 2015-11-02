package com.crowdaround.android.nanodegree.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 10/28/2015.
 */
public class MovieDataParser {

    private final String LOG_TAG = MovieDataParser.class.getSimpleName();

    public static List<Movie> getMoviesFromJSON(String moviesJsonStr)
        throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String TMDB_RESULTS = "results";
        final String TMDB_TITLE = "original_title";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_RELEASE_DATE = "release_date";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_USER_RATING = "vote_average";
        //final String DATE_FORMAT = "yyyy-MM-dd";
        final String MOVIE_POSTER_BASE_URI = "http://image.tmdb.org/t/p/w185/";

        JSONObject moviesJSON = new JSONObject(moviesJsonStr);
        JSONArray moviesJSONArray = moviesJSON.getJSONArray(TMDB_RESULTS);

        ArrayList<Movie> movieList = new ArrayList<>();

        for (int i = 0; i < moviesJSONArray.length(); i++) {
            JSONObject movieJSON = moviesJSONArray.getJSONObject(i);
            String title = movieJSON.getString(TMDB_TITLE);
            String overview = movieJSON.getString(TMDB_OVERVIEW);
            String releaseDate = movieJSON.getString(TMDB_RELEASE_DATE);
            String posterPath = movieJSON.getString(TMDB_POSTER_PATH);
            String userRating = movieJSON.getString(TMDB_USER_RATING);

            Movie movie = new Movie(
                    title,
                    overview,
                    releaseDate,
                    userRating,
                    MOVIE_POSTER_BASE_URI + posterPath
            );

            movieList.add(movie);
        }

        return movieList;
    }
}
