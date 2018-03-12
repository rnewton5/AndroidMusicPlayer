package com.rhettnewton.musicplayer.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import com.rhettnewton.musicplayer.activities.ArtistViewActivity;
import com.rhettnewton.musicplayer.adapters.ArtistListAdapter;

public class ArtistsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        ArtistListAdapter.ArtistListAdapterOnClickHandler {


    private RecyclerView mRecyclerView;
    private ArtistListAdapter mArtistListAdapter;

    private static final int ARTISTS_LOADER_ID = 955;

    public ArtistsFragment() {
        // Required empty public constructor
    }

    public static ArtistsFragment newInstance() {
        ArtistsFragment fragment = new ArtistsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artists, container, false);

        mRecyclerView = view.findViewById(R.id.rv_artists);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mArtistListAdapter = new ArtistListAdapter(getContext(), this);

        mRecyclerView.setAdapter(mArtistListAdapter);

        getActivity().getSupportLoaderManager().initLoader(ARTISTS_LOADER_ID, null, this);

        return view;
    }

    @Override
    public void onClick(String artistId, String artistName) {
        Intent intent = new Intent(getActivity(), ArtistViewActivity.class);
        intent.putExtra(ArtistViewActivity.EXTRA_ARTIST_ID, artistId);
        intent.putExtra(ArtistViewActivity.EXTRA_ARTIST_NAME, artistName);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getContext(),
                ArtistListAdapter.CONTENT_URI,
                ArtistListAdapter.MAIN_ARTIST_PROJECTION,
                null,
                null,
                ArtistListAdapter.MAIN_ARTIST_PROJECTION[ArtistListAdapter.INDEX_ARTIST_NAME] + " COLLATE NOCASE"
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mArtistListAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mArtistListAdapter.swapCursor(null);
    }
}
