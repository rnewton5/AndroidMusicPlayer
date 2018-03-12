package com.rhettnewton.musicplayer.activities;


import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;

import com.rhettnewton.musicplayer.R;
import com.rhettnewton.musicplayer.fragments.ArtistAlbumsFragment;
import com.rhettnewton.musicplayer.fragments.SongsFragment;

public class ArtistViewActivity extends AppCompatActivity implements
        ArtistAlbumsFragment.OnArtistAlbumInteractionListener {

    public static final String EXTRA_ARTIST_ID = "extra_artist_id";
    public static final String EXTRA_ARTIST_NAME = "extra_artist_name";

    private String mArtistId;
    private String mArtistName;

    private static final String DEFAULT_ARTIST_NAME = "Artist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_view);

        mArtistId = getIntent().getStringExtra(EXTRA_ARTIST_ID);
        mArtistName = getIntent().getStringExtra(EXTRA_ARTIST_NAME);

        replaceAlbumsFragment(ArtistAlbumsFragment.newInstance(mArtistId));
        replaceSongsFragment(SongsFragment.newInstance(null, mArtistId));

        setArtistArt();
        setToolbar();
    }

    public void replaceAlbumsFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.album_frame_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void replaceSongsFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.song_frame_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void setToolbar() {
        if (mArtistName == null || mArtistName.isEmpty()) {
            mArtistName = DEFAULT_ARTIST_NAME;
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mArtistName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public Palette createPaletteSync(Bitmap bitmap) {
        return Palette.from(bitmap).generate();
    }

    public void setArtistArt() {
        //TODO: use Glide to get and cache artist art
    }

    @Override
    public void OnArtistAlbumInteraction(Uri uri) {

    }
}
