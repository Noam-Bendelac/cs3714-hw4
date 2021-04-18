package com.example.hw4;

import android.view.View;
import android.widget.TextView;

public class MovieBinder implements ListAdapter.ItemBinder<Movie> {
  /**
   * takes the itemView to bind, and the movie data to bind to;
   * and binds itemView to reflect the I item's data
   */
  @Override
  public void bind(View itemView, Movie movie) {
    // make itemView reflect movie's data
    TextView textTitle = itemView.findViewById(R.id.textTitle);
    textTitle.setText(movie.title);
  }
}
