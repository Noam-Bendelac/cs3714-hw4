package com.example.hw4;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

public class MovieBinder implements ListAdapter.ItemBinder<Movie> {
  
  private final Context mCtx;
  
  public MovieBinder(Context ctx) {
    mCtx = ctx;
  }
  
  /**
   * takes the itemView to bind, and the movie data to bind to;
   * and binds itemView to reflect the I item's data
   */
  @Override
  public void bind(View itemView, Movie movie, int position) {
    // make itemView reflect movie's data
    ConstraintLayout conLayout = itemView.findViewById(R.id.constraintLayout);
    TextView textTitle = itemView.findViewById(R.id.textTitle);
    TextView textOverview = itemView.findViewById(R.id.textOverview);
    ImageView image = itemView.findViewById(R.id.imageView);
    
    textTitle.setText(movie.title);
    textOverview.setText(movie.overview);
    
    Glide.with(mCtx).load(movie.posterURL).into(image);
    
    
    // set alternating background colors
    int lightColor = 40;
    int darkColor = 10;
    conLayout.setBackgroundColor(
      position % 2 == 0
      ? Color.rgb(darkColor, darkColor, darkColor)
      : Color.rgb(lightColor, lightColor, lightColor)
    );
    
  }
}
