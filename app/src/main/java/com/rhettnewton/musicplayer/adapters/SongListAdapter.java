package com.rhettnewton.musicplayer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Media;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rhettnewton.musicplayer.R;

/**
 * Created by Rhett on 2/25/2018.
 */

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder>  {

    public static final Uri CONTENT_URI = Media.EXTERNAL_CONTENT_URI;
    public static final String[] MAIN_SONG_PROJECTION = {
            Media._ID,
            Media.ARTIST_ID,
            Media.ALBUM_ID,
            Media.ARTIST,
            Media.ARTIST_KEY,
            Media.ALBUM,
            Media.ALBUM_KEY,
            Media.TITLE,
            Media.TITLE_KEY,
            Media.TRACK,
            Media.YEAR,
            Media.DATA
    };

    public static final int INDEX_SONG_ID = 0;
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ALBUM_ID = 2;
    public static final int INDEX_ARTIST = 3;
    public static final int INDEX_ARTIST_KEY = 4;
    public static final int INDEX_ALBUM = 5;
    public static final int INDEX_ALBUM_KEY = 6;
    public static final int INDEX_TITLE = 7;
    public static final int INDEX_TITLE_KEY = 8;
    public static final int INDEX_TRACK = 9;
    public static final int INDEX_YEAR = 10;
    public static final int INDEX_SONG_DATA = 11;

    private Context mContext;
    private Cursor mCursor;
    private SongListAdapterOnClickHandler mClickHandler;

    public interface SongListAdapterOnClickHandler {
        void onClick(String songId);
    }

    public SongListAdapter(Context context, SongListAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.song_item, viewGroup, false);

        view.setFocusable(true);

        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            holder.mSongTitle.setText(mCursor.getString(INDEX_TITLE));
            holder.mArtistName.setText(mCursor.getString(INDEX_ARTIST));
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mSongTitle;
        private TextView mArtistName;

        private SongViewHolder(View itemView) {
            super(itemView);
            mSongTitle = itemView.findViewById(R.id.tv_song_title);
            mArtistName = itemView.findViewById(R.id.tv_song_artist);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mCursor.moveToPosition(getAdapterPosition())) {
                mClickHandler.onClick(mCursor.getString(INDEX_SONG_ID));
            }
        }
    }
}
