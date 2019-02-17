package com.example.katalogfilm.feature.detailmovieactivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.katalogfilm.R;
import com.example.katalogfilm.data.db.DatabaseHelper;
import com.example.katalogfilm.data.db.FvDatabaseContract;
import com.example.katalogfilm.data.db.FvMovieHelper;
import com.example.katalogfilm.data.entity.Genre;
import com.example.katalogfilm.data.entity.Movie;
import com.example.katalogfilm.data.entity.MovieItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.katalogfilm.data.db.FvDatabaseContract.CONTENT_URI;
import static com.example.katalogfilm.data.db.FvDatabaseContract.TABLE_FV;
import static com.example.katalogfilm.data.db.FvDatabaseContract.favoriteColumns.MOVIE_ID;
import static com.example.katalogfilm.data.db.FvDatabaseContract.favoriteColumns.OVERVIEW;
import static com.example.katalogfilm.data.db.FvDatabaseContract.favoriteColumns.POSTER_PATH;
import static com.example.katalogfilm.data.db.FvDatabaseContract.favoriteColumns.TITLE;

public class DetailActivity extends AppCompatActivity implements DetailView {
    public static final String EXTRA_MOVIE = "extra_movie";

    //CircleImageView imgMvDetail;
    //TextView mvTitle, mvTagline, mvRating, mvLanguage, mvDuration, mvOverview, mvGenre, mvRelease;
    DetailPresenter presenter;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout mvContent;
    Movie mv;

    @BindView(R.id.tv_detail_title_movie)TextView mvTitle;
    @BindView(R.id.tv_detail_tagline_movie)TextView mvTagline;
    @BindView(R.id.tv_rating)TextView mvRating;
    @BindView(R.id.tv_language)
    TextView mvLanguage;
    @BindView(R.id.tv_duration)TextView mvDuration;
    @BindView(R.id.tv_movie_overview_detail)TextView mvOverview;
    @BindView(R.id.tv_genre)TextView mvGenre;
    @BindView(R.id.tv_release)TextView mvRelease;
    @BindView(R.id.img_detail_movie)CircleImageView imgMvDetail;
    private Boolean isFavorite = false;
    private Uri uriDetailMovie;
    private FvMovieHelper fvMovieHelper;
    private DatabaseHelper helper;
    private static final String TABLE_NAME = TABLE_FV;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        fvMovieHelper=new FvMovieHelper(this);
        fvMovieHelper.open();
        helper=new DatabaseHelper(this);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);

        swipeRefreshLayout=findViewById(R.id.swipeRefreshDetail);
        mvContent=findViewById(R.id.movie_content);

        presenter = new DetailPresenter(this);

        mv=getIntent().getParcelableExtra(EXTRA_MOVIE);
        presenter.getData(mv.getId());
        isFavorite=checkFav();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getData(mv.getId());
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null) {
            helper.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (isFavorite) {
            menu.findItem(R.id.btn_fav).setIcon(R.drawable.ic_favorite_black_24dp);
        } else {
            menu.findItem(R.id.btn_fav).setIcon(R.drawable.ic_favorite_border_black_24dp);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.btn_fav:
                invalidateOptionsMenu();
                toggleFavorite(item);
                return true;

        }
        return false;
    }


    @Override
    public void showLoad() {
        swipeRefreshLayout.setRefreshing(true);
        mvContent.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
        this.setTitle(mv.getTitle());
        mvTitle.setText(mv.getTitle());
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w780" + mv.getPosterPath())
                .into(imgMvDetail);
    }

    @Override
    public void finishLoad() {
        swipeRefreshLayout.setRefreshing(false);
        mvContent.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showData(MovieItem movieItem) {
        mvTagline.setText(movieItem.getTagline());
        mvRating.setText(movieItem.getVote_average() + "");
        mvOverview.setText(movieItem.getOverview());

        String releaseDate = movieItem.getRelease_date();
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try{
            Date date = parser.parse(releaseDate);
            SimpleDateFormat formatter= new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault());
            String formatedDate=formatter.format(date);

            mvRelease.setText(formatedDate);
        }catch (ParseException e){
            e.printStackTrace();
        }


        mvDuration.setText(movieItem.getRuntime() + "");
        mvLanguage.setText(movieItem.getOriginal_language());
        mvGenre.setText("");
        for (Genre genre: movieItem.getGenres()){
            mvGenre.setText(mvGenre.getText() + genre.getName() +", ");
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleFavorite(MenuItem item) {
        if (isFavorite) {
            Uri uri = Uri.parse(CONTENT_URI+"/" + mv.getId());
            getContentResolver().delete(uri,null,null);
            //fvMovieHelper.delete(mv.getId());
            Toast.makeText(this,R.string.remove_fav, Toast.LENGTH_SHORT).show();
            isFavorite = false;
        } else {
            saveMovie(mv);
            //fvMovieHelper.insert(mv);
            Toast.makeText(this,R.string.add_fav, Toast.LENGTH_SHORT).show();
            item.setIcon(R.drawable.ic_favorite_border_black_24dp);
            isFavorite = true;
        }
    }

    private boolean checkFav(){
        //SQLiteDatabase sqLiteDatabase= helper.getWritableDatabase();
        //String query = "SELECT * FROM " + TABLE_FV + " WHERE movie_id = " +mv.getId();
        Uri uri = Uri.parse(CONTENT_URI+"/"+mv.getId());
        Cursor c = getContentResolver().query(uri,null,null,null,null);
        if(c!=null){
            return c.getCount()>0;
        }
        return false;

    }
    private void saveMovie(Movie movie){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_ID, movie.getId());
        contentValues.put(TITLE, movie.getTitle());
        contentValues.put(OVERVIEW, movie.getOverview());
        contentValues.put(POSTER_PATH, movie.getPosterPath());
        getContentResolver().insert(CONTENT_URI, contentValues);
    }
}