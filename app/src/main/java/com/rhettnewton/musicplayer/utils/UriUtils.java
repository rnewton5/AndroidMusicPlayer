package com.rhettnewton.musicplayer.utils;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Rhett on 3/9/2018.
 */

public class UriUtils {

    public static Uri getAlbumArtUri(String albumId) {
        Uri artworkUri = Uri.parse("content://media/external/audio/albumart");
        return ContentUris.withAppendedId(artworkUri, Long.parseLong(albumId));
    }
}
