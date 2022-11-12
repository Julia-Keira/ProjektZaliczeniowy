package com.example.projektzaliczeniowy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "Store";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Orders";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String DATA_FIELD = "data";
    private static final String NAME_FIELD = "name";
    private static final String ITEMS_FIELD = "items";



    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context){
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);
        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(DATA_FIELD)
                .append(" TEXT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(ITEMS_FIELD)
                .append(" TEXT) ");
        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void addOrderToDatabase(Order order){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DATA_FIELD, order.data);
        contentValues.put(NAME_FIELD, order.personalData);
        contentValues.put(ITEMS_FIELD, String.valueOf(order.items));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void populateOrderListArray(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Order.orderArrayList.clear();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if(result.getCount() != 0){
                while(result.moveToNext()){
                    String data = result.getString(2);
                    String name = result.getString(3);
                    String items = result.getString(4);
                    Order order = new Order(name, data, items);
                    Order.orderArrayList.add(order);
                }
            }
        }

    }
}
