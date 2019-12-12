package com.idw.project.cataloguemovie.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idw.project.cataloguemovie.model.TvShowVideos;

import java.util.List;

public class ResponseTvShowVideos {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<TvShowVideos> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TvShowVideos> getResults() {
        return results;
    }

    public void setResults(List<TvShowVideos> results) {
        this.results = results;
    }

}
