package com.crowdaround.android.nanodegree.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PostersActivityFragment extends Fragment {

    private static final String LOG_TAG = PostersActivityFragment.class.getSimpleName();

    MovieAdapter mMoviesAdapter;
    ArrayList<Movie> mMovieList;
    GridView mPosterGridView;

    private static final String MOVIE_LIST_KEY = "movies";

    public PostersActivityFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIE_LIST_KEY, mMovieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIE_LIST_KEY)) {
            mMovieList = new ArrayList<>();
        }
        else {
            mMovieList = savedInstanceState.getParcelableArrayList(MOVIE_LIST_KEY);
        }

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_posters, container, false);

        mMoviesAdapter = new MovieAdapter(
                getActivity(),
                mMovieList
        );

        mPosterGridView = (GridView)v.findViewById(R.id.poster_grid_view);
        mPosterGridView.setAdapter(mMoviesAdapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mMoviesAdapter.clear();
            for (Movie movie : movies) {
                mMoviesAdapter.add(movie);
            }
        }

        @Override
        protected List<Movie> doInBackground(String... params) {

            final String DISCOVER_MOVIE_BASE_URI = "https://api.themoviedb.org/3/discover/movie";
            final String SORT_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";
            final String SORT_BY_POPULARITY = "popularity";
            final String SORT_BY_FAVORITES = "vote_average";
            final String SORT_DESC = ".desc";

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;
            List<Movie> movieList = null;

            try {
                // Construct the URL for the TheMovieDB.org query
                // Possible parameters are available at TheMovieDB API page, at
                // https://www.themoviedb.org/documentation/api/
                // Sample URI: https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=<yourapikeyhere>
                // Sample image URI: http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg

                Uri builtUri = Uri.parse(DISCOVER_MOVIE_BASE_URI).buildUpon()
                        .appendQueryParameter(SORT_PARAM, SORT_BY_POPULARITY + SORT_DESC)
                        .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    moviesJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Append a newlinek for readability
                    builder.append(line + "\n");
                }

                if (builder.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    moviesJsonStr = null;
                }
                moviesJsonStr = builder.toString();
                movieList = new MovieDataParser().getMoviesFromJSON(moviesJsonStr);

                Log.v(LOG_TAG, "Movie JSON string: " + moviesJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attempting
                // to parse it.
                moviesJsonStr = null;
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't parse it, nothing to return.
                moviesJsonStr = null;
            }finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            return movieList;
        }
    }

    private void updateMovies() {
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //String zipCode = preferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
        //String displayUnits = preferences.getString(getString(R.string.pref_units_key), getString(R.string.pref_units_default));
        new FetchMoviesTask().execute();
    }
}
