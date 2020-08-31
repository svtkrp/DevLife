package com.sve.devlife;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sve.devlife.model.Memes;

public class CurrentPreferences {

    private static final String PREF_MEMES = "memes";

    public static Memes getStoredMemes(Context context) {
        return new Gson().fromJson(PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_MEMES, null), Memes.class);
    }

    public static void setStoredMemes(Context context, Memes memes) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_MEMES, new Gson().toJson(memes))
                .apply();
    }
}
