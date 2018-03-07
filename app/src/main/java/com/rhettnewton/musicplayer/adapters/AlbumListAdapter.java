package com.rhettnewton.musicplayer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rhettnewton.musicplayer.R;

/**
 * Created by Rhett on 3/4/2018.
 */

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private AlbumListAdapterOnClickHandler mClickHandler;

    public interface AlbumListAdapterOnClickHandler {
        void onClick(String albumId);
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
            holder.mAlbumTextView.setText(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
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

    class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mAlbumTextView;

        private AlbumViewHolder(View itemView) {
            super(itemView);
            mAlbumTextView = itemView.findViewById(R.id.tv_album_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mCursor.moveToPosition(getAdapterPosition())) {
                mClickHandler.onClick(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Albums._ID)));
            }
        }
    }
}
