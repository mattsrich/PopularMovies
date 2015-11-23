package com.crowdaround.android.nanodegree.popularmovies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private Movie movie;

    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movie = getActivity().getIntent().getParcelableExtra(DetailActivity.EXTRA_MOVIE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        // Title
        TextView title = (TextView)v.findViewById(R.id.title_textview);
        title.setText(movie.getTitle());

        //Release date
        TextView releaseDate = (TextView)v.findViewById(R.id.release_date_textview);
        releaseDate.setText(movie.getReleaseYear());

        // Movie poster
        ImageView posterImageView = (ImageView)v
                .findViewById(R.id.detail_poster_imageView);
        Picasso.with(getActivity()).load(movie.getPosterURI()).placeholder(R.drawable.default_poster).into(posterImageView);

        // User vote average
        TextView voteAverageTextview = (TextView)v.findViewById(R.id.vote_average_textview);
        voteAverageTextview.setText(movie.getUserRating()+"/10");

        // Overview
        TextView overviewTextview = (TextView)v.findViewById(R.id.overview_textview);
        overviewTextview.setText(movie.getOverview());

        return v;
    }
}
