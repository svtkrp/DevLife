package com.sve.devlife.internet;

import com.sve.devlife.model.Meme;
import com.sve.devlife.model.Memes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MemeApi {

    @GET("random?json=true")
    Call<Meme> downloadRandomMeme();

    @GET("latest/{page}?json=true")
    Call<Memes> downloadLatestMemes(@Path("page") int page);

    @GET("top/{page}?json=true")
    Call<Memes> downloadTopMemes(@Path("page") int page);
}
