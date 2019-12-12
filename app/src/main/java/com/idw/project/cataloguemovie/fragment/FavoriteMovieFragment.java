package com.idw.project.cataloguemovie.fragment;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.idw.project.cataloguemovie.LoadFavoriteMovieCallback;
import com.idw.project.cataloguemovie.R;
import com.idw.project.cataloguemovie.adapter.FavoriteMovieAdapter;
import com.idw.project.cataloguemovie.model.Movie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.CONTENT_URI_MOVIE;
import static com.idw.project.cataloguemovie.helper.MappingHelper.mapCursorToArrayListMovie;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements LoadFavoriteMovieCallback {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FavoriteMovieAdapter adapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favorite_movie, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setSubtitle("Favorite");
        }

        recyclerView = view.findViewById(R.id.rvMovies);
        progressBar = view.findViewById(R.id.progressbar);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).getContentResolver().registerContentObserver(CONTENT_URI_MOVIE, true, myObserver);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new FavoriteMovieAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadFavMovieAsync(getActivity(),  this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null ) {
                adapter.setListMovies(list);
            }


        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovies());
    }

    @Override
    public void preExecute() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void postExecute(Cursor movies) {
        progressBar.setVisibility(View.INVISIBLE);

        System.out.println("cek data dari"+movies);

        ArrayList<Movie> listMovie = mapCursorToArrayListMovie(movies);
        if (listMovie.size() > 0) {
            adapter.setListMovies(listMovie);
        } else {
            adapter.setListMovies(new ArrayList<Movie>());
            showSnackbarMessage();
        }
    }

    private static class LoadFavMovieAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavoriteMovieCallback> weakCallback;

        private LoadFavMovieAsync(Context context, LoadFavoriteMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI_MOVIE, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);

            weakCallback.get().postExecute(movies);
        }
    }

    private void showSnackbarMessage() {
        Snackbar.make(recyclerView, "Tidak ada data movie saat ini", Snackbar.LENGTH_SHORT).show();
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadFavMovieAsync(context, (LoadFavoriteMovieCallback) context).execute();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadFavMovieAsync(getActivity(),  this).execute();
    }
}
