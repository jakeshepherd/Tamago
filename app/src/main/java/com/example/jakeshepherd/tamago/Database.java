package com.example.jakeshepherd.tamago;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    // gonna start the primary key as just 1,2,3,4,... for now cos confusing
    private static final String DATABASE_NAME = "Food.db",
            TABLE_NAME = "Food_Table",
            COL_1 = "FOOD_ID",
            COL_2 = "FOOD_NAME",
            COL_3 = "FOOD_CATEGORY",
            COL_4 = "FOOD_QUANTITY",
            COL_5 = "EXPIRY_DATE",
            typeInt = " INTEGER ",
            typeString = " TEXT ";

    Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {   // I know this pains to look at but it means it's easy to change the column names
        db.execSQL("drop table if exists " + TABLE_NAME);
        // "create table if not exists " + TABLE_NAME + "(FOOD_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        // FOOD_NAME TEXT, FOOD_CATEGORY TEXT, FOOD_QUANTITY INTEGER, EXPIRY_DATE TEXT)"
        db.execSQL("create table if not exists " + TABLE_NAME + "(" + COL_1 + typeInt + " PRIMARY KEY" +
                " AUTOINCREMENT, " + COL_2 + typeString + ", " + COL_3 + typeString + ", " +  COL_4 + typeInt
                + ", " + COL_5 + typeString + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    /**
     * Insert individual data to the database
     */

    protected boolean insertDataFromObject(FoodItem toAdd) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, toAdd.getFoodName());
        contentValues.put(COL_3, toAdd.getFoodCategory());
        contentValues.put(COL_4, toAdd.getFoodQuantity());
        contentValues.put(COL_5, toAdd.getExpiryDate());

        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }

    /**
     * Collects all data from database
     *
     * @return Return cursor containing data from database
     */
    protected Cursor getAllData() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    /**
     * Update database, depending on the food number requested
     *
     * @param foodNum  Food to be updated
     * @param toUpdate Item to be updated
     * @return true if update works
     */
    protected boolean updateData(String foodNum, FoodItem toUpdate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, foodNum);
        contentValues.put(COL_2, toUpdate.getFoodName());
        contentValues.put(COL_3, toUpdate.getFoodCategory());
        contentValues.put(COL_4, toUpdate.getFoodQuantity());
        contentValues.put(COL_5, toUpdate.getExpiryDate());

        sqLiteDatabase.update(TABLE_NAME, contentValues, "FOOD_NUMBER = ?", new String[]{foodNum});
        return true;
    }

    protected void deleteRowData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where " + COL_1 + " = " + id);
    }

    /**
     * Get specific food name
     *
     * @return String relating to the foodID that was requested
     */
    protected String getFoodName(int foodID){
        return getAllFoodNames().get(foodID);
    }

    /**
     * Get all food stored in database
     *
     * @return ArrayList containing all food in database
     */
    protected List <String> getAllFoodNames() {
        List <String> list = new ArrayList<>();
        Cursor res = getAllData();
        if (res.moveToFirst()) {
            while (!res.isAfterLast()){
                list.add(res.getString(res.getColumnIndex(COL_2)));
                res.moveToNext();
            }
        }
        return list;
    }

    protected String getFoodCategory(int foodID) {  // is this the most efficient way???
        return getAllCategories().get(foodID);
    }

    protected List <String> getAllCategories() {
        List <String> list = new ArrayList<>();
        Cursor res = getAllData();

        if (res.moveToFirst()) {
            while (!res.isAfterLast()){
                list.add(res.getString(res.getColumnIndex(COL_3)));
                res.moveToNext();
            }
        }
        return list;
    }

    protected int getFoodQuantity(int foodID) {  // is this the most efficient way???
        return getAllQuantities().get(foodID);
    }

    protected List <Integer> getAllQuantities() {
        List <Integer> list = new ArrayList<>();
        Cursor res = getAllData();
        if (res.moveToFirst()) {
            while (!res.isAfterLast()) {
                try{
                    list.add(Integer.parseInt(res.getString(res.getColumnIndex(COL_4))));
                }
                catch(NumberFormatException e){
                    Log.d("DatabaseError", "Couldn't parse string");
                    break;
                }
                res.moveToNext();
            }
        }
        return list;
    }

    protected String getFoodExpiryDate(int foodID){
        return getAllExpiryDates().get(foodID);
    }

    protected List <String> getAllExpiryDates() {
        List <String> list = new ArrayList<>();
        Cursor res = getAllData();

        if (res.moveToFirst()) {
            while (!res.isAfterLast()){
                list.add(res.getString(res.getColumnIndex(COL_5)));
                res.moveToNext();
            }
        }
        return list;
    }




    protected int getNumberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return count;
    }

    protected void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table if exists " + TABLE_NAME);
        Log.d("info", "dropped table: " + TABLE_NAME);
    }

    protected void deleteAllRows(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }

    protected void manuallyCreateTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table if not exists " + TABLE_NAME + "(" + COL_1 + typeInt + " PRIMARY KEY" +
                " AUTOINCREMENT, " + COL_2 + typeString + ", " + COL_3 + typeString + ", " +  COL_4 + typeInt
                + ", " + COL_5 + typeString + ")");
        Log.d("info", "manually created new table: " + TABLE_NAME);
    }
}
