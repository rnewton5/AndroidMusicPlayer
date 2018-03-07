package com.rhettnewton.musicplayer.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.rhettnewton.musicplayer.R;
import com.rhettnewton.musicplayer.fragments.AlbumsFragment;
import com.rhettnewton.musicplayer.fragments.ArtistsFragment;
import com.rhettnewton.musicplayer.fragments.SongsFragment;

/**
 * Created by Rhett on 3/5/2018.
 */

public class MusicCollectionPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private static final int NUM_ITEMS = 3;

    public MusicCollectionPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
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
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.songs_page_title);
            case 1:
                return mContext.getString(R.string.albums_page_title);
            case 2:
                return mContext.getString(R.string.artists_page_title);
            default:
                return null;
        }
    }
}
