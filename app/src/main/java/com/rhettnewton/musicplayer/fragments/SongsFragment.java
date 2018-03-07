package com.rhettnewton.musicplayer.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private boolean mPermissionsGranted = false;
    private RecyclerView mRecyclerView;
    private SongListAdapter mSongListAdapter;

    private static final int SONG_LOADER_ID = 368;

    public SongsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SongsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongsFragment newInstance(String param1, String param2) {
        SongsFragment fragment = new SongsFragment();
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
        // TODO: Launch explict intent for AlbumViewActivity
        Log.d("SongsFragment", "Song Item clicked with id: " + songId);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        switch (loaderId) {
            case SONG_LOADER_ID:
                String selection = "is_music=1";
                return new CursorLoader(
                        getContext(),
                        Media.EXTERNAL_CONTENT_URI,
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
