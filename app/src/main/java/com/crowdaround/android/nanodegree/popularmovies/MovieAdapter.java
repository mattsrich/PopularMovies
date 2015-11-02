package com.crowdaround.android.nanodegree.popularmovies;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Matt on 10/28/2015.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity)getContext();
        Movie movie = getItem(position);
        if (convertView == null) {
            convertView = activity.getLayoutInflater()
                    .inflate(R.layout.poster_item, parent, false);
        }

        ImageView imageView = (ImageView)convertView
                .findViewById(R.id.poster_item_imageView);
        Picasso.with(activity).load(movie.getPosterURI()).placeholder(R.drawable.default_poster).into(imageView);

        return convertView;
    }

}
