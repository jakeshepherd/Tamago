package com.example.jakeshepherd.tamago;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    // gonna start the primary key as just 1,2,3,4,... for now cos confusing
    private static final String DATABASE_NAME = "Food.db";
    private static final String TABLE_NAME = "Food_Table";
    private static final String COL_1 = "FOOD_NUMBER";
    private static final String COL_2 = "FOOD_NAME";
    private static final String COL_3 = "EXPIRY_DATE";

    //Commenting out all foodCategory lines
    //private static final String COL_4 = "FOOD_CATEGORY";


    Database(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table  " + TABLE_NAME);

        //This line works
        db.execSQL("create table " + TABLE_NAME + "(FOOD_NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, FOOD_NAME TEXT, EXPIRY_DATE TEXT)");
        //This line doesn't (but is what we need to!)
        //db.execSQL("create table " + TABLE_NAME + "(FOOD_NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, FOOD_NAME TEXT, EXPIRY_DATE TEXT, FOOD_CATEGORY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*
    public boolean insertData(String foodName, String expiryDate){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, foodName);
        contentValues.put(COL_3, expiryDate);
        contentValues.put(COL_4)

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
    */

    public boolean insertDataFromObject(foodItem toAdd){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, toAdd.getFoodName());
        contentValues.put(COL_3, toAdd.getExpiryDate().toString());

        //contentValues.put(COL_4, toAdd.getFoodCategory());

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
     * @param toUpdate
     *       Item to be updated
     * @return
     *      true if update works
     */
    public boolean updateData(String foodNum, foodItem toUpdate){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, foodNum);
        contentValues.put(COL_2, toUpdate.getFoodName());
        contentValues.put(COL_3, toUpdate.getExpiryDate().toString());

        //contentValues.put(COL_4, toUpdate.getFoodCategory();

        sqLiteDatabase.update(TABLE_NAME, contentValues, "FOOD_NUMBER = ?", new String[] {foodNum});

        return true;
    }

    public Integer deleteRowData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "BILL_NUMBER = ?", new String[] {id});
    }

    /**
     * Get specific food name
     * @return
     *      String relating to the foodID that was requested
     */
    public String getFoodName(int foodID){
        List<String> list = new ArrayList<>();
        Cursor res = getAllData();

        if(res.moveToFirst()){
            while(!res.isAfterLast()){
                String name = res.getString(res.getColumnIndex("FOOD_NAME"));
                list.add(name);
                res.moveToNext();
            }
        }

        return list.get(foodID);
    }

    /**
     * Get all food stored in database
     * @return
     *      ArrayList containing all food in database
     */
    public List<String> getAllFoodNames(){
        List<String> list = new ArrayList<>();
        Cursor res = getAllData();

        if(res.moveToFirst()){
            while(!res.isAfterLast()){
                String name = res.getString(res.getColumnIndex("FOOD_NAME"));
                list.add(name);
                res.moveToNext();
            }
        }

        return list;
    }

    public String getExpiryDate(int foodID){
        List<String> list = new ArrayList<>();
        Cursor res = getAllData();

        if(res.moveToFirst()){
            while(!res.isAfterLast()){
                String name = res.getString(res.getColumnIndex("EXPIRY_DATE"));
                list.add(name);
                res.moveToNext();
            }
        }

        return list.get(foodID);
    }
    public List<String> getAllExpiryDates(){
        List<String> list = new ArrayList<>();
        Cursor res = getAllData();

        if(res.moveToFirst()){
            while(!res.isAfterLast()){
                String name = res.getString(res.getColumnIndex("EXPIRY_DATE"));
                list.add(name);
                res.moveToNext();
            }
        }
        return list;
    }

    /*
    public String getFoodCategory(int foodID){
        List<String> list = new ArrayList<>();
        Cursor res = getAllData();

        if(res.moveToFirst()){
            while(!res.isAfterLast()){
                String name = res.getString(res.getColumnIndex("FOOD_CATEGORY"));
                list.add(name);
                res.moveToNext();
            }
        }

        return list.get(foodID);
    }


    public List<String> getAllCategories(){
        List<String> list = new ArrayList<>();
        Cursor res = getAllData();

        if(res.moveToFirst()){
            while(!res.isAfterLast()){
                String name = res.getString(res.getColumnIndex("FOOD_CATEGORY"));
                list.add(name);
                res.moveToNext();
            }
        }
        return list;
    }

    */

    public int getFoodQuantity(int foodID){
        List<Integer> list = new ArrayList<>();
        Cursor res = getAllData();

        if(res.moveToFirst()){
            while(!res.isAfterLast()){
                int quantity = res.getColumnIndex("FOOD_QUANTITY");
                list.add(quantity);
                res.moveToNext();
            }
        }

        return list.get(foodID);

    }

    public int getNumberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return (int)count;
    }

}
