package com.example.binlin.retrofitworkshop;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.artist_name_editText)
    protected TextInputEditText artistName;
    @BindView(R.id.song_editText)
    protected TextInputEditText songTitle;
    private LyricsFragment lyricsFragment;

    public static final String ARTIST_NAME_KEY = "artist name";
    public static final String SONG_TITLE_KEY = "song title";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.get_lyrics_button)
    protected void sumbitClicked(){
        if(artistName.getText().toString().isEmpty() || songTitle.getText().toString().isEmpty()){
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show();
        }else{
            // handle getting info from editTexts, and passing it to the fragment for on API call
            lyricsFragment = LyricsFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString(ARTIST_NAME_KEY, artistName.getText().toString());
            bundle.putString(SONG_TITLE_KEY, songTitle.getText().toString());
            lyricsFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,lyricsFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(lyricsFragment.isVisible()){
            getSupportFragmentManager().beginTransaction().remove(lyricsFragment).commit();
        }else{
            super.onBackPressed();
        }
    }
}
