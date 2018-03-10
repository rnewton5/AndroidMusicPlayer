package com.rhettnewton.musicplayer.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.rhettnewton.musicplayer.R;
import com.rhettnewton.musicplayer.fragments.SongsFragment;

public class AlbumViewActivity extends AppCompatActivity {

    public static String EXTRA_ALBUM_ID = "com.rhettnewton.EXTRA_ALBUM_ID";
    public static String EXTRA_ALBUM_NAME = "com.rhettnewton.EXTRA_ALBUM_NAME";
    public static String EXTRA_ALBUM_ART = "com.rhettnewton.EXTRA_ALBUM_ART";

    private String mAlbumId = "-1";
    private String mAlbumName = "Album Name";
    private String mAlbumArt = "";

    private static final String DEFAULT_ALBUM_NAME = "Album";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);

        mAlbumId = getIntent().getStringExtra(EXTRA_ALBUM_ID);
        mAlbumName = getIntent().getStringExtra(EXTRA_ALBUM_NAME);
        mAlbumArt = getIntent().getStringExtra(EXTRA_ALBUM_ART);

        replaceFragment(SongsFragment.newInstance(mAlbumId,null));
        setAlbumArt();
        setToolbar();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void setToolbar() {
        if (mAlbumName.isEmpty() || mAlbumName == null) {
            mAlbumName = DEFAULT_ALBUM_NAME;
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mAlbumName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public Palette createPaletteSync(Bitmap bitmap) {
        return Palette.from(bitmap).generate();
    }

    public void setAlbumArt() {
        if (!mAlbumArt.equals("") && mAlbumArt != null) {
            Bitmap bm = BitmapFactory.decodeFile(mAlbumArt);
            ImageView image = findViewById(R.id.iv_album_art);
            image.setImageBitmap(bm);
            int color = createPaletteSync(bm).getDominantColor(getResources().getColor(R.color.colorPrimary));
            CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
            collapsingToolbarLayout.setBackgroundColor(color);
            collapsingToolbarLayout.setContentScrimColor(color);
            collapsingToolbarLayout.setStatusBarScrimColor(color);
        }
    }
}
