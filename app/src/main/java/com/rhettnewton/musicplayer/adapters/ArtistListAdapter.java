package com.rhettnewton.musicplayer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Artists;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rhettnewton.musicplayer.R;

/**
 * Created by Rhett on 3/4/2018.
 */

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistViewHolder> {

    public static final Uri CONTENT_URI = Artists.EXTERNAL_CONTENT_URI;
    public static final String[] MAIN_ARTIST_PROJECTION = {
            Artists._ID,
            Artists.ARTIST,
            Artists.ARTIST_KEY,
            Artists.NUMBER_OF_ALBUMS,
            Artists.NUMBER_OF_TRACKS
    };

    public static final int INDEX_ARTIST_ID = 0;
    public static final int INDEX_ARTIST_NAME = 1;
    public static final int INDEX_ARTIST_KEY = 2;
    public static final int INDEX_ARTIST_NUM_ALBUMS = 3;
    public static final int INDEX_ARTIST_NUM_TRACKS = 4;

    private Context mContext;
    private Cursor mCursor;
    private ArtistListAdapterOnClickHandler mClickHandler;

    public interface ArtistListAdapterOnClickHandler {
        void onClick(String artistId);
    }

    public ArtistListAdapter(Context context, ArtistListAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
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
            holder.mArtistTextView.setText(mCursor.getString(INDEX_ARTIST_NAME));
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

    class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mArtistTextView;

        private ArtistViewHolder(View itemView) {
            super(itemView);
            mArtistTextView = itemView.findViewById(R.id.tv_artist);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mCursor.moveToPosition(getAdapterPosition())) {
                mClickHandler.onClick(mCursor.getString(INDEX_ARTIST_ID));
            }
        }
    }
}
