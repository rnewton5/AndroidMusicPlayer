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

    Context mContext;
    Cursor mCursor;

    public SongListAdapter(Context context) {
        mContext = context;
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
            holder.mSongTitle.setText(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
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

    class SongViewHolder extends RecyclerView.ViewHolder {

        private TextView mSongTitle;

        private SongViewHolder(View itemView) {
            super(itemView);
            mSongTitle = itemView.findViewById(R.id.tv_song_title);
        }
    }
}
