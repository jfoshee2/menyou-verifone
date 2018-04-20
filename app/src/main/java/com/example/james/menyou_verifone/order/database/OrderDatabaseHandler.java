package com.example.james.menyou_verifone.order.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.james.menyou_verifone.order.Order;

public class OrderDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "orders.db";

    public static final String TABLE_ORDERS = "orders";
    public static final String TABLE_ITEMS = "items";

    public static final String COLUMN_ID = "order_id";

    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_ITEM_ORDER_ID = "item_order_id";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_ITEM_PRICE = "item_price";


    public OrderDatabaseHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_ORDERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY " + ");";

        String secondQuery = "CREATE TABLE " + TABLE_ITEMS + "(" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_ITEM_ORDER_ID + " INTEGER, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_ITEM_PRICE + " DECIMAL " +
        ");";

        // Create necessary tables
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(secondQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(sqLiteDatabase);
    }

    public void addOrder(Order order) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, order.getOrderNumber());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ORDERS, null, contentValues);
        db.close();
    }

    public void deleteOrder(int orderId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ORDERS + " WHERE " +
                COLUMN_ID + " =\"" + orderId + "\";");
    }

    public String toString() {
        String result = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ORDERS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getInt(c.getColumnIndex("order_id")) >= 0) {
                result += c.getInt(c.getColumnIndex("order_id"));
                result += "\n";
            }
        }

        c.close();
        return result;
    }
}