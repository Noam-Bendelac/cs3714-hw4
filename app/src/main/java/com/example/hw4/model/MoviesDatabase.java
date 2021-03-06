package com.example.hw4;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.core.util.Consumer;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import okhttp3.Headers;

// repository class that acts as an abstraction of the themoviedb.org database
public class MoviesDatabase {
  
  private static final String MOVIES_URL_BASE =
    // "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    "https://api.themoviedb.org/3/movie/now_playing";
  private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
  
  
  private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";
  
  
  public static final List<Movie> DEFAULT_MOVIES = Collections.emptyList();
  
  // get movies asynchronously from /movie/now_playing endpoint.
  // response passed to callback
  public static void getMovies(int page, Consumer<List<Movie>> callback) {
    AsyncHttpClient client = new AsyncHttpClient();
  
    @SuppressLint("DefaultLocale")
    String url = String.format("%s?api_key=%s&page=%d", MOVIES_URL_BASE, API_KEY, page);
    
    client.get(url, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Headers headers, JSON json) {
        try {
          // process json
          JSONArray results = json.jsonObject.getJSONArray("results");
          List<Movie> movies = new ArrayList<>();
          for (int i = 0; i < results.length(); i++) {
            movies.add(Movie.fromJson(results.getJSONObject(i)));
          }
          // "return" list
          callback.accept(movies);
          
        } catch (JSONException e) {
          e.printStackTrace();
          callback.accept(DEFAULT_MOVIES);
        }
      }
    
      @Override
      public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
        // for now just log; data will remain empty
        Log.e("MovieDB/getMovies", "fetch failed"
          + statusCode
          + response
          + Arrays.toString(throwable.getStackTrace())
        );
        callback.accept(DEFAULT_MOVIES);
      }
    });
  }
  
  
  public static String backdropUrl(String backdropPath) {
    return String.format("%s/w780%s", IMAGE_BASE_URL, backdropPath);
  }
  public static String posterUrl(String posterPath) {
    return String.format("%s/w342%s", IMAGE_BASE_URL, posterPath);
  }
  
}
