package com.example.hw4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.Arrays;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
    vm.getMovies().observe(this, jsonObject -> {
      TextView tv = findViewById(R.id.textView1);
      tv.setText(jsonObject == null ? "NULL" : jsonObject.toString());
    });
  }
  
  
  
  
  
  
  // ViewModel class to handle MainActivity presentation
  static public class MainViewModel extends ViewModel {
    
    
    private static final String MOVIES_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    
    
    // livedata stores the api response
    private MutableLiveData<JSONObject> mMovies;
    
    
    public LiveData<JSONObject> getMovies() {
      if (mMovies == null) {
        // first time the data is requested, initiate the http request
        // data will initially be null
        mMovies = new MutableLiveData<>(null);
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
          mMovies.setValue(json.jsonObject);
        }
        
        @Override
        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
          // for now just log; data will remain null
          Log.e("Main/VM/fetch", "fetch failed"
            + statusCode
            + response
            + Arrays.toString(throwable.getStackTrace())
          );
        }
      });
    }
    
    
    
  }
  
  
}