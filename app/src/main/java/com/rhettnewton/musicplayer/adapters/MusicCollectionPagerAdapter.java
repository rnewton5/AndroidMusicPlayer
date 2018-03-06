package com.rhettnewton.musicplayer.adapters;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.rhettnewton.musicplayer.fragments.AlbumsFragment;
import com.rhettnewton.musicplayer.fragments.ArtistsFragment;
import com.rhettnewton.musicplayer.fragments.SongsFragment;

/**
 * Created by Rhett on 3/5/2018.
 */

public class MusicCollectionPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;
    private String[] titles = {
        "Songs", "Albums", "Artists"
    };

    public MusicCollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SongsFragment.newInstance(null, null);
            case 1:
                return AlbumsFragment.newInstance(null, null);
            case 2:
                return ArtistsFragment.newInstance(null, null);
            default:
                throw new RuntimeException("No Fragment at position: " + position);
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
