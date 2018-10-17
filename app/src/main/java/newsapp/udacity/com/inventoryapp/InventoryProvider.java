package newsapp.udacity.com.inventoryapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

public class InventoryProvider extends ContentProvider {

    private static final int INVENTORY = 100;
    private static final int INVENTORY_ID = 101;

    DatabaseOpenHelper databaseOpenHelper;

    String LOG_TAG = InventoryProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {
        sUriMatcher.addURI(DatabaseInventoryContract.CONTENT_AUTHORITY, DatabaseInventoryContract.PATH_PRODUCTS, INVENTORY);
        sUriMatcher.addURI(DatabaseInventoryContract.CONTENT_AUTHORITY, DatabaseInventoryContract.PATH_PRODUCTS + "/#", INVENTORY_ID);
    }

    @Override
    public boolean onCreate() {
        databaseOpenHelper = new DatabaseOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        Cursor cursor = null;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:

                SQLiteDatabase sqLiteDatabase = databaseOpenHelper.getWritableDatabase();

                cursor = sqLiteDatabase.query(true, DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_NAME,
                        new String[]{DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_KEY_ID, DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_PRODUCT_NAME, DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_PRICE, DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_QUANTITY, DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_SUPPLIER_NAME, DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_SUPPIER_PHONE_NUMBER}
                        ,null,null, null, null, null, null);

                break;
            case INVENTORY_ID:
                selection = DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_KEY_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };



                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case INVENTORY:
                return DatabaseInventoryContract.DatabaseColoumnEntry.CONTENT_LIST_TYPE;
            case INVENTORY_ID:
                return DatabaseInventoryContract.DatabaseColoumnEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + sUriMatcher.match(uri));
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        switch (sUriMatcher.match(uri)) {
            case INVENTORY:
                return insertMobileInfo(uri,values);
                }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (sUriMatcher.match(uri)){
            case INVENTORY:
                return updateMobileInfo(uri, values, selection, selectionArgs);

            case INVENTORY_ID:
                selection = DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_KEY_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateMobileInfo(uri, values, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }

    }

    @Nullable
    private Uri insertMobileInfo(Uri uri, ContentValues contentValues) {

        SQLiteDatabase database = databaseOpenHelper.getWritableDatabase();
        long newRowId = database.insert(DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_NAME,null,contentValues);
        if (newRowId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, newRowId);
    }

    private int updateMobileInfo(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();

        int rowsUpdated = database.update(DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_NAME,contentValues,selection,selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
