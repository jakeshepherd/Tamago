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

public class TamagoRecipeDB extends SQLiteOpenHelper {

    // gonna start the primary key as just 1,2,3,4,... for now cos confusing
    private static final String DATABASE_NAME = "tamagoDB.db",
            TABLE_NAME = "Recipes",
            COL_1 = "RecipeID",
            COL_2 = "RecipeName",
            COL_3 = "RecipeTime",
            COL_4 = "imageURL",
            COL_5 = "instructions",
            COL_6 = "ingredients",
            COL_7 = "ingNames",
            typeInt = " INTEGER",
            typeString = " TEXT";

    TamagoRecipeDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + "(" + COL_1 + typeString + " PRIMARY KEY, "
                + COL_2 + typeString + ", " + COL_3 + typeString + ", " +  COL_4 + typeString
                + ", " + COL_5 + typeString + ", " + COL_6 + typeString +", " + COL_7 + typeString +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    /**
     * Collects all data from database
     *
     * @return Return cursor containing data from database
     */
    private Cursor getAllData() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

//    /**
//     * Update database, depending on the food number requested
//     *
//     * @param foodNum  Food to be updated
//     * @param toUpdate Item to be updated
//     * @return true if update works
//     */
//    protected boolean updateData(String foodNum, FoodItem toUpdate) {
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(COL_1, foodNum);
//        contentValues.put(COL_2, toUpdate.getFoodName());
//        contentValues.put(COL_3, toUpdate.getFoodCategory());
//        contentValues.put(COL_4, toUpdate.getFoodQuantity());
//        contentValues.put(COL_5, toUpdate.getExpiryDate());
//
//        this.getWritableDatabase().update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[]{foodNum});
//        return true;
//    }

    void deleteRowData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where " + COL_1 + " = " + id);
        id++;

        while (id <= getNumberOfRows()) { // when deleting a row, decrement the lower rows' IDs
            db.execSQL("update " + TABLE_NAME + " set " + COL_1 + " = " + (id - 1) + " where " + COL_1 + " = " + id);
            id++;
        }
    }


    // This was a good debugging method so I'll leave it in for now

    /* protected List <Integer> getAllFoodIDs() {
        List <Integer> list = new ArrayList<>();
        Cursor res = getAllData();
        if (res.moveToFirst()) {
            while (!res.isAfterLast()) {
                try{
                    list.add(Integer.parseInt(res.getString(res.getColumnIndex(COL_1))));
                }
                catch(NumberFormatException e){
                    Log.d("DatabaseError", "Couldn't parse string");
                    break;
                }
                res.moveToNext();
            }
        }
        return list;
    } */

    /**
     * Get recipe name for given recipe ID
     *
     * @return String of recipe name
     */
    String getRecipeName(String RecipeID){
        String returnString;
        Cursor cursor = this.getReadableDatabase()
                .rawQuery("SELECT " + COL_2 + " FROM " + TABLE_NAME + " WHERE " + COL_1 + " = " + RecipeID, null);
        if(cursor.moveToFirst())
            returnString = cursor.getString(0);
        else returnString = "error";
        cursor.close();
        return returnString;
    }


    /**
     * Get recipe time for given recipe ID
     *
     * @return String of number of minutes needed for recipe
     */
    String getRecipeTime(String RecipeID) {
        String returnString;
        Cursor cursor = this.getReadableDatabase()
                .rawQuery("SELECT " + COL_3 + " FROM " + TABLE_NAME + " WHERE " + COL_1 + " = " + RecipeID, null);
        if(cursor.moveToFirst())
            returnString = cursor.getString(0);
        else returnString = "error";
        cursor.close();
        return returnString;
    }

    /**
     * Get recipe image URL for given recipe ID
     *
     * @return String of recipe image URL
     */
    String getImageURL(String RecipeID){
        String returnString;
        Cursor cursor = this.getReadableDatabase()
                .rawQuery("SELECT " + COL_4 + " FROM " + TABLE_NAME + " WHERE " + COL_1 + " = " + RecipeID, null);
        if(cursor.moveToFirst())
            returnString = cursor.getString(0);
        else returnString = "error";
        cursor.close();
        return returnString;
    }

    String getInstructions(String RecipeID){
        String returnString;
        Cursor cursor = this.getReadableDatabase()
                .rawQuery("SELECT " + COL_5 + " FROM " + TABLE_NAME + " WHERE " + COL_1 + " = " + RecipeID, null);
        if(cursor.moveToFirst())
            returnString = cursor.getString(0);
        else returnString = "error";
        cursor.close();
        return returnString;
    }

    /**
     * Get recipe Ingredients for given recipe ID
     *
     * @return String of recipe ingredients with measurements
     */
    String getIngredients(String RecipeID){
        String returnString;
        Cursor cursor = this.getReadableDatabase()
                .rawQuery("SELECT " + COL_6 + " FROM " + TABLE_NAME + " WHERE " + COL_1 + " = " + RecipeID, null);
        if(cursor.moveToFirst())
            returnString = cursor.getString(0);
        else returnString = "error";
        cursor.close();
        return returnString;
    }

    /**
     * Get recipe Ingredients for given recipe ID
     *
     * @return String of recipe ingredients without measurements
     */
    String getIngredientNames(String RecipeID){
        String returnString;
        Cursor cursor = this.getReadableDatabase()
                .rawQuery("SELECT " + COL_7 + " FROM " + TABLE_NAME + " WHERE " + COL_1 + " = " + RecipeID, null);
        if(cursor.moveToFirst())
            returnString = cursor.getString(0);
        else returnString = "error";
        cursor.close();
        return returnString;
    }

    /**
     * Get recipe ID for given recipe name
     *
     * @return String of recipeID
     */
    String getRecipeID(String RecipeName){
        String returnString;
        Cursor cursor = this.getReadableDatabase()
                .rawQuery("SELECT " + COL_1 + " FROM " + TABLE_NAME + " WHERE " + COL_2 + " = " + RecipeName, null);
        if(cursor.moveToFirst())
            returnString = cursor.getString(0);
        else returnString = "error";
        cursor.close();
        return returnString;
    }

    /**
     * Get recipe ID for given recipe ingredient (only works with 1 ingredient atm)
     *
     * @return String of recipeID
     */
    String getRecipeIDwithIng(String ingredient){
        String returnString;
        Cursor cursor = this.getReadableDatabase()
                .rawQuery("SELECT " + COL_1 + " FROM " + TABLE_NAME + " WHERE " + COL_7 +  " LIKE " + "'%"+ingredient+"%'", null);
        if(cursor.moveToFirst())
            returnString = cursor.getString(0);
        else returnString = "error";
        cursor.close();
        Log.d("Db Debug", returnString);
        return returnString;
    }


    int getNumberOfRows() {
        return (int) DatabaseUtils.queryNumEntries(this.getReadableDatabase(), TABLE_NAME);
    }

    protected void deleteTable(){
        this.getWritableDatabase().execSQL("drop table if exists " + TABLE_NAME);
        Log.d("info", "dropped table: " + TABLE_NAME);
    }

    protected void deleteAllRows(){
        this.getWritableDatabase().execSQL("delete from " + TABLE_NAME);
    }

    protected void manuallyCreateTable(){
        this.getWritableDatabase().execSQL("create table if not exists " + TABLE_NAME + "(" + COL_1 + typeInt +
                " PRIMARY KEY, " + COL_2 + typeString + ", " + COL_3 + typeString
                + ", " + COL_4 + typeInt + ", " + COL_5 + typeString + ")");
        Log.d("info", "manually created new table: " + TABLE_NAME);
    }
}
