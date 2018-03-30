package com.sliit.blockbuster;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder>{

    private List<MovieModel> movies;
    private Context context;
    String date;

    public MovieListAdapter(List<MovieModel> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_movie_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MovieModel movie = movies.get(position);

        //setting movie details to card view
        holder.movieName.setText(movie.getMovieName());
        holder.year.setText( movie.getYear() );
        holder.genre.setText(movie.getGenre());
        holder.plot.setText(movie.getPlot());

        Picasso.with( context )
            .load( movie.getImage() )
                .placeholder( R.drawable.splash )
            .into( holder.image );


        //when the card clicked onclick listener will work
        holder.linearLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //passing selected movie name and web url to MovieDetailView activity by using intents
                Intent intent = new Intent( context, MovieDetailView.class);
                intent.putExtra("MOVIE_NAME", movie.getMovieName());
                intent.putExtra("WEB_URL", movie.getUrl());
                //trigger the MovieDetailView activity
                context.getApplicationContext().startActivity(intent);

            }
        } );
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView movieName;
        public TextView year;
        public TextView genre;
        public TextView plot;
        public ImageView image;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            movieName = itemView.findViewById(R.id.movieName);
            year = itemView.findViewById(R.id.year);
            genre = itemView.findViewById(R.id.genre);
            image = itemView.findViewById( R.id.image );
            plot = itemView.findViewById(R.id.plot);
            linearLayout = itemView.findViewById( R.id.linearLayout );
        }

    }

}
