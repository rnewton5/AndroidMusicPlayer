package com.rhettnewton.musicplayer.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rhettnewton.musicplayer.R;
import com.rhettnewton.musicplayer.fragments.AlbumsFragment;
import com.rhettnewton.musicplayer.fragments.ArtistsFragment;
import com.rhettnewton.musicplayer.fragments.SongsFragment;

public class MainActivity extends AppCompatActivity implements
        SongsFragment.OnFragmentInteractionListener,
        ArtistsFragment.OnFragmentInteractionListener,
        AlbumsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadContent() {
        FragmentManager fm = getSupportFragmentManager();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
