package com.idw.project.cataloguemovie.config;

import com.idw.project.cataloguemovie.BuildConfig;

public class Config {
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String ENDPOINT = "https://api.themoviedb.org/3/";
    public static final String EN_LANGUAGE = "en-US";
    private static final String IMAGE_ENDPOINT = "https://image.tmdb.org/t/p/";
    public static final String IMAGE_W342 = IMAGE_ENDPOINT+"w342/";
    public static final String IMAGE_W500 = IMAGE_ENDPOINT+"w500/";
    public static final String VIDEO_URL = "https://www.youtube.com/embed/";

}
