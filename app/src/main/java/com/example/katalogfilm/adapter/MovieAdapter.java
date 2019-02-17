package com.example.katalogfilm.adapter;

import android.content.Context;
import android.content.Intent;
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
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    ArrayList<Movie> movieList;

    public MovieAdapter(ArrayList<Movie> movieList, Context context) {
        this.context = context;
        this.movieList = movieList;
    }
    public void refill(List<Movie> items) {
        this.movieList = new ArrayList<>();
        this.movieList.addAll(items);

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.BindItem(movieList.get(i), context);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView imgPoster;
        View itemViewe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle= itemView.findViewById(R.id.tv_movie_title);
            imgPoster=itemView.findViewById(R.id.img_poster);
            tvOverview= itemView.findViewById(R.id.tv_movie_overview);
            itemViewe=itemView;
        }
        private void BindItem(final Movie movie, final Context context) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w92" + movie.getPosterPath())
                    .into(imgPoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent moveDetailMovieActivity = new Intent(context, DetailActivity.class);
                    moveDetailMovieActivity.putExtra(DetailActivity.EXTRA_MOVIE, movie);
                    moveDetailMovieActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(moveDetailMovieActivity);
                }
            });
        }

    }
}
