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
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {
  
  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    RecyclerView rvMovies = findViewById(R.id.recycViewMovies);
    Button buttonPrev = findViewById(R.id.buttonPrev);
    Button buttonNext = findViewById(R.id.buttonNext);
    TextView textPage = findViewById(R.id.textPage);
    
    
    ListAdapter<Movie> movieAdapter = new ListAdapter<>(
      this, Collections.emptyList(), R.layout.item_movie, new MovieBinder(this)
    );
    
    MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
    // update view when model changes:
    vm.getMovies().observe(this, movieAdapter::setData);
    vm.getPage().observe(this, page -> {
      textPage.setText(Integer.toString(page));
      // only allow prev page if there is one:
      buttonPrev.setEnabled(page > 1);
    });
  
    rvMovies.setAdapter(movieAdapter);
    rvMovies.setLayoutManager(new LinearLayoutManager(this));
    
  
    // update model on user action:
    buttonPrev.setOnClickListener(v -> {
      vm.prevPage();
    });
    buttonNext.setOnClickListener(v -> {
      vm.nextPage();
    });
    
    
  }
  
  
  
  
  
  
  // ViewModel class to handle MainActivity presentation.
  // this class outlives MainActivity onDestroy for configuration changes, but will be destroyed
  //  when MainActivity is actually done
  static public class MainViewModel extends ViewModel {
    
    
    // livedata stores the api response
    // mMovies.value will never be null; only empty list or valid list
    private final MutableLiveData<List<Movie>> mMovies =
      new MutableLiveData<>(MoviesDatabase.DEFAULT_MOVIES);
    
    // livedata to react to page control
    private final MutableLiveData<Integer> mPage = new MutableLiveData<>(1);
    
    
    public MainViewModel() {
      super();
      // when page selected, load that page
      mPage.observeForever(page -> loadMovies(page));
    }
    
    public LiveData<List<Movie>> getMovies() {
      return mMovies;
    }
    public LiveData<Integer> getPage() {
      return mPage;
    }
    
    public void nextPage() {
      mPage.setValue(mPage.getValue() + 1);
    }
    public void prevPage() {
      mPage.setValue(mPage.getValue() - 1);
    }
  
  
  
    // asynchronously fetch the movies
    private void loadMovies(int page) {
      MoviesDatabase.getMovies(page, movieList -> mMovies.setValue(movieList));
    }
    
    
    
  }
  
  
}