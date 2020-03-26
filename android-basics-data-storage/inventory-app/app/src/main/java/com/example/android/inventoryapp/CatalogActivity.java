package com.example.android.inventoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ProductContract.ProductEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Displays list of products that were entered and stored in the app.
 */

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PRODUCT_LOADER = 0;

    private ProductAdapter mCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // fab
        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fabIntent = new Intent(CatalogActivity.this, EditActivity.class);
                startActivity(fabIntent);
            }
        });

        // list view, adapter, OnItemClickListener
        ListView listView = findViewById(R.id.list_view_products);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        mCursorAdapter = new ProductAdapter(this, null);
        listView.setAdapter(mCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, EditActivity.class);
                Uri currentProductUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
                intent.setData(currentProductUri);
                startActivity(intent);
            }
        });

        // CursorLoader
        getSupportLoaderManager().initLoader(PRODUCT_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.catalog_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_dummy_data:
                addDummyProducts();
                return true;
            case R.id.delete_all_data:
                deleteAllProducts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // INSERT (CREATE)
    private void addDummyProducts() {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, "Cheese");
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_PRICE, 4);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY, 100);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER, "FoodMaster");

//        long newRowId = db.insert(ProductEntry.TABLE_NAME, null, values);
        Uri uri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);
    }

    // DELETE (DELETE)
    private void deleteAllProducts() {
        int deletedRows = getContentResolver().delete(ProductEntry.CONTENT_URI, null, null);

        if (deletedRows > 0) {
            Toast.makeText(this, "Succesfully Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nothing to Delete", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                ProductEntry.COLUMN_NAME_PRODUCT_PRICE,
                ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY,
        };
        return new CursorLoader(
                this,
                ProductEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

}
