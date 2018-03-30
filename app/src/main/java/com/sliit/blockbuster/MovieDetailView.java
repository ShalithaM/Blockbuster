package com.sliit.blockbuster;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MovieDetailView extends AppCompatActivity {

    //base URL of the second webservice to get movie data by movie title
    public static final String URL = "http://www.omdbapi.com/?apikey=ad294e63&t=";
    private String movieUrl="";
    private String movieName="";
    private MovieModel movie;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_view);

        progressBar = findViewById(R.id.progressBar);
        //set back navigation to activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled( true );

        // Get the Intent that started this activity and extract the string
        movieName = Objects.requireNonNull(getIntent().getExtras()).getString("MOVIE_NAME");
        movieUrl = getIntent().getExtras().getString("WEB_URL");


        //check for network availability
        if(isNetworkAvailable()){
            //load data to the view
            loadMovieDetails();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("No internet connection")
                    .setMessage("Please check your network connection and relaunch application")
                    .setCancelable( false )
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            System.exit( 0 );
                        }}).show();
        }

    }

    //onclick method of MORE INFO button
    public void webView(View view) {

        // set the intents and view next activity
        Intent intent = new Intent( MovieDetailView.this,MovieDetailWebView.class );
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("WEB_URL", movieUrl);
        intent.putExtra("MOVIE_NAME", movieName);
        MovieDetailView.this.startActivity(intent);

    }


    //calling webservice to get single movie detail and set results to the view
    private void loadMovieDetails(){

        progressBar.setVisibility( View.VISIBLE);
        StringRequest stringRequest = new StringRequest( Request.Method.GET,URL+movieName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility( View.GONE);

                        try {
                            JSONObject movieJSON = new JSONObject( response );
                            Log.i("Log************", movieJSON.getString( "Title"));
                                movie = new MovieModel(
                                        movieJSON.getString( "Title" ),
                                        movieJSON.getString( "Poster" ),
                                        movieJSON.getString( "Year" ),
                                        movieJSON.getString( "Released" ),
                                        movieJSON.getString( "Genre" ),
                                        movieJSON.getString( "Runtime" ),
                                        movieJSON.getString( "Director" ),
                                        movieJSON.getString( "imdbRating" ),
                                        movieJSON.getString( "Actors" ),
                                        movieJSON.getString( "Plot" ),
                                        movieJSON.getString( "Website" ),
                                        movieJSON.getString( "Writer")
                                );

                            TextView movieName = findViewById(R.id.movieName);
                            TextView year = findViewById(R.id.year);
                            TextView director = findViewById(R.id.director);
                            TextView actors = findViewById(R.id.actors);
                            TextView genre = findViewById(R.id.genre);
                            TextView plot = findViewById(R.id.plot);
                            TextView writer = findViewById(R.id.writer);
                            ImageView image = findViewById(R.id.image);

                            movieName.setText(movie.getMovieName());
                            year.setText(movie.getYear());
                            director.setText(movie.getDirector());
                            actors.setText(movie.getActors());
                            genre.setText(movie.getGenre());
                            plot.setText(movie.getPlot());
                            writer.setText(movie.getWriter());
                            Picasso.with( MovieDetailView.this )
                                    .load( movie.getImage() )
                                    .placeholder( R.drawable.splash )
                                    .into( image );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility( View.GONE );
                        Toast.makeText( getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                } );
        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }

    //check for network availability
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
