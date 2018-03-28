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
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {


    public static final String URL = "https://api.myjson.com/bins/12afwv";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<Movie> movies;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        progressBar = findViewById( R.id.progressBar );
        recyclerView = findViewById( R.id.recyclerView );

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        movies = new ArrayList<>();

        if(isNetworkAvailable()){
            loadRecycleViewData();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("No internet connection")
                    .setMessage("Please turn on your network connection and relaunch application")
                    .setCancelable( false )
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            System.exit( 0 );
                        }}).show();
        }
    }

    private void loadRecycleViewData(){

        progressBar.setVisibility( View.VISIBLE);

        StringRequest stringRequest = new StringRequest( Request.Method.GET,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility( View.GONE);

                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            JSONArray jsonArray = jsonObject.getJSONArray( "movies" );

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject( i );
                                Movie movie = new Movie(
                                        jsonObject1.getString( "Title" ),
                                        jsonObject1.getString( "Poster" ),
                                        jsonObject1.getString( "Year" ),
                                        jsonObject1.getString( "Released" ),
                                        jsonObject1.getString( "Genre" ),
                                        jsonObject1.getString( "Runtime" ),
                                        jsonObject1.getString( "Director" ),
                                        jsonObject1.getString( "imdbRating" ),
                                        jsonObject1.getString( "Actors" ),
                                        jsonObject1.getString( "Plot" ),
                                        jsonObject1.getString( "Website" )

                                );
                                movies.add(movie);
                            }

                            adapter = new Adapter(movies,getApplicationContext() );
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
