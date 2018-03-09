package com.rhettnewton.musicplayer.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Albums;
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
import com.rhettnewton.musicplayer.activities.AlbumViewActivity;
import com.rhettnewton.musicplayer.activities.ArtistViewActivity;
import com.rhettnewton.musicplayer.adapters.AlbumListAdapter;

public class AlbumsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AlbumListAdapter.AlbumListAdapterOnClickHandler {

    private static final String ARTIST_ID = "artist_id";

    private String mArtistId;

    private RecyclerView mRecyclerView;
    private AlbumListAdapter mAlbumListAdapter;

    private static final int ALBUM_LOADER_ID = 642;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param artistId The Id of the artist, or null for all albums.
     * @return A new instance of fragment AlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumsFragment newInstance(String artistId) {
        AlbumsFragment fragment = new AlbumsFragment();
        Bundle args = new Bundle();
        args.putString(ARTIST_ID, artistId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArtistId = getArguments().getString(ARTIST_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        mRecyclerView = view.findViewById(R.id.rv_albums);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAlbumListAdapter = new AlbumListAdapter(getContext(), this);

        mRecyclerView.setAdapter(mAlbumListAdapter);

        getActivity().getSupportLoaderManager().initLoader(ALBUM_LOADER_ID, null, this);

        return view;
    }

    @Override
    public void onClick(String albumId, String albumName, String albumArt) {
        Intent intent = new Intent(getActivity(), AlbumViewActivity.class);
        intent.putExtra(AlbumViewActivity.EXTRA_ALBUM_ID, albumId);
        intent.putExtra(AlbumViewActivity.EXTRA_ALBUM_NAME, albumName);
        intent.putExtra(AlbumViewActivity.EXTRA_ALBUM_ART, albumArt);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                getQueryUri(),
                AlbumListAdapter.MAIN_ALBUM_PROJECTION,
                null,
                null,
                AlbumListAdapter.MAIN_ALBUM_PROJECTION[AlbumListAdapter.INDEX_ALBUM_NAME]
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAlbumListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAlbumListAdapter.swapCursor(null);
    }

    private Uri getQueryUri() {
        if (mArtistId != null && !mArtistId.isEmpty()) {
            return MediaStore.Audio.Artists.Albums.getContentUri("external", Long.parseLong(mArtistId));
        }
        return AlbumListAdapter.CONTENT_URI;
    }
}
