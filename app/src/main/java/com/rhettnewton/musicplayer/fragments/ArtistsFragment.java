package com.rhettnewton.musicplayer.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Artists;
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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private ArtistListAdapter mArtistListAdapter;

    private static final int ARTISTS_LOADER_ID = 955;

    public ArtistsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtistsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArtistsFragment newInstance(String param1, String param2) {
        ArtistsFragment fragment = new ArtistsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
    public void onClick(String artistId) {
        // TODO: Launch explict intent for AlbumViewActivity
        Log.d("ArtistsFragment", "Artist Item clicked with id: " + artistId);
        Intent intent = new Intent(getActivity(), ArtistViewActivity.class);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getContext(),
                Artists.EXTERNAL_CONTENT_URI,
                ArtistListAdapter.MAIN_ARTIST_PROJECTION,
                null,
                null,
                null
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
