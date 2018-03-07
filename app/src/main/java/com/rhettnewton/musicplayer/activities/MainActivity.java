package com.rhettnewton.musicplayer.activities;

import android.Manifest;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rhettnewton.musicplayer.R;
import com.rhettnewton.musicplayer.adapters.MusicCollectionPagerAdapter;
import com.rhettnewton.musicplayer.fragments.AlbumsFragment;
import com.rhettnewton.musicplayer.fragments.ArtistsFragment;
import com.rhettnewton.musicplayer.fragments.SongsFragment;

public class MainActivity extends AppCompatActivity implements
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
        mAdapter = new MusicCollectionPagerAdapter(this, getSupportFragmentManager());

        getPermissionsThenLoadContent();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void hideNeedsPermissionsMessage() {
        TextView permissionsMessageTextView = (TextView) findViewById(R.id.tv_permissions_message);
        permissionsMessageTextView.setVisibility(View.INVISIBLE);
    }

    private void loadPages() {
        hideNeedsPermissionsMessage();
        mViewPager.setAdapter(mAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mainTabLayout);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 252;
    public void getPermissionsThenLoadContent(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

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
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadPages();
                }
            }
        }
    }
}
