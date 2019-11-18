package com.example.barcode_reader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="products.db";
    public static final String TABLE_NAME="products_table";
    public static final String COL_1="barcode_no";
    public static final String COL_2="barcode_number";
    public static final String COL_3="price";
    int id_c = 0;
     private static final String SQL_CREATE_ENTRIES="CREATE TABLE " + TABLE_NAME +
             " ( "+COL_1 + " TEXT PRIMARY KEY,"+COL_2 + " TEXT," +COL_3 + " TEXT)" ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //db.execSQL(SQL_DELETE_ENTRIES);
        //onCreate(db);
    }

    public void insert(String barcode_number, String product_name, String price)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues Values = new ContentValues();
        Values.put(COL_1, barcode_number );
        Values.put(COL_2,product_name );
        Values.put(COL_3,  price);

        long nowRowId=db.insert(TABLE_NAME , null, Values);
        db.close();
    }
    public void Delete(String barcodeP)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,COL_1+"=?",new String[]{barcodeP});
        db.close();
    }

    public ArrayList<HashMap<String, String>> GetProduct()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_NAME ;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            HashMap<String, String> products = new HashMap<>();
            products.put(COL_1, cursor.getString(cursor.getColumnIndex(COL_1)));
            products.put(COL_2, cursor.getString(cursor.getColumnIndex(COL_2)));
            products.put(COL_3, cursor.getString(cursor.getColumnIndex(COL_3)));
            productList.add(products);
        }
        cursor.close();
        db.close();
        return productList;

    }


    // Get product Details based on barcodeNumber
    public ArrayList<HashMap<String, String>> GetProductByBarcode(String barcodeN)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> PList = new ArrayList<>();
        System.out.println(db.isOpen() + barcodeN );

        //String query = "SELECT * FROM " + TABLE_NAME + " WHERE  barcode_number = 'kk' ";
        //Cursor cursor = db.rawQuery(query, null);
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL_1, COL_2, COL_3},
                COL_1 + "=?",new String[]{barcodeN}, null, null, null, null);

        while (cursor.moveToNext()) {
            System.out.println("oooooooooooooooooooooooooooooo");
            HashMap<String, String> products = new HashMap<>();
            products.put(COL_1, cursor.getString(cursor.getColumnIndex(COL_1)));
            products.put(COL_2, cursor.getString(cursor.getColumnIndex(COL_2)));
            products.put(COL_3, cursor.getString(cursor.getColumnIndex(COL_3)));
            PList.add(products);
        }
        return PList;

    }
}
