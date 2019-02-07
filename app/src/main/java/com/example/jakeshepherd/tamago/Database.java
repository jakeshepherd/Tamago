package com.example.jakeshepherd.tamago;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    // gonna start the primary key as just 1,2,3,4,... for now cos confusing
    private static final String DATABASE_NAME = "Food.db";
    private static final String TABLE_NAME = "Food_Table";
    private static final String COL_1 = "FOOD_NUMBER";
    private static final String COL_2 = "FOOD_NAME";
    private static final String COL_3 = "EXPIRY_DATE";


    Database(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(FOOD_NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, FOOD_NAME TEXT, EXPIRY_DATE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String foodName, String expiryDate){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, foodName);
        contentValues.put(COL_3, expiryDate);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    /**
     * Collects all data from database
     * @return
     *      Return cursor containing data from database
     */
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return sqLiteDatabase.rawQuery(query, null);
    }

    /**
     * Update database, depending on the food number requested
     * @param foodNum
     *      Food to be updated
     * @param foodName
     *      New food name
     * @param expiryDate
     *      New expiry date
     * @return
     */
    public boolean updateData(String foodNum, String foodName, String expiryDate){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, foodNum);
        contentValues.put(COL_2, foodName);
        contentValues.put(COL_3, expiryDate);

        sqLiteDatabase.update(TABLE_NAME, contentValues, "FOOD_NUMBER = ?", new String[] {foodNum});

        return true;
    }

    public Integer deleteRowData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "BILL_NUMBER = ?", new String[] {id});
    }


}
