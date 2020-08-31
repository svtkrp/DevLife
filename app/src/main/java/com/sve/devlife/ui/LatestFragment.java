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
import com.sve.devlife.internet.DownloadLatestMemesController;
import com.sve.devlife.model.Meme;
import com.sve.devlife.model.Memes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestFragment extends Fragment {

    private View mRoot;
    private TextView mTextView;
    private ImageView mImageView;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;

    private int mCurrentPage = 0;
    private Integer mCurrentNumberOnPage = 0;
    private Memes mCurrentMemes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_meme, container, false);

        mTextView = mRoot.findViewById(R.id.text_view);
        mTextView.setText("...");

        mImageView = mRoot.findViewById(R.id.image_view);
        downloadJson();

        mPreviousButton = mRoot.findViewById(R.id.previous_button);
        hidePreviousButton();
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("...");
                mImageView.setImageResource(R.drawable.ic_loading);
                mCurrentNumberOnPage--;
                if (mCurrentNumberOnPage == 0 && mCurrentPage == 0) {
                    hidePreviousButton();
                }
                if (mCurrentNumberOnPage < 0) {
                    mCurrentPage--;
                    mCurrentNumberOnPage = null;
                    downloadJson();
                } else {
                    update();
                }
            }
        });

        mNextButton = mRoot.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPreviousButton();
                mTextView.setText("...");
                mImageView.setImageResource(R.drawable.ic_loading);
                mCurrentNumberOnPage++;
                if (mCurrentNumberOnPage > mCurrentMemes.getResult().size() - 1) {
                    mCurrentPage++;
                    mCurrentNumberOnPage = 0;
                    downloadJson();
                } else {
                    update();
                }
            }
        });

        return mRoot;
    }

    private void downloadJson() {
        new DownloadLatestMemesController().start(mCurrentPage, new Callback<Memes>() {
            @Override
            public void onResponse(Call<Memes> call, Response<Memes> response) {
                if (response.isSuccessful()) {
                    mCurrentMemes = response.body();
                    if (mCurrentNumberOnPage == null)
                        mCurrentNumberOnPage = mCurrentMemes.getResult().size() - 1;
                    update();
                } else {
                    Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Memes> call, Throwable t) {
                Toast.makeText(getActivity(), "failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void update() {
        Meme meme = mCurrentMemes.getResult().get(mCurrentNumberOnPage);
        mTextView.setText(meme.getDescription());
        Glide.with(mRoot)
                .load(meme.getGifURL())
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