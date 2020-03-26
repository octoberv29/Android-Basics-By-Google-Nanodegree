package com.example.android.inventoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ProductContract.ProductEntry;
import com.example.android.inventoryapp.data.ProductDbHelper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Allows user to create a new product or edit an existing one.
 */
public class EditActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private final static int EXISTING_PRODUCT_LOADER = 1;

    // UI Elements
    private TextInputEditText mNameInputEditText;
    private TextInputEditText mPriceInputEditText;
    private TextInputEditText mQuantityInputEditText;
    private TextInputEditText mSupplierInputEditText;

    private Button mAddButton;
    private Button mSubtractButton;
    private Button mOrderMoreButton;
    private Button mSaveButton;
    private Button mDeleteButton;

    private Uri mCurrentProductUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        mCurrentProductUri = intent.getData();

        mNameInputEditText = findViewById(R.id.edit_product_name);
        mPriceInputEditText = findViewById(R.id.edit_product_price);
        mQuantityInputEditText = findViewById(R.id.edit_product_quantity);
        mSupplierInputEditText = findViewById(R.id.edit_product_supplier);

        mAddButton = findViewById(R.id.button_add);
        mSubtractButton = findViewById(R.id.button_subtract);
        mOrderMoreButton = findViewById(R.id.button_order_more);
        mSaveButton = findViewById(R.id.button_edit_save);
        mDeleteButton = findViewById(R.id.button_edit_delete);

        mAddButton.setOnClickListener(this);
        mSubtractButton.setOnClickListener(this);
        mOrderMoreButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);

        if (mCurrentProductUri == null) {
            setTitle("Add a product");
            mAddButton.setEnabled(false);
            mSubtractButton.setEnabled(false);
            mOrderMoreButton.setEnabled(false);
            mDeleteButton.setText("Discard");
        } else {
            setTitle("Edit a product");
            getSupportLoaderManager().initLoader(EXISTING_PRODUCT_LOADER, null, this);
        }

    }

    // OnClickListener's method and corresponding helper methods
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_add:
                changeQuantity(1);
                break;
            case R.id.button_subtract:
                changeQuantity(-1);
                break;
            case R.id.button_order_more:
                break;
            case R.id.button_edit_save:
                saveProduct();
                finish();
                break;
            case R.id.button_edit_delete:
                deleteProduct();
                break;
        }
    }

    private void changeQuantity(int offset) {
        int currentQuantity = Integer.parseInt(String.valueOf(mQuantityInputEditText.getText()).trim());

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY, currentQuantity + offset);

        if (mCurrentProductUri != null) {
            getContentResolver().update(mCurrentProductUri, null, null, null);
        }
    }

    private void saveProduct() {

        String name = String.valueOf(mNameInputEditText.getText()).trim();
        String price = String.valueOf(mPriceInputEditText.getText()).trim();
        String quantity = String.valueOf(mQuantityInputEditText.getText()).trim();
        String supplier = String.valueOf(mSupplierInputEditText.getText()).trim();

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, name);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_PRICE, Integer.parseInt(price));
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY, Integer.parseInt(quantity));
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER, supplier);

        if (mCurrentProductUri == null) {
            Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(this, "Product saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error with saving product", Toast.LENGTH_SHORT).show();
            }
        } else {
            int updatedRows = getContentResolver().update(mCurrentProductUri, values, null, null);
            if (updatedRows > 0) {
                Toast.makeText(this, "Product saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error with saving product", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteProduct() {
        if (mCurrentProductUri == null) {
            finish();
//            AlertDialog.Builder dialogDiscard = new MaterialAlertDialogBuilder(this);
//            dialogDiscard.setTitle("DISCARD");
//            dialogDiscard.setMessage("Are you sure you want to discard all changes you made?");
//            dialogDiscard.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    if (dialog != null) {
//                        dialog.dismiss();
//                    }
//                }
//            });
//            dialogDiscard.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//            dialogDiscard.create().show();
        } else {
            AlertDialog.Builder dialogDelete = new MaterialAlertDialogBuilder(this);
            dialogDelete.setTitle("DELETE");
            dialogDelete.setMessage("Are you sure you want to delete this product?");
            dialogDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });
            dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getContentResolver().delete(mCurrentProductUri, null, null);
                    finish();
                }
            });
            AlertDialog alertDialog = dialogDelete.create();
            alertDialog.show();
        }
    }


    // CursorLoader's methods
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                ProductEntry.COLUMN_NAME_PRODUCT_PRICE,
                ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER
        };

        return new CursorLoader(
                this,
                mCurrentProductUri,
                projection,
                null,
                null,
                null
        );

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_PRODUCT_NAME));
            String price = String.valueOf(cursor.getInt(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_PRODUCT_PRICE)));
            String quantity = String.valueOf(cursor.getInt(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY)));
            String supplier = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER));

            mNameInputEditText.setText(name);
            mPriceInputEditText.setText(price);
            mQuantityInputEditText.setText(quantity);
            mSupplierInputEditText.setText(supplier);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mNameInputEditText.setText("");
        mPriceInputEditText.setText("");
        mQuantityInputEditText.setText("");
        mSupplierInputEditText.setText("");
    }

}
