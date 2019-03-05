package com.example.jakeshepherd.tamago;

import java.util.ArrayList;

public class ShoppingList {
    private static ShoppingList myShoppingList = new ShoppingList();
    ArrayList<foodItem> shoppingListItems = new ArrayList<>();

    private ShoppingList(){
    }

    public static ShoppingList getInstance(){
        return myShoppingList;
    }

    public void addToShoppingList(foodItem toAdd){
        shoppingListItems.add(toAdd);
    }

    public ArrayList<foodItem> getShoppingList(){
        return shoppingListItems;
    }

    public void removeItem(foodItem foodItem){
        shoppingListItems.remove(foodItem);
    }

    public void removeByIndex(int i){
        shoppingListItems.remove(i);
    }

}
