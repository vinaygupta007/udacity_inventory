package newsapp.udacity.com.inventoryapp;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseInventoryContract {

    public static final String CONTENT_AUTHORITY = "newsapp.udacity.com.inventoryapp";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCTS = "inventory_db";

    private DatabaseInventoryContract(){}

    public static final class DatabaseColoumnEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        public final static String KEY_DATABASE_NAME = "inventory_db";
        public final static int KEY_DATABASE_VERSION_CODE = 1;

        public final static String KEY_TABLE_NAME = "inventory_table";

        public final static String KEY_TABLE_KEY_ID = BaseColumns._ID;
        public final static String KEY_TABLE_PRODUCT_NAME = "product_name";
        public final static String KEY_TABLE_PRICE = "price";
        public final static String KEY_TABLE_QUANTITY = "quantity";
        public final static String KEY_TABLE_SUPPLIER_NAME = "supplier_name";
        public final static String KEY_TABLE_SUPPIER_PHONE_NUMBER = "supplier_phone_number";
    }

}
