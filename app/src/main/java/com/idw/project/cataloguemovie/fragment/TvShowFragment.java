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
import com.idw.project.cataloguemovie.activity.DetailTvShowActivity;
import com.idw.project.cataloguemovie.adapter.TvShowAdapter;
import com.idw.project.cataloguemovie.model.TvShow;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<TvShow> TvArrayList = new ArrayList<>();
    private ShimmerFrameLayout mShimmerViewContainer;
    private MainViewModel TvViewModel, mainViewModelSearch;

    private SwipeRefreshLayout swipeRefreshLayout;

    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setSubtitle("Katalog TV Show");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TvViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TvViewModel.getListTv().observe(Objects.requireNonNull(getActivity()), getListTv);
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
            if (searchManager != null) {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            }
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    TvArrayList.clear();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        mainViewModelSearch.getListTvShowSearch(query).observe(Objects.requireNonNull(getActivity()), getListTvShowSearch);
                    }

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    TvArrayList.clear();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        mainViewModelSearch.getListTvShowSearch(newText).observe(Objects.requireNonNull(getActivity()), getListTvShowSearch);
                    }

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
                        TvArrayList.clear();
                        TvViewModel.setListTv();
                    }
                }, 1000);
            }
        });
    }

    private Observer<ArrayList<TvShow>> getListTv = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> tvs) {
            if (tvs != null){
                TvArrayList = tvs;

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new TvShowAdapter(TvArrayList, new TvShowAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(TvShow item) {
                        Intent intent = new Intent(getContext(), DetailTvShowActivity.class);
                        intent.putExtra(DetailTvShowActivity.TAG, item);
                        System.out.println("id nya adalah"+ item.getId());
                        startActivity(intent);
//                        Toast.makeText(getContext(), "Klik Item"+item.getId(), Toast.LENGTH_SHORT).show();
                    }
                }));

                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }
    };

    private Observer<ArrayList<TvShow>> getListTvShowSearch = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> tvs) {
            if (tvs != null){
                TvArrayList = tvs;

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new TvShowAdapter(TvArrayList, new TvShowAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(TvShow item) {
                        Intent intent = new Intent(getContext(), DetailTvShowActivity.class);
                        intent.putExtra(DetailTvShowActivity.TAG, item);
                        System.out.println("id nya adalah"+ item.getId());
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
