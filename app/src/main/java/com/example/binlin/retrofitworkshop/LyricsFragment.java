package com.example.binlin.retrofitworkshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.binlin.retrofitworkshop.MainActivity.ARTIST_NAME_KEY;
import static com.example.binlin.retrofitworkshop.MainActivity.SONG_TITLE_KEY;

public class LyricsFragment extends Fragment {

    private String baseURL = "https://api.lyrics.ovh/v1/";
    private Retrofit retrofit;
    private RetrofitMusicApiCalls retrofitMusicApiCalls;

    @BindView(R.id.lyrics_textView)
    protected TextView lyrcisTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lyrics, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static LyricsFragment newInstance() {

        Bundle args = new Bundle();

        LyricsFragment fragment = new LyricsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // Retrofit converter method. Tells retrofit how to convert the Json it gets into something we can use in our app
    private void builtRetrofit(){
        retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitMusicApiCalls = retrofit.create(RetrofitMusicApiCalls.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        // TODO get artist and title from bundle here
        String artistName = getArguments().getString(ARTIST_NAME_KEY);
        String songTitle = getArguments().getString(SONG_TITLE_KEY);
        builtRetrofit();
        // TODO Make our API call
        makeApiCall(artistName, songTitle);
    }

    private void makeApiCall(String artistName, String songTitle){
        retrofitMusicApiCalls.getSongLyrics(artistName, songTitle).enqueue(new Callback<RetrofitMusicApiCalls.SongLyrics>() {
            @Override
            public void onResponse(Call<RetrofitMusicApiCalls.SongLyrics> call, Response<RetrofitMusicApiCalls.SongLyrics> response) {
                if(response.isSuccessful()){
                    // get the info from the call, set the textView equal to the lyrics
                    lyrcisTextView.setText(response.body().getLyrics());
                }else{
                    // Call went through but data was not sent back.
                    Toast.makeText(getActivity(), "Error, Try Again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RetrofitMusicApiCalls.SongLyrics> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Hit onFailure, check API info and network connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
