package com.example.hw4;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
  
  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
    vm.getMovies().observe(this, movies -> {
      TextView tv = findViewById(R.id.textView1);
      tv.setText(movies.stream()
        .map(movie -> String.format("%s %s", movie.title, movie.overview))
        .collect(Collectors.joining(", "))
      );
    });
  }
  
  
  
  
  
  
  // ViewModel class to handle MainActivity presentation
  static public class MainViewModel extends ViewModel {
    
    
    private static final String MOVIES_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    
    
    // livedata stores the api response
    // mMovies.value will never be null; only empty list or valid list
    private MutableLiveData<List<Movie>> mMovies;
    
    
    public LiveData<List<Movie>> getMovies() {
      if (mMovies == null) {
        // first time the data is requested, initiate the http request
        // data will initially be empty
        mMovies = new MutableLiveData<>(Collections.emptyList());
        loadMovies();
      }
      return mMovies;
    }
  
  
  
    // asynchronously fetch the movies
    private void loadMovies() {
      AsyncHttpClient client = new AsyncHttpClient();
      client.get(MOVIES_URL, new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Headers headers, JSON json) {
          try {
            JSONArray results = json.jsonObject.getJSONArray("results");
            List<Movie> movies = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
              movies.add(Movie.fromJson(results.getJSONObject(i)));
            }
            mMovies.setValue(movies);
          } catch (JSONException e) {
            e.printStackTrace();
            mMovies.setValue(Collections.emptyList());
          }
        }
        
        @Override
        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
          // for now just log; data will remain null
          Log.e("Main/VM/fetch", "fetch failed"
            + statusCode
            + response
            + Arrays.toString(throwable.getStackTrace())
          );
          mMovies.setValue(Collections.emptyList());
        }
      });
    }
    
    
    
  }
  
  
}