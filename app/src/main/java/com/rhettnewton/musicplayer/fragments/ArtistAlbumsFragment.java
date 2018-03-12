package com.rhettnewton.musicplayer.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rhettnewton.musicplayer.R;
import com.rhettnewton.musicplayer.adapters.AlbumListAdapter;
import com.rhettnewton.musicplayer.adapters.ArtistAlbumListAdapter;

public class ArtistAlbumsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        ArtistAlbumListAdapter.ArtistAlbumListAdapterOnClickHandler {

    private static final String ARTIST_ID = "artist_id";

    private String mArtistId;

    private RecyclerView mRecyclerView;
    private ArtistAlbumListAdapter mArtistAlbumListAdapter;

    private static final int ARTIST_ALBUM_LOADER_ID = 3712;

    private OnArtistAlbumInteractionListener mListener;

    public interface OnArtistAlbumInteractionListener {
        void OnArtistAlbumInteraction(String albumId);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param artistId The id of the artist to get albums for
     * @return A new instance of fragment ArtistAlbumsFragment.
     */
    public static ArtistAlbumsFragment newInstance(String artistId) {
        ArtistAlbumsFragment fragment = new ArtistAlbumsFragment();
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
        View view = inflater.inflate(R.layout.fragment_artist_albums, container, false);

        mRecyclerView = view.findViewById(R.id.rv_artist_albums);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mArtistAlbumListAdapter = new ArtistAlbumListAdapter(getContext(), this);

        mRecyclerView.setAdapter(mArtistAlbumListAdapter);

        getActivity().getSupportLoaderManager().initLoader(ARTIST_ALBUM_LOADER_ID, null, this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnArtistAlbumInteractionListener) {
            mListener = (OnArtistAlbumInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(String albumId) {
        if (mListener != null) {
            mListener.OnArtistAlbumInteraction(albumId);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getContext(),
                getQueryUri(),
                ArtistAlbumListAdapter.MAIN_ALBUM_PROJECTION,
                null,
                null,
                ArtistAlbumListAdapter.MAIN_ALBUM_PROJECTION[ArtistAlbumListAdapter.INDEX_ALBUM_NAME]
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mArtistAlbumListAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mArtistAlbumListAdapter.swapCursor(null);
    }

    private Uri getQueryUri() {
        if (mArtistId != null && !mArtistId.isEmpty()) {
            return MediaStore.Audio.Artists.Albums.getContentUri("external", Long.parseLong(mArtistId));
        }
        return AlbumListAdapter.CONTENT_URI;
    }
}
