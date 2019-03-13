package com.example.jakeshepherd.tamago;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
//TODO -- get this to sync properly, it almsot sort of works i think
public class SyncData extends AppCompatActivity {
    Database db;
    ShoppingList shoppingList;
    ArrayList<FoodItem> foodArray;

    SyncData(){
        db = new Database(this);
        shoppingList = ShoppingList.getInstance();
    }

    public ArrayList<FoodItem> getShoppingListData(){
         return shoppingList.getShoppingList();
    }

    public void addShoppingToDB(){
        foodArray = getShoppingListData();

        for(int i = 0; i<foodArray.size(); i++){
            if(foodArray.get(i).getFoodName().equals(db.getFoodName(i))){
                Log.d("hhee", "already exists");
            }else{
                db.insertDataFromObject(new FoodItem(foodArray.get(i).getFoodName(), foodArray.get(i).getFoodCategory(),
                        foodArray.get(i).getFoodQuantity(), foodArray.get(i).getExpiryDate()));
            }
        }
    }

}
