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
    private static final String DATABASE_NAME = "Food.db";
    private static final String TABLE_NAME = "Food_Table";
    private static final String COL_1 = "FOOD_NUMBER";
    private static final String COL_2 = "FOOD_NAME";
    private static final String COL_3 = "EXPIRY_DATE";
    private static final String COL_4 = "FOOD_CATEGORY";


    Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        //This line works
        //db.execSQL("create table if not exists " + TABLE_NAME + "(FOOD_NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, FOOD_NAME TEXT, EXPIRY_DATE TEXT);");
        //This line doesn't (but is what we need to!)
        db.execSQL("create table if not exists " + TABLE_NAME + "(FOOD_NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, FOOD_NAME TEXT, EXPIRY_DATE TEXT, FOOD_CATEGORY TEXT);");
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
        contentValues.put(COL_3, toAdd.getExpiryDate().toString());
        contentValues.put(COL_4, toAdd.getFoodCategory());
        boolean temp = (sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1);
        return temp;
    }

    /**
     * Collects all data from database
     *
     * @return Return cursor containing data from database
     */
    protected Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME ;
        Cursor temp = db.rawQuery(query, null);
        return temp;
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
        contentValues.put(COL_3, toUpdate.getExpiryDate().toString());

        //contentValues.put(COL_4, toUpdate.getFoodCategory();

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
                list.add(res.getString(res.getColumnIndex("FOOD_NAME")));
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
                list.add(res.getString(res.getColumnIndex("FOOD_CATEGORY")));
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
                    list.add(Integer.parseInt(res.getString(res.getColumnIndex("FOOD_QUANTITY"))));
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
                list.add(res.getString(res.getColumnIndex("EXPIRY_DATE")));
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
        db.execSQL("create table if not exists " + TABLE_NAME + "(FOOD_NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, FOOD_NAME TEXT, EXPIRY_DATE TEXT, FOOD_CATEGORY TEXT);");
        Log.d("info", "manually created new table: " + TABLE_NAME);
    }
}
