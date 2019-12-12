package com.idw.project.cataloguemovie.viewModel;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.idw.project.cataloguemovie.config.Config;
import com.idw.project.cataloguemovie.model.Movie;
import com.idw.project.cataloguemovie.model.MovieVideos;
import com.idw.project.cataloguemovie.model.TvShow;
import com.idw.project.cataloguemovie.model.TvShowVideos;
import com.idw.project.cataloguemovie.response.ResponseMovie;
import com.idw.project.cataloguemovie.response.ResponseMovieDetail;
import com.idw.project.cataloguemovie.response.ResponseMovieSearch;
import com.idw.project.cataloguemovie.response.ResponseMovieVideos;
import com.idw.project.cataloguemovie.response.ResponseTvShow;
import com.idw.project.cataloguemovie.response.ResponseTvShowDetail;
import com.idw.project.cataloguemovie.response.ResponseTvShowSearch;
import com.idw.project.cataloguemovie.response.ResponseTvShowVideos;
import com.idw.project.cataloguemovie.rest.ApiClient;
import com.idw.project.cataloguemovie.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private ArrayList<Movie> MovieArrayList = new ArrayList<>();
    private ArrayList<TvShow> TvArrayList = new ArrayList<>();


    private ApiInterface apiService;

    private static final String API_KEY= Config.API_KEY;
    private static final String URL_VIDEO = Config.VIDEO_URL;

    //bagian movie search
    private MutableLiveData<ArrayList<Movie>> listMovieSearch = new MutableLiveData<>();
    private void setListMovieSearch(String query){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService.getSearchMovie(API_KEY, Config.EN_LANGUAGE, query).enqueue(new Callback<ResponseMovieSearch>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMovieSearch> call, @NonNull Response<ResponseMovieSearch> response) {
                System.out.println("responseIn"+response);
                if (response.isSuccessful()){

                    if (response.body() != null) {
                        if (response.body().getTotalResults() > 0){
                            MovieArrayList.addAll(response.body().getResults());
                            System.out.println(response.body().getResults().get(0).getId());
                            listMovieSearch.postValue(MovieArrayList);

                        }else {
                            Log.d("response", String.valueOf(response));
                        }
                    }

                }else{
                    Log.d("response", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMovieSearch> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.e("error", Objects.requireNonNull(t.getMessage()));
                }
            }
        });
    }

    public LiveData<ArrayList<Movie>> getListMoviesSearch(String query) {
        setListMovieSearch(query);
        return listMovieSearch;
    }

    //bagian movie
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();
    public void setListMovie(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService.getMovie(API_KEY, Config.EN_LANGUAGE).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMovie> call, @NonNull Response<ResponseMovie> response) {
                System.out.println("responseIn"+response);
                if (response.isSuccessful()){

                    if (response.body() != null) {
                        if (response.body().getTotalResults() > 0){
                            MovieArrayList.addAll(response.body().getResults());
                            System.out.println(response.body().getResults().get(0).getId());
                            listMovie.postValue(MovieArrayList);

                        }else {
                            Log.d("response", String.valueOf(response));
                        }
                    }

                }else{
                    Log.d("response", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMovie> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.e("error", Objects.requireNonNull(t.getMessage()));
                }
            }
        });
    }

    public LiveData<ArrayList<Movie>> getListMovies() {
        setListMovie();
        return listMovie;
    }

    //bagian tv search
    private MutableLiveData<ArrayList<TvShow>> listTvShowSearch = new MutableLiveData<>();
    private void setListTvShowSearch(String query){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService.getSearchTvShow(API_KEY, Config.EN_LANGUAGE, query).enqueue(new Callback<ResponseTvShowSearch>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTvShowSearch> call, @NonNull Response<ResponseTvShowSearch> response) {
                System.out.println("responseIn"+response);
                if (response.isSuccessful()){

                    if (response.body() != null) {
                        if (response.body().getTotalResults() > 0){
                            TvArrayList.addAll(response.body().getResults());
                            System.out.println(response.body().getResults().get(0).getId());
                            listTvShowSearch.postValue(TvArrayList);

                        }else {
                            Log.d("response", String.valueOf(response));
                        }
                    }

                }else{
                    Log.d("response", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTvShowSearch> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.e("error", Objects.requireNonNull(t.getMessage()));
                }
            }
        });
    }

    public LiveData<ArrayList<TvShow>> getListTvShowSearch(String query) {
        setListTvShowSearch(query);
        return listTvShowSearch;
    }

    //bagian tv
    private MutableLiveData<ArrayList<TvShow>> listTv = new MutableLiveData<>();
    public void setListTv(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService.getTv(API_KEY, Config.EN_LANGUAGE).enqueue(new Callback<ResponseTvShow>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTvShow> call, @NonNull Response<ResponseTvShow> response) {
                System.out.println("responseIn"+response);
                if (response.isSuccessful()){

                    if (response.body() != null) {
                        if (response.body().getTotalResults() > 0){
                            TvArrayList.addAll(response.body().getResults());
                            System.out.println(response.body().getResults().get(0).getId());
                            listTv.postValue(TvArrayList);

                        }else {
                            Log.d("response", String.valueOf(response));
                        }
                    }

                }else{
                    Log.d("response", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTvShow> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.e("error", Objects.requireNonNull(t.getMessage()));
                }
            }
        });
    }

    public LiveData<ArrayList<TvShow>> getListTv() {
        setListTv();
        return listTv;
    }

    //bagian movie video

    private MutableLiveData<String> StringMovieVideo = new MutableLiveData<>();
    private void setListMovieVideo(final String id){
        System.out.println("cekId"+ id);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService.getMovieVideos(Integer.parseInt(id), API_KEY).enqueue(new Callback<ResponseMovieVideos>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMovieVideos> call, @NonNull Response<ResponseMovieVideos> response) {
                System.out.println("responseIn"+response);
                if (response.isSuccessful()){

                    if (response.body() != null) {
                        if (response.body().getResults().size()>0){
                            System.out.println("testing"+ id);
                            List<MovieVideos> movieVideos = response.body().getResults();
                            String key = movieVideos.get(0).getKey();

                            String path = URL_VIDEO+key;

                            StringMovieVideo.postValue(path);

                        }else {
                            Log.d("response", String.valueOf(response));
                        }
                    }

                }else{
                    Log.d("response", String.valueOf(response));
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseMovieVideos> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.e("error", Objects.requireNonNull(t.getMessage()));
                }
            }
        });
    }

    public LiveData<String> getListMovieVideos(String id) {
        setListMovieVideo(id);
        return StringMovieVideo;
    }

    //bagian tv video
    private MutableLiveData<String> StringTvVideo = new MutableLiveData<>();
    private void setListTvVideo(final String id){
        System.out.println("cekId"+ id);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService.getTvVideos(Integer.parseInt(id), API_KEY).enqueue(new Callback<ResponseTvShowVideos>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTvShowVideos> call, @NonNull Response<ResponseTvShowVideos> response) {
                System.out.println("responseIn"+response);
                if (response.isSuccessful()){

                    if (response.body() != null) {
                        if (response.body().getResults().size()>0){
                            List<TvShowVideos> tvVideos = response.body().getResults();
                            String key = tvVideos.get(0).getKey();

                            String path = URL_VIDEO+key;

                            StringTvVideo.postValue(path);

                        }else {
                            Log.d("response", String.valueOf(response));
                        }
                    }

                }else{
                    Log.d("response", String.valueOf(response));
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseTvShowVideos> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.e("error", Objects.requireNonNull(t.getMessage()));
                }
            }
        });
    }

    public LiveData<String> getListTvVideos(String id) {
        setListTvVideo(id);
        return StringTvVideo;
    }

    //bagian detail movie
    private MutableLiveData<ResponseMovieDetail> detailMovie = new MutableLiveData<>();

    private void setDetailMovie(final int id){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService.getMovieDetails(id, API_KEY, Config.EN_LANGUAGE).enqueue(new Callback<ResponseMovieDetail>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMovieDetail> call, @NonNull Response<ResponseMovieDetail> response) {
                System.out.println("responseIn"+response);
                if (response.isSuccessful()){

                    detailMovie.postValue(response.body());
                    if (response.body() != null) {
                        System.out.print("id nya merupakan" + response.body().getId());
                    }

                }else{
                    Log.d("response", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMovieDetail> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.e("error", Objects.requireNonNull(t.getMessage()));
                }
            }
        });
    }

    public LiveData<ResponseMovieDetail> getDetailMovie(int id) {
        setDetailMovie(id);
        return detailMovie;
    }

    //bagian detail tvshow
    private MutableLiveData<ResponseTvShowDetail> detailTv = new MutableLiveData<>();

    private void setDetailTv(final String id){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService.getTvDetails(Integer.parseInt(id), API_KEY, Config.EN_LANGUAGE).enqueue(new Callback<ResponseTvShowDetail>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTvShowDetail> call, @NonNull Response<ResponseTvShowDetail> response) {
                System.out.println("responseIn"+response);
                if (response.isSuccessful()){

                    detailTv.postValue(response.body());

                }else{
                    Log.d("response", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTvShowDetail> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.e("error", Objects.requireNonNull(t.getMessage()));
                }
            }
        });
    }

    public LiveData<ResponseTvShowDetail> getDetailTv(String id) {
        setDetailTv(id);
        return detailTv;
    }


}
