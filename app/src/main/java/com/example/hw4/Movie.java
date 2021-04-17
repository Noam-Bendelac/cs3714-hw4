package com.example.hw4;

import org.json.JSONException;
import org.json.JSONObject;

// immutable model class for movie
public class Movie {
  
  public final String title;
  public final String overview;
  public final String backdropURL;
  public final String posterURL;
  
  private Movie(String title, String overview, String backdropURL, String posterURL) {
    this.title = title;
    this.overview = overview;
    this.backdropURL = backdropURL;
    this.posterURL = posterURL;
  }
  
  public static Movie fromJson(JSONObject json) {
    try {
      return new Movie(
        json.getString("title"),
        json.getString("overview"),
        String.format("TODO/%s", json.getString("backdrop_path")),
        String.format("TODO/%s", json.getString("poster_path"))
      );
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
  }
}
