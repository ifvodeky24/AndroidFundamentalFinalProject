package com.idw.project.cataloguemovie.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.idw.project.cataloguemovie.viewModel.MainViewModel;
import com.idw.project.cataloguemovie.R;
import com.idw.project.cataloguemovie.activity.DetailMovieActivity;
import com.idw.project.cataloguemovie.adapter.MovieAdapter;
import com.idw.project.cataloguemovie.model.Movie;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Movie> MovieArrayList = new ArrayList<>();
    private ShimmerFrameLayout mShimmerViewContainer;
    private MainViewModel mainViewModel, mainViewModelSearch;

    private SwipeRefreshLayout swipeRefreshLayout;

    private SearchView searchView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setSubtitle("Katalog Movie");
        }


        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mainViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mainViewModel.getListMovies().observe(Objects.requireNonNull(getActivity()), getListMovies);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mainViewModelSearch = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        }

        setHasOptionsMenu(true);

        initView(view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);

        SearchManager searchManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
        }

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null) {
            assert searchManager != null;
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean onQueryTextSubmit(String query) {
                    MovieArrayList.clear();
                    mainViewModelSearch.getListMoviesSearch(query).observe(Objects.requireNonNull(getActivity()), getListMoviesSearch);

                    return true;
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean onQueryTextChange(String newText) {
                    MovieArrayList.clear();
                    mainViewModelSearch.getListMoviesSearch(newText).observe(Objects.requireNonNull(getActivity()), getListMoviesSearch);

                    return true;
                }
            });
        }

    }

    private void initView(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);
        refresh();
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        MovieArrayList.clear();
                        mainViewModel.setListMovie();
                    }
                }, 1000);
            }
        });
    }

    private Observer<ArrayList<Movie>> getListMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if (movies != null){
                MovieArrayList = movies;

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new MovieAdapter(MovieArrayList, new MovieAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Movie item) {
                        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                        intent.putExtra(DetailMovieActivity.TAG, item);
                        startActivity(intent);
                    }
                }));

                mShimmerViewContainer.stopShimmerAnimation();

                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }
    };

    private Observer<ArrayList<Movie>> getListMoviesSearch = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if (movies != null){
                MovieArrayList = movies;

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new MovieAdapter(MovieArrayList, new MovieAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Movie item) {
                        Intent intent = new Intent(getContext(), DetailMovieActivity.class);
                        intent.putExtra(DetailMovieActivity.TAG, item);
                        startActivity(intent);
                    }
                }));
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mShimmerViewContainer.stopShimmerAnimation();

    }


}
