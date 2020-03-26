package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;

import com.example.android.inventoryapp.data.ProductContract.ProductEntry;
import com.google.android.material.textview.MaterialTextView;

public class ProductAdapter extends CursorAdapter {

    private MaterialTextView tvQuantity;
    private Button mButtonSale;


    public ProductAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final MaterialTextView tvName = view.findViewById(R.id.text_product_name);
        MaterialTextView tvPrice = view.findViewById(R.id.text_product_price);
        tvQuantity = view.findViewById(R.id.text_product_quantity);
        mButtonSale = view.findViewById(R.id.button_sale);

        String name = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_PRODUCT_NAME));
        String price = String.valueOf(cursor.getInt(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_PRODUCT_PRICE)));
        String quantity = String.valueOf(cursor.getInt(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY)));

        tvName.setText(name);
        tvPrice.setText(price);
        tvQuantity.setText(quantity);

        mButtonSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int currentQuantity = Integer.parseInt(tvName.getText().toString());
//                tvQuantity.setText(String.valueOf(currentQuantity+1));
            }
        });
    }
}
