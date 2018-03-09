package com.rhettnewton.musicplayer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Media;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rhettnewton.musicplayer.R;
import com.rhettnewton.musicplayer.utils.TimeUtils;
import com.rhettnewton.musicplayer.utils.UriUtils;

/**
 * Created by Rhett on 2/25/2018.
 */

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder> {

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
            Media.DATA,
            Media.DURATION
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
    public static final int INDEX_SONG_DURATION = 12;

    public static final int VIEW_TYPE_ALBUM = 0;
    public static final int VIEW_TYPE_PLAYLIST = 1;

    private Context mContext;
    private Cursor mCursor;
    private SongListAdapterOnClickHandler mClickHandler;
    private int mViewType;

    public interface SongListAdapterOnClickHandler {
        void onClick(String songId);
    }

    public SongListAdapter(Context context, SongListAdapterOnClickHandler clickHandler, int viewType) {
        mContext = context;
        mClickHandler = clickHandler;
        mViewType = viewType;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutId;

        switch(mViewType) {
            case VIEW_TYPE_ALBUM:
                layoutId = R.layout.song_item_album;
                break;
            case VIEW_TYPE_PLAYLIST:
                layoutId = R.layout.song_item_playlist;
                break;
            default: throw new IllegalArgumentException("Invalid view type, value of " + mViewType);
        }

        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
        view.setFocusable(true);

        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            if (mViewType == VIEW_TYPE_ALBUM) {
                holder.mSongTitle.setText(mCursor.getString(INDEX_TITLE));
                String duration = TimeUtils.convertMillisToMinutesSeconds(Long.parseLong(mCursor.getString(INDEX_SONG_DURATION)));
                holder.mSongDuration.setText(duration);
                holder.mTrackNum.setText(String.valueOf(position + 1));
            } else if (mViewType == VIEW_TYPE_PLAYLIST) {
                holder.mSongTitle.setText(mCursor.getString(INDEX_TITLE));
                holder.mArtistName.setText(mCursor.getString(INDEX_ARTIST));
                Glide.with(mContext).load(UriUtils.getAlbumArtUri(mCursor.getString(INDEX_ALBUM_ID))).into(holder.mAlbumArt);
            }
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
        private TextView mTrackNum;
        private TextView mSongDuration;
        private ImageView mAlbumArt;

        private SongViewHolder(View itemView) {
            super(itemView);
            mSongTitle = itemView.findViewById(R.id.tv_song_title);
            mArtistName = itemView.findViewById(R.id.tv_song_artist);
            mAlbumArt = itemView.findViewById(R.id.iv_album_art);
            mTrackNum = itemView.findViewById(R.id.tv_track_num);
            mSongDuration = itemView.findViewById(R.id.tv_song_duration);
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
