package com.sliit.blockbuster;


import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieMainActivity extends AppCompatActivity {


    //base URL of the first webservice to get movie list
    public static final String URL = "https://api.myjson.com/bins/12afwv";
    //creating objects for recycler view and adapter
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    //creating list from movie model to store movie list
    private List<MovieModel> movies;
    //initialize progressbar object to animate loading view
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_movie_main);

        progressBar = findViewById( R.id.progressBar );
        recyclerView = findViewById( R.id.recyclerView );

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( MovieMainActivity.this ) );

        movies = new ArrayList<>();

        //check for network connection availability
        if(isNetworkAvailable()){
            loadRecycleViewData();
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

    // calling webservice and assign results to movie list
    private void loadRecycleViewData(){
        progressBar.setVisibility( View.VISIBLE);
        StringRequest stringRequest = new StringRequest( Request.Method.GET,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility( View.GONE);

                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            JSONArray movieArray = jsonObject.getJSONArray( "movies" );

                            for (int i=0;i<movieArray.length();i++){
                                JSONObject movieJSON = movieArray.getJSONObject( i );
                                MovieModel movie = new MovieModel(
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
                                movies.add(movie);
                            }

                            adapter = new MovieListAdapter(movies,getApplicationContext() );
                            recyclerView.setAdapter( adapter );

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



    //check network connection availability
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
