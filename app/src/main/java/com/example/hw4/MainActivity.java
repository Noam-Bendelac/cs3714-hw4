package com.example.hw4;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {
  
  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    // not ideal, just doing this for now
    List<Movie> temp = new ArrayList<>();
    ListAdapter<Movie> movieAdapter = new ListAdapter<>(this, temp, R.layout.item_movie, new MovieBinder());
    
    MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
    vm.getMovies().observe(this, movies -> {
      temp.clear();
      temp.addAll(movies);
      movieAdapter.notifyDataSetChanged();
      
      // TextView tv = findViewById(R.id.textView1);
      // tv.setText(movies.stream()
      //   .map(movie -> String.format("%s\n%s", movie.title, movie.overview))
      //   .collect(Collectors.joining(",\n"))
      // );
    });
  
    RecyclerView rvMovies = findViewById(R.id.recycViewMovies);
    rvMovies.setAdapter(movieAdapter);
    rvMovies.setLayoutManager(new LinearLayoutManager(this));
    
  }
  
  
  
  
  
  
  // ViewModel class to handle MainActivity presentation.
  // this class outlives MainActivity onDestroy for configuration changes, but will be destroyed
  //  when MainActivity is actually done
  static public class MainViewModel extends ViewModel {
    
    
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
      MoviesDatabase.getMovies(movieList -> mMovies.setValue(movieList));
    }
    
    
    
  }
  
  
}