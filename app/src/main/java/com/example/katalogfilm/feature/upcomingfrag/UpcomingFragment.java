package com.example.katalogfilm.feature.upcomingfrag;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.katalogfilm.R;
import com.example.katalogfilm.adapter.MovieAdapter;
import com.example.katalogfilm.base.BaseFragment;
import com.example.katalogfilm.data.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class UpcomingFragment extends BaseFragment implements UpcomingView {
    private RecyclerView recyclerView;
    private UpcomingPresenter presenter;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movieList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upcoming_frag, container, false);


        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_listUp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        movieAdapter=new MovieAdapter(movieList, getActivity());
        recyclerView.setAdapter(movieAdapter);
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshUp);

        presenter = new UpcomingPresenter(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getList();
            }
        });
        if(savedInstanceState==null){
            presenter.getList();

        }else{
            movieList=savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            movieAdapter.refill(movieList);
        }
    }

    @Override
    public void showLoad() {
        swipeRefreshLayout.setRefreshing(true);
        //Toast.makeText(getContext(), "Loading..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishLoad() {
        swipeRefreshLayout.setRefreshing(false);
        //Toast.makeText(getContext(), "Finish!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showList(ArrayList<Movie> data) {
        movieList=data;
        movieAdapter.refill(movieList);
    }

    @Override
    public void noData() {
        swipeRefreshLayout.setRefreshing(false);
        movieList.clear();
        movieAdapter.notifyDataSetChanged();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getContext(), "Landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(getContext(), "Portrait", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIES, movieList);
        super.onSaveInstanceState(outState);
        Log.d("tes","dong"+outState);
    }
}
