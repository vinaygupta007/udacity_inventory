package newsapp.udacity.com.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddInventory_Activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    @BindView(R.id.tv_product_name)
    EditText productNameTv;

    @BindView(R.id.tv_price)
    EditText priceTv;

    @BindView(R.id.tv_quantity)
    TextView quantityTv;

    @BindView(R.id.tv_supplier_name)
    EditText suppplierNameTv;

    @BindView(R.id.tv_supplier_phn_no)
    EditText supplierPhoneNumberTv;

    int quantity = 0;
    Uri currentUri;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory_);

        unbinder = ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUri = getIntent().getData();

        if (currentUri == null) {
            setTitle(getString(R.string.add_inventroy));
        } else {
            setTitle(getString(R.string.edit_inv));
            getLoaderManager().initLoader(1, null, this);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();

        }
        return true;
    }

    @OnClick({R.id.add_quantity_tv, R.id.sub_quantity_tv, R.id.save_inventory_btn})
    protected void onButtonClick(View view){

        switch (view.getId()) {

            case R.id.add_quantity_tv:

                quantity++;
                updateQuantity();
                break;

            case R.id.sub_quantity_tv:

                if(quantity > 0)
                    quantity--;
                else
                    Toast.makeText(this, getResources().getString(R.string.quantityalreadyzero), Toast.LENGTH_LONG).show();

                updateQuantity();
                break;

            case R.id.save_inventory_btn:

                if(currentUri == null)
                    saveNewInventory();
                else {
                    updateInventory();
                }

                finish();

                break;
        }

    }

    protected void saveNewInventory(){
        String productName = productNameTv.getText().toString().trim();
        String price = priceTv.getText().toString().trim();
        String supplierName= suppplierNameTv.getText().toString().trim();
        String supplierPhoneumber = supplierPhoneNumberTv.getText().toString().trim();

               /* if(productName.isEmpty()){
                    productNameTv.setError(getResources().getString(R.string.emptyproductname));
                    return;
                } else if(price.isEmpty()){
                    priceTv.setError(getResources().getString(R.string.emptyprice));
                    return;
                } else if(quantity <= 0){
                    quantityTv.setError(getResources().getString(R.string.emptyquantity));
                    return;
                } else if(supplierName.isEmpty()){
                    suppplierNameTv.setError(getResources().getString(R.string.emptysuppliername));
                    return;
                } else if(supplierPhoneumber.isEmpty()){
                    supplierPhoneNumberTv.setError(getResources().getString(R.string.emptysupplierphnno));
                    return;
                }*/

        Inventpory_GetterSetter inventpory_getterSetter = new Inventpory_GetterSetter(
                productName, price, quantity, supplierName, supplierPhoneumber);

        ContentValues values = getContentValues(inventpory_getterSetter);
        Uri uri = null;
        try {
            uri = getContentResolver().insert(DatabaseInventoryContract.DatabaseColoumnEntry.CONTENT_URI, values);
        } catch (IllegalArgumentException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IllegalStateException exp){
            Toast.makeText(this, exp.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (uri == null) {
            Toast.makeText(AddInventory_Activity.this, getString(R.string.insert_fail), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(AddInventory_Activity.this, getString(R.string.inserted_successfully) + uri, Toast.LENGTH_SHORT).show();

    }

    private void updateInventory(){

        String productName = productNameTv.getText().toString().trim();
        String price = priceTv.getText().toString().trim();
        String supplierName= suppplierNameTv.getText().toString().trim();
        String supplierPhoneumber = supplierPhoneNumberTv.getText().toString().trim();

               /* if(productName.isEmpty()){
                    productNameTv.setError(getResources().getString(R.string.emptyproductname));
                    return;
                } else if(price.isEmpty()){
                    priceTv.setError(getResources().getString(R.string.emptyprice));
                    return;
                } else if(quantity <= 0){
                    quantityTv.setError(getResources().getString(R.string.emptyquantity));
                    return;
                } else if(supplierName.isEmpty()){
                    suppplierNameTv.setError(getResources().getString(R.string.emptysuppliername));
                    return;
                } else if(supplierPhoneumber.isEmpty()){
                    supplierPhoneNumberTv.setError(getResources().getString(R.string.emptysupplierphnno));
                    return;
                }*/

        Inventpory_GetterSetter inventpory_getterSetter = new Inventpory_GetterSetter(
                productName, price, quantity, supplierName, supplierPhoneumber);

        ContentValues values = getContentValues(inventpory_getterSetter);

        int rowsAffected = getContentResolver().update(currentUri, values, null, null);
        if (rowsAffected != -1) {
            Toast.makeText(AddInventory_Activity.this, getString(R.string.edit_success), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddInventory_Activity.this, getString(R.string.edit_failed),
                    Toast.LENGTH_SHORT).show();
        }

    }

    private ContentValues getContentValues(Inventpory_GetterSetter invertory_value){

        ContentValues values = new ContentValues();

        values.put(DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_PRODUCT_NAME, invertory_value.getProductName());
        values.put(DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_PRICE, invertory_value.getPrice());
        values.put(DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_QUANTITY, invertory_value.getQuantity());
        values.put(DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_SUPPLIER_NAME, invertory_value.getSupplierName());
        values.put(DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_SUPPIER_PHONE_NUMBER, invertory_value.getSupplierPhoneNumber());

        return values;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    private void updateQuantity(){
        quantityTv.setText(""+quantity);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_KEY_ID,
                DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_PRODUCT_NAME,
                DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_PRICE,
                DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_QUANTITY,
                DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_SUPPLIER_NAME,
                DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_SUPPIER_PHONE_NUMBER
        };
        return new CursorLoader(this,
                currentUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor.moveToFirst()) {

            int productNameIndex = cursor.getColumnIndex(DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_PRODUCT_NAME);
            int priceIndex= cursor.getColumnIndex(DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_PRICE);
            int stockIndex  = cursor.getColumnIndex(DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_QUANTITY);
            int supplierNameIndex = cursor.getColumnIndex(DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_SUPPLIER_NAME);
            int supplierPhoneIndex = cursor.getColumnIndex(DatabaseInventoryContract.DatabaseColoumnEntry.KEY_TABLE_SUPPIER_PHONE_NUMBER);

            quantity = stockIndex;


            productNameTv.setText(cursor.getString(productNameIndex));
            priceTv.setText(String.valueOf(cursor.getDouble(priceIndex)));
            quantityTv.setText(String.valueOf(cursor.getInt(stockIndex)));
            suppplierNameTv.setText(cursor.getString(supplierNameIndex));
            supplierPhoneNumberTv.setText(cursor.getString(supplierPhoneIndex));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
