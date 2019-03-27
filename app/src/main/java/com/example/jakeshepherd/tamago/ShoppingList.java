package com.example.jakeshepherd.tamago;

import java.util.ArrayList;

public class ShoppingList {

    private static ShoppingList myShoppingList = new ShoppingList();
    private ArrayList <FoodItem> shoppingListItems = new ArrayList <>();

    private ShoppingList(){}

    public static ShoppingList getInstance(){
        return myShoppingList;
    }

    public void addToShoppingList(FoodItem toAdd){
        shoppingListItems.add(toAdd);
    }

    public ArrayList <FoodItem> getShoppingList(){
        return shoppingListItems;
    }

    public void removeItem(FoodItem foodItem){
        shoppingListItems.remove(foodItem);
    }

    public void removeByIndex(int i){
        shoppingListItems.remove(i);
    }

}
