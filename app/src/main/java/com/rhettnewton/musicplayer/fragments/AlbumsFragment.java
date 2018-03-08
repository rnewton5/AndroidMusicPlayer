package com.rhettnewton.musicplayer.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumsFragment newInstance(String param1, String param2) {
        AlbumsFragment fragment = new AlbumsFragment();
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
    public void onClick(String albumId, String albumName) {
        // TODO: Launch explict intent for AlbumViewActivity
        Log.d("AlbumsFragment", "Album Item clicked with id: " + albumId);
        Intent intent = new Intent(getActivity(), AlbumViewActivity.class);
        intent.putExtra(AlbumViewActivity.EXTRA_ALBUM_ID, albumId);
        intent.putExtra(AlbumViewActivity.EXTRA_ALBUM_NAME, albumName);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("AlbumsFragment", "Albums loader created");
        return new CursorLoader(
                getContext(),
                AlbumListAdapter.CONTENT_URI,
                AlbumListAdapter.MAIN_ALBUM_PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("AlbumsFragment", "Albums load finished");
        mAlbumListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAlbumListAdapter.swapCursor(null);
    }
}
