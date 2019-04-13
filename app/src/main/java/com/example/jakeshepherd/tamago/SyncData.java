package com.example.jakeshepherd.tamago;

import android.content.Context;

import java.util.ArrayList;

//TODO -- doesnt sync properly when you have equal amounts of items

public class SyncData{
    Database db;
    SyncData(Context context){
        db = new Database(context);
    }

    public void doSyncing(){
        ArrayList<FoodItem> shoppingListArray = ShoppingList.getInstance().getShoppingList();
        for(int i = 0; i<shoppingListArray.size(); i++){
            if(!shoppingListArray.get(i).getFoodName().toUpperCase().equals(db.getFoodName(i).toUpperCase())){
                db.insertDataFromObject(new FoodItem(shoppingListArray.get(i).getFoodName(), shoppingListArray.get(i).getFoodQuantity()));
            }else{
                i++;
            }
        }
    }
}
