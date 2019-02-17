package com.example.katalogfilm.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.katalogfilm.R;
import com.example.katalogfilm.data.entity.Movie;
import com.example.katalogfilm.feature.detailmovieactivity.DetailActivity;

import java.util.ArrayList;

public class FvAdapter extends RecyclerView.Adapter<FvAdapter.FvViewHolder> {
    private Cursor cursor;
    private Context context;
    private ArrayList<Movie> movieList;


    public FvAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<Movie>getListMovie(){
        return movieList;
    }
    public void setListMv(Cursor cursor) {
        this.cursor = cursor;
    }
    @NonNull
    @Override
    public FvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FvViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup ,false));

    }

    @Override
    public void onBindViewHolder(@NonNull FvViewHolder fvViewHolder, int i) {
        final Movie movie = getItem(i);
        fvViewHolder.tvTitleFv.setText(movie.getTitle());
        fvViewHolder.tvOverviewFv.setText(movie.getOverview());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w92" + movie.getPosterPath())
                .into(fvViewHolder.imgPosterFv);
        fvViewHolder.itemVieweFv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveDetailMovieActivity = new Intent(context, DetailActivity.class);
                moveDetailMovieActivity.putExtra(DetailActivity.EXTRA_MOVIE, movie);
                moveDetailMovieActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(moveDetailMovieActivity);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
        //return getListMovie().size();
    }
    private Movie getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new Movie(cursor);
    }

    public class FvViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitleFv;
        private TextView tvOverviewFv;
        private ImageView imgPosterFv;
        private View itemVieweFv;
        public FvViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleFv= itemView.findViewById(R.id.tv_movie_title);
            imgPosterFv=itemView.findViewById(R.id.img_poster);
            tvOverviewFv= itemView.findViewById(R.id.tv_movie_overview);
            itemVieweFv=itemView;
        }

    }
}
