package com.rhettnewton.musicplayer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
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

    public static final String[] MAIN_SONG_PROJECTION = {
            MediaStore.Audio.Media._ID,
//            Media.ARTIST_ID,
            MediaStore.Audio.Media.ARTIST,
//            Media.ARTIST_KEY,
//            Media.ALBUM_ID,
//            Media.ALBUM,
//            Media.ALBUM_KEY,
            MediaStore.Audio.Media.TITLE,
//            Media.TITLE_KEY,
//            Media.TRACK,
//            Media.YEAR,
            MediaStore.Audio.Media.DATA
    };

    private static final int INDEX_SONG_ID = 0;
    private static final int INDEX_SONG_ARTIST = 1;
    private static final int INDEX_SONG_TITLE = 2;
    private static final int INDEX_SONG_DATA = 3;

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
            holder.mSongTitle.setText(mCursor.getString(INDEX_SONG_TITLE));
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
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mSongTitle;

        private SongViewHolder(View itemView) {
            super(itemView);
            mSongTitle = itemView.findViewById(R.id.tv_song_title);
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
