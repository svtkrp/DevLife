package com.sve.devlife.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sve.devlife.R;
import com.sve.devlife.internet.DownloadRandomMemeController;
import com.sve.devlife.model.Meme;
import com.sve.devlife.model.Memes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sve.devlife.CurrentPreferences.getStoredMemes;
import static com.sve.devlife.CurrentPreferences.setStoredMemes;

public class RandomFragment extends Fragment {

    private View mRoot;
    private TextView mTextView;
    private ImageView mImageView;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;

    private Meme mCurrentMeme;
    private Integer mCurrentNumber = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_meme, container, false);

        mTextView = mRoot.findViewById(R.id.text_view);
        mTextView.setText("...");

        mImageView = mRoot.findViewById(R.id.image_view);
        if (mCurrentNumber > getStoredMemes(getActivity()).getResult().size() - 1) {
            downloadJson();
        } else {
            mCurrentMeme = getStoredMemes(getActivity()).getResult().get(mCurrentNumber);
            update();
        }

        mPreviousButton = mRoot.findViewById(R.id.previous_button);
        hidePreviousButton();
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("...");
                mImageView.setImageResource(R.drawable.ic_loading);
                mCurrentNumber--;
                if (mCurrentNumber == 0) {
                    hidePreviousButton();
                }
                mCurrentMeme = getStoredMemes(getActivity()).getResult().get(mCurrentNumber);
                update();
            }
        });

        mNextButton = mRoot.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPreviousButton();
                mTextView.setText("...");
                mImageView.setImageResource(R.drawable.ic_loading);
                mCurrentNumber++;
                if (mCurrentNumber > getStoredMemes(getActivity()).getResult().size() - 1) {
                    downloadJson();
                } else {
                    mCurrentMeme = getStoredMemes(getActivity()).getResult().get(mCurrentNumber);
                    update();
                }
            }
        });

        return mRoot;
    }

    private void downloadJson() {
        new DownloadRandomMemeController().start(new Callback<Meme>() {
            @Override
            public void onResponse(Call<Meme> call, Response<Meme> response) {
                if (response.isSuccessful()) {
                    mCurrentMeme = response.body();
                    Memes memes = getStoredMemes(getActivity());
                    memes.getResult().add(mCurrentMeme);
                    setStoredMemes(getActivity(), memes);
                    update();
                } else {
                    Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Meme> call, Throwable t) {
                Toast.makeText(getActivity(), "failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void update() {
        mTextView.setText(mCurrentMeme.getDescription());
        Glide.with(mRoot)
                .load(mCurrentMeme.getGifURL())
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(mImageView);
    }

    private void showPreviousButton() {
        mPreviousButton.setVisibility(View.VISIBLE);
    }

    private void hidePreviousButton() {
        mPreviousButton.setVisibility(View.GONE);
    }
}