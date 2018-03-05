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
import com.rhettnewton.musicplayer.fragments.ArtistsFragment;

/**
 * Created by Rhett on 3/4/2018.
 */

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public ArtistListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.artist_item, viewGroup, false);

        view.setFocusable(true);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            holder.mAristTextView.setText(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor == null)
            return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        private TextView mAristTextView;

        private ArtistViewHolder(View itemView) {
            super(itemView);
            mAristTextView = itemView.findViewById(R.id.tv_artist);
        }
    }
}
