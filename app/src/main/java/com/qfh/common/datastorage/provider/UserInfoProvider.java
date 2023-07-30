package com.qfh.common.datastorage.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qfh.common.datastorage.database.UserDBHelper;

public class UserInfoProvider extends ContentProvider {
    private final static String TAG = "UserInfoProvider";
    private UserDBHelper userDB;
    private static final int USER_INFO = 1;
    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(UserInfoContent.AUTHORITIES, "/user", USER_INFO);
    }

    @Override
    public boolean onCreate() {
        userDB = UserDBHelper.getInstance(getContext(), 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        if (uriMatcher.match(uri) == USER_INFO) {
            SQLiteDatabase db = userDB.getReadableDatabase();
            db.query(UserInfoContent.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            db.close();
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) == USER_INFO) {
            SQLiteDatabase db = userDB.getWritableDatabase();
            long rowId = db.insert(UserInfoContent.TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri newUri = ContentUris.withAppendedId(UserInfoContent.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(newUri, null);
            }
            db.close();
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        if (uriMatcher.match(uri) == USER_INFO) {
            SQLiteDatabase db = userDB.getWritableDatabase();
            db.delete(UserInfoContent.TABLE_NAME, selection, selectionArgs);
            db.close();
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
