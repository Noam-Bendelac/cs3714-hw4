# Flix
Flix is an app that allows users to browse movies from the [The Movie Database API](http://docs.themoviedb.apiary.io/#).

---

## Flix

### User Stories

#### REQUIRED (10pts)
- [x] (70pts) User can view a list of movies (title, poster image, and overview) currently playing in theaters from the Movie Database API.
- [x] (10pts) Views should be responsive for both landscape/portrait mode.
   - [x] (5pt) In portrait mode, the poster image, title, and movie overview is shown.
   - [x] (5pt) In landscape mode, the rotated alternate layout should use the backdrop image instead and show the title and movie overview to the right of it.
- [x] (20pts) Improved the user interface by experimenting with styling and coloring.

### App Walkthough GIF


<img src="https://github.com/Noam-Bendelac/cs3714-hw4/blob/main/screen_record.gif?raw=true"><br>

### Notes


I wasn't sure how to lay out the elements of each movie component, as I didn't like the extra space at the bottom of movie items with longer descriptions. I decided to center both the description and the image vertically, so whichever one is taller determines the height of the item, and the other is still centered.

The borders between items looked a little confusing depending on the description length, so I decided that a unique and good-looking way to fix this was to alternate background colors, like a webpage table, rather than use borders that could add a little clutter to the screen. I made sure that there was still sufficient contrast between the text and the background colors.

I added a page control (next and previous buttons, and a current page TextView) to allow the user to navigate between the pages of the database's `now_playing` endpoint. Before I implemented disabling the `prev` button when on page 1, I got invalid responses from the database when I requested page "0", so that was something I had to debug. I found my use of ViewModel and LiveData made this feature easier to implement and debug than it could have been had I not used those builtin android classes.

I successfully uploaded the GIF screenrecording to imgur at https://imgur.com/a/KdFltYE, however Github wouldn't show it as it was too big, so I uploaded it to my repo and linked it from there.

### Open-source libraries used

- [Android Async HTTP](https://github.com/codepath/CPAsyncHttpClient) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Androids
