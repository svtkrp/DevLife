package com.sve.devlife.internet;

import com.sve.devlife.model.Meme;

import retrofit2.Call;
import retrofit2.Callback;

public class DownloadRandomMemeController {

    public void start(Callback<Meme> callback) {
        Call<Meme> call = ApiImpl.getMemeApi().downloadRandomMeme();
        call.enqueue(callback);
    }
}