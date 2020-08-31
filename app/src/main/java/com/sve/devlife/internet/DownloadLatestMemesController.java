package com.sve.devlife.internet;

import com.sve.devlife.model.Memes;

import retrofit2.Call;
import retrofit2.Callback;

public class DownloadLatestMemesController {

    public void start(int page, Callback<Memes> callback) {
        Call<Memes> call = ApiImpl.getMemeApi().downloadLatestMemes(page);
        call.enqueue(callback);
    }
}