package com.example.tmorgan2.csc415project;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by tmorgan2 on 12/7/2017.
 */

public class SkillListProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.tmorgan2.csc415project";

    public static final int NO_MATCH = -1;
    public static final int ALL_SKILLS_URI = 0;
    public static final int SINGLE_TASK_URI = 1;

    private SkillsDB db;
    private UriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
        db = new SkillsDB(getContext());

        uriMatcher = new UriMatcher(NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "skills", ALL_SKILLS_URI);
        uriMatcher.addURI(AUTHORITY, "skills/#", SINGLE_TASK_URI);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case ALL_SKILLS_URI:
                return "vnd.android.cursor.dir/vnd.example.tmorgan2.csc415project.skills";
            case SINGLE_TASK_URI:
                return "vnd.android.cursor.item/vnd.example.tmorgan2.csc415project.skills";
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported.");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (uriMatcher.match(uri))
        {
            case ALL_SKILLS_URI:
                long insertID = db.insert(contentValues);
                getContext().getContentResolver().notifyChange(uri, null);
                return uri.buildUpon().appendPath(Long.toString(insertID)).build();
            default:
                throw new UnsupportedOperationException("URI: " + uri + " is not supported.");
        }

    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        int deleteCount;
        switch (uriMatcher.match(uri)) {
            case SINGLE_TASK_URI:
                String id = uri.getLastPathSegment();
                String where2 = "_id = ?";
                String[] whereArgs2 = {id};
                deleteCount = db.delete(where2, whereArgs2);
                getContext()
                        .getContentResolver().notifyChange(uri, null);
                return deleteCount;
            case ALL_SKILLS_URI:
                deleteCount = db.delete(where, whereArgs);
                getContext()
                        .getContentResolver().notifyChange(uri, null);
                return deleteCount;
            default:
                throw new UnsupportedOperationException(
                        "URI " + uri + " is not supported.");
        }
    }


    @Override
    public int update(Uri uri,  ContentValues contentValues, String where, String[] whereArgs) {
        int updateCount;

        switch (uriMatcher.match(uri))
        {
            case SINGLE_TASK_URI:
                String id = uri.getLastPathSegment();
                String where2 = "_id = ?";
                String[] whereArgs2 = { id };
                updateCount = db.update(contentValues, where2, whereArgs2);
                getContext().getContentResolver().notifyChange(uri, null);
                return updateCount;
            case ALL_SKILLS_URI:
                updateCount = db.update(contentValues, where, whereArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return updateCount;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported");

        }
    }
}
