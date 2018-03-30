package com.sliit.blockbuster;

/**
 * movie model created for map incoming data from webservices and getters
 * created for get values from created object
 *
 */

public class MovieModel {
    private String movieName;
    private String image;
    private String year;
    private String released;
    private String genre;
    private String runtime;
    private String director;
    private String imdbRating;
    private String actors;
    private String plot;
    private String url;
    private String writer;

    public MovieModel(String movieName, String image, String year, String released, String genre,
                      String runtime, String director, String imdbRating, String actors,
                      String plot, String url,String writer) {
        this.movieName = movieName;
        this.image = image;
        this.year = year;
        this.released = released;
        this.genre = genre;
        this.runtime = runtime;
        this.director = director;
        this.imdbRating = imdbRating;
        this.actors = actors;
        this.plot = plot;
        this.url = url;
        this.writer = writer;

    }

    public String getGenre() {
        return genre;
    }

    public String getPlot() {
        return plot;
    }

    public String getActors() { return actors; }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getDirector() {
        return director;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getReleased() {
        return released;
    }

    public String getYear() {
        return year;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() { return url; }

    public String getWriter() { return writer; }
}
