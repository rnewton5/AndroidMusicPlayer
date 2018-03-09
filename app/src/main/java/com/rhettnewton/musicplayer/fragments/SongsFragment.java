package com.rhettnewton.musicplayer.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rhettnewton.musicplayer.R;
import com.rhettnewton.musicplayer.adapters.SongListAdapter;

public class SongsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        SongListAdapter.SongListAdapterOnClickHandler {

    private static final String ALBUM_ID = "album_id";
    private static final String ARTIST_ID = "artist_id";

    private String mAlbumId;
    private String mArtistId;

    private RecyclerView mRecyclerView;
    private SongListAdapter mSongListAdapter;

    private static final int SONG_LOADER_ID = 368;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param albumId The Id of the Album, or null for all songs.
     * @param artistId The Id of the Artist, or null for all songs.
     * @return A new instance of fragment SongsFragment.
     */
    public static SongsFragment newInstance(String albumId, String artistId) {
        SongsFragment fragment = new SongsFragment();
        Bundle args = new Bundle();
        args.putString(ALBUM_ID, albumId);
        args.putString(ARTIST_ID, artistId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlbumId = getArguments().getString(ALBUM_ID);
            mArtistId = getArguments().getString(ARTIST_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_songs, container, false);

        mRecyclerView = view.findViewById(R.id.rv_song_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mSongListAdapter = new SongListAdapter(getContext(), this);

        mRecyclerView.setAdapter(mSongListAdapter);

        getActivity().getSupportLoaderManager().initLoader(SONG_LOADER_ID, null, this);

        return view;
    }

    @Override
    public void onClick(String songId) {
        Log.d("SongsFragment", "Song Item clicked with id: " + songId);
    }

    private String buildQuerySelection(){
        String selection = "is_music=1";
        if (mAlbumId != null && !mAlbumId.isEmpty()) {
            selection += " AND ";
            selection += Media.ALBUM_ID + "=" + mAlbumId;
        }
        if (mArtistId != null && !mArtistId.isEmpty()) {
            selection += " AND " ;
            selection += Media.ARTIST_ID + "=" + mArtistId;
        }
        return selection;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        switch (loaderId) {
            case SONG_LOADER_ID:
                String selection = buildQuerySelection();
                return new CursorLoader(
                        getContext(),
                        SongListAdapter.CONTENT_URI,
                        SongListAdapter.MAIN_SONG_PROJECTION,
                        selection,
                        null,
                        null
                );
            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) { mSongListAdapter.swapCursor(data); }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { mSongListAdapter.swapCursor(null); }
}
