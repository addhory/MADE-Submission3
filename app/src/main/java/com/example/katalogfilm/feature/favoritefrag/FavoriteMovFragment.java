package com.example.katalogfilm.feature.favoritefrag;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.katalogfilm.R;
import com.example.katalogfilm.adapter.FvAdapter;
import com.example.katalogfilm.data.db.FvMovieHelper;
import com.example.katalogfilm.data.entity.Movie;
import com.example.katalogfilm.feature.nowplayingfrag.NowPlayingPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.katalogfilm.data.db.FvDatabaseContract.CONTENT_URI;

public class FavoriteMovFragment extends Fragment {

    private NowPlayingPresenter presenter;
    private FvAdapter fvAdapter;
    private ArrayList<Movie> movieList = new ArrayList<>();
    private FvMovieHelper fvMovieHelper;

    @BindView(R.id.swipeRefreshFav) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_listFav)RecyclerView recyclerView;
    @BindView(R.id.tvFav)
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_frag, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        fvMovieHelper=new FvMovieHelper(getContext());
        fvMovieHelper.open();

        fvAdapter = new FvAdapter(getContext());


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadMovieAsync().execute();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieAsync().execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
            textView.setVisibility(View.INVISIBLE);
            if (movieList.size() > 0){
                movieList.clear();
            }
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }
        @Override
        protected void onPostExecute(Cursor mv) {
            super.onPostExecute(mv);
            swipeRefreshLayout.setRefreshing(false);
            textView.setVisibility(View.INVISIBLE);

            Cursor list = mv;
            fvAdapter.setListMv(list);
            fvAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(fvAdapter);
            int count = 0;
            try {
                count = ((list.getCount() > 0) ? list.getCount() : 0);
            } catch (Exception e) {
                Log.w("ERROR", e.getMessage());
            }
            if (count == 0) {
                textView.setVisibility(View.VISIBLE);
            }
        }
    }


}
