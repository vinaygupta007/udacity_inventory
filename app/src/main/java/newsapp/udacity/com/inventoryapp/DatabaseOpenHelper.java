package newsapp.udacity.com.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static DatabaseOpenHelper databaseOpenHelper;

    public DatabaseOpenHelper(Context context) {
        super(context, DatabaseInventoryContract.DatabaseColoumnEntry.KEY_DATABASE_NAME, null, DatabaseInventoryContract.DatabaseColoumnEntry.KEY_DATABASE_VERSION_CODE);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_NAME + "("
                + DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_KEY_ID + " INTEGER PRIMARY KEY,"
                + DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_PRODUCT_NAME + " TEXT,"
                + DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_PRICE + " TEXT ,"
                + DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_QUANTITY + " INTEGER ,"
                + DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_SUPPLIER_NAME + " TEXT ,"
                + DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_SUPPIER_PHONE_NUMBER + " TEXT "+ ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*section which will run when you update/change in database afetr user installed the application.*/
    }
}
