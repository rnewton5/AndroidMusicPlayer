package com.rhettnewton.musicplayer.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rhettnewton.musicplayer.R;
import com.rhettnewton.musicplayer.adapters.SongListAdapter;
import com.rhettnewton.musicplayer.fragments.SongsFragment;

public class AlbumViewActivity extends AppCompatActivity {

    public static String EXTRA_ALBUM_ID = "com.rhettnewton.EXTRA_ALBUM_ID";
    public static String EXTRA_ALBUM_NAME = "com.rhettnewton.EXTRA_ALBUM_NAME";
    public static String EXTRA_ALBUM_ART = "com.rhettnewton.EXTRA_ALBUM_ART";

    private String albumId = "-1";
    private String albumName = "Album Name";
    private String albumArt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);
        
        albumId = getIntent().getStringExtra(EXTRA_ALBUM_ID);
        albumName = getIntent().getStringExtra(EXTRA_ALBUM_NAME);
        albumArt = getIntent().getStringExtra(EXTRA_ALBUM_ART);

        replaceFragment(SongsFragment.newInstance(albumId,null));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(albumName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
