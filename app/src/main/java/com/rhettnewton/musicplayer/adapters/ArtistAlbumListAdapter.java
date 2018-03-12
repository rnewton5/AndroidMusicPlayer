package com.rhettnewton.musicplayer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rhettnewton.musicplayer.R;

/**
 * Created by Rhett on 3/12/2018.
 */

public class ArtistAlbumListAdapter extends RecyclerView.Adapter<ArtistAlbumListAdapter.ArtistAlbumViewHolder> {

    public static final Uri CONTENT_URI = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    public static final String[] MAIN_ALBUM_PROJECTION = {
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ALBUM_KEY,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.ALBUM_ART
    };

    public static final int INDEX_ALBUM_ID = 0;
    public static final int INDEX_ALBUM_NAME = 1;
    public static final int INDEX_ALBUM_KEY = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;
    public static final int INDEX_NUM_SONGS = 4;
    public static final int INDEX_ALBUM_ART = 5;

    private static final int VIEW_TYPE_ALL_ALBUMS = 0;
    private static final int VIEW_TYPE_ALBUM = 1;

    private Context mContext;
    private Cursor mCursor;
    private ArtistAlbumListAdapterOnClickHandler mClickHandler;

    public interface ArtistAlbumListAdapterOnClickHandler {
        void onClick(String albumId);
    }

    public ArtistAlbumListAdapter(Context context, ArtistAlbumListAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @Override
    public ArtistAlbumViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int viewId;
        switch (viewType){
            case VIEW_TYPE_ALBUM:
                viewId = R.layout.artist_album_item;
                break;
            case VIEW_TYPE_ALL_ALBUMS:
                viewId = R.layout.artist_album_all_item;
                break;
            default:
                throw new RuntimeException("NO view for viewType: " + viewType);
        }

        View view = LayoutInflater.from(mContext).inflate(viewId, viewGroup, false);

        view.setFocusable(true);

        return new ArtistAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistAlbumViewHolder holder, int position) {
        if (position != 0 && mCursor.moveToPosition(position - 1)) {
            holder.mAlbumName.setText(mCursor.getString(INDEX_ALBUM_NAME));
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
            return mCursor.getCount() + 1;
        }
        return 1;
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_ALL_ALBUMS;
        }
        return VIEW_TYPE_ALBUM;
    }

    class ArtistAlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mAlbumName;
        private TextView mNumTracks;
        private ImageView mAlbumArt;

        private ArtistAlbumViewHolder(View itemView) {
            super(itemView);
            mAlbumName = itemView.findViewById(R.id.tv_album_name);
            mNumTracks = itemView.findViewById(R.id.tv_track_num);
            mAlbumArt = itemView.findViewById(R.id.iv_album_art);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mCursor.moveToPosition(getAdapterPosition() - 1)) {
                mClickHandler.onClick(mCursor.getString(INDEX_ALBUM_ID));
            } else {
                mClickHandler.onClick(null);
            }
        }
    }
}
