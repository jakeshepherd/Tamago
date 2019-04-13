package com.example.jakeshepherd.tamago;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private String foodName, expiryDate, foodCategory;
    private int foodQuantity;

    FoodItem(String newFoodName, int newQuantity){
        foodName = newFoodName;
        foodQuantity = newQuantity;
    }

    FoodItem(String foodName, String foodCategory, int foodQuantity, String expiryDate){
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodQuantity = foodQuantity;
        this.expiryDate = expiryDate;
    }

    String getFoodName(){
        return foodName;
    }

    String getFoodCategory(){
        return foodCategory;
    }

    int getFoodQuantity(){
        return foodQuantity;
    }

    String getExpiryDate(){
        return expiryDate;
    }
}

