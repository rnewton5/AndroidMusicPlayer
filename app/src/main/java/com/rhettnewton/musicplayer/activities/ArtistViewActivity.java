package com.rhettnewton.musicplayer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rhettnewton.musicplayer.R;

public class ArtistViewActivity extends AppCompatActivity {

    public static final String EXTRA_ARTIST_ID = "extra_artist_it";
    public static final String EXTRA_ARTIST_NAME = "extra_artist_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_view);
    }
}
