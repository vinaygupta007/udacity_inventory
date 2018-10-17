package newsapp.udacity.com.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int INVENTORY_LOADER = 1;
    Unbinder unbinder;

    @BindView(R.id.recyclerview)
    ListView recyclerView;

    private InventoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        adapter = new InventoryAdapter(this, null);
        recyclerView.setAdapter(adapter);

        getLoaderManager().initLoader(INVENTORY_LOADER, null, this);

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positin, long id) {
                Intent intent = new Intent(MainActivity.this, AddInventory_Activity.class);
                Uri contentPetUri = ContentUris.withAppendedId(DatabaseInventoryContract.DatabaseColoumnEntry.CONTENT_URI, id);

                intent.setData(contentPetUri);
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.fabbutton)
    protected void onFabButton() {

        startActivity(new Intent(this, AddInventory_Activity.class));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_KEY_ID,
                DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_PRODUCT_NAME,
                DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_PRICE,
                DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_QUANTITY,
                DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_SUPPLIER_NAME,
                DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_SUPPIER_PHONE_NUMBER
        };

        return new CursorLoader(this,
                DatabaseInventoryContract.DatabaseColoumnEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
