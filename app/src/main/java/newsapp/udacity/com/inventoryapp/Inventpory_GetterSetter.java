package newsapp.udacity.com.inventoryapp;

import android.database.Cursor;
import android.widget.CursorAdapter;

public class Inventpory_GetterSetter {

    private String productName, SupplierName, SupplierPhoneNumber, Price;
    private int quantity;

    public Inventpory_GetterSetter() {
    }

    public Inventpory_GetterSetter(String productName,String price, int quantity, String supplierName, String supplierPhoneNumber ) {
        this.productName = productName;
        SupplierName = supplierName;
        SupplierPhoneNumber = supplierPhoneNumber;
        Price = price;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public String getSupplierPhoneNumber() {
        return SupplierPhoneNumber;
    }

    public String getPrice() {
        return Price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public void setSupplierPhoneNumber(String supplierPhoneNumber) {
        SupplierPhoneNumber = supplierPhoneNumber;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static Inventpory_GetterSetter Inventory_GetterSetter(Cursor cursor){
        Inventpory_GetterSetter inventory = null;
        try
        {
            inventory = new Inventpory_GetterSetter();

            inventory.setProductName(cursor.getString(1));
            inventory.setPrice(cursor.getString(2));
            inventory.setQuantity(cursor.getInt(2));
            inventory.setSupplierName(cursor.getString(3));
            inventory.setSupplierPhoneNumber(cursor.getString(4));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return inventory;
    }
}
