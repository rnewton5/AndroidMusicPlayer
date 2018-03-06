package com.rhettnewton.musicplayer.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.rhettnewton.musicplayer.R;
import com.rhettnewton.musicplayer.adapters.MusicCollectionPagerAdapter;
import com.rhettnewton.musicplayer.fragments.AlbumsFragment;
import com.rhettnewton.musicplayer.fragments.ArtistsFragment;
import com.rhettnewton.musicplayer.fragments.SongsFragment;

public class MainActivity extends FragmentActivity implements
        ArtistsFragment.OnFragmentInteractionListener,
        SongsFragment.OnFragmentInteractionListener,
        AlbumsFragment.OnFragmentInteractionListener {

    private ViewPager mViewPager;
    private MusicCollectionPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new MusicCollectionPagerAdapter(getSupportFragmentManager());
        getPermissionsThenLoadContent();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void loadPages() {
        mViewPager.setAdapter(mAdapter);
    }

    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 252;
    public void getPermissionsThenLoadContent(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // TODO: show explanation for permission
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {
            loadPages();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadPages();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }
}
