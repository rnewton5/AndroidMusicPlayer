package com.rhettnewton.musicplayer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Albums;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rhettnewton.musicplayer.R;

/**
 * Created by Rhett on 3/4/2018.
 */

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder> {

    public static final Uri CONTENT_URI = Albums.EXTERNAL_CONTENT_URI;
    public static final String[] MAIN_ALBUM_PROJECTION = {
            Albums._ID,
            Albums.ALBUM,
            Albums.ALBUM_KEY,
            Albums.ARTIST,
            Albums.NUMBER_OF_SONGS,
            Albums.ALBUM_ART
    };

    public static final int INDEX_ALBUM_ID = 0;
    public static final int INDEX_ALBUM_NAME = 1;
    public static final int INDEX_ALBUM_KEY = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;
    public static final int INDEX_NUM_SONGS = 4;
    public static final int INDEX_ALBUM_ART = 5;

    private Context mContext;
    private Cursor mCursor;
    private AlbumListAdapterOnClickHandler mClickHandler;

    public interface AlbumListAdapterOnClickHandler {
        void onClick(String albumId, String albumName, String albumArt);
    }

    public AlbumListAdapter(Context context, AlbumListAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.album_item, viewGroup, false);

        view.setFocusable(true);

        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            holder.mAlbumName.setText(mCursor.getString(INDEX_ALBUM_NAME));
            holder.mArtistName.setText(mCursor.getString(INDEX_ALBUM_ARTIST));
            holder.mNumTracks.setText(mCursor.getString(INDEX_NUM_SONGS) + " Tracks");
            String albumArt = mCursor.getString(INDEX_ALBUM_ART);
            if (albumArt != null && !albumArt.isEmpty()) {
                Glide.with(mContext).load(mCursor.getString(INDEX_ALBUM_ART)).into(holder.mAlbumArt);
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

    class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mAlbumName;
        private TextView mArtistName;
        private TextView mNumTracks;
        private ImageView mAlbumArt;

        private AlbumViewHolder(View itemView) {
            super(itemView);
            mAlbumName = itemView.findViewById(R.id.tv_album_name);
            mArtistName = itemView.findViewById(R.id.tv_artist_name);
            mNumTracks = itemView.findViewById(R.id.tv_num_tracks);
            mAlbumArt = itemView.findViewById(R.id.iv_album_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mCursor.moveToPosition(getAdapterPosition())) {
                mClickHandler.onClick(
                        mCursor.getString(INDEX_ALBUM_ID),
                        mCursor.getString(INDEX_ALBUM_NAME),
                        mCursor.getString(INDEX_ALBUM_ART)
                );
            }
        }
    }
}
