package newsapp.udacity.com.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InventoryAdapter extends CursorAdapter {

    private List<Inventpory_GetterSetter> invnetoryList;

    /*@BindView(R.id.productnametv)
    TextView productNameTv;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.quantity_tv)
    TextView quantityTv;
    @BindView(R.id.suppliername_tv)
    TextView supplierNameTv;
    @BindView(R.id.supplierphoneno_tv)
    TextView supplierPhoneNumberTv;*/

    public InventoryAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       View view = LayoutInflater.from(context).inflate(R.layout.layout_inventory, parent, false);

//       ButterKnife.bind(this, view);
       return view;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Log.d("**view", "called");
        TextView productNameTv = view.findViewById(R.id.productnametv);
        TextView priceTv = view.findViewById(R.id.price_tv);
        TextView quantityTv = view.findViewById(R.id.quantity_tv);
        TextView supplierNameTv = view.findViewById(R.id.suppliername_tv);
        TextView supplierPhoneNumberTv = view.findViewById(R.id.supplierphoneno_tv);



        Inventpory_GetterSetter inventpory_getterSetter = Inventpory_GetterSetter.Inventory_GetterSetter(cursor);

        productNameTv.setText(inventpory_getterSetter.getProductName());
        priceTv.setText("$" +inventpory_getterSetter.getPrice());
        quantityTv.setText(""+ inventpory_getterSetter.getQuantity());
        supplierNameTv.setText(inventpory_getterSetter.getSupplierName());
        supplierPhoneNumberTv.setText(inventpory_getterSetter.getSupplierPhoneNumber());
    }

}
