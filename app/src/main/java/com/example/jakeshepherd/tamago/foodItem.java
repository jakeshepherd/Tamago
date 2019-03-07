package com.example.jakeshepherd.tamago;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class foodItem implements Serializable {
    private String foodName;
    private LocalDate expiryDate;
    private String foodCategory;
    private int foodQuantity;

    public foodItem(String newFoodName, String newExpiryDate, String newFoodCategory){
        foodName = newFoodName;
        foodCategory = newFoodCategory;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        expiryDate = LocalDate.parse(newExpiryDate, formatter);
    }

    public foodItem(String newFoodName, int newQuantity){
        foodName = newFoodName;
        foodQuantity = newQuantity;
    }

    protected foodItem(String foodName, String foodCategory, int foodQuantity, String expiryDate){
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodQuantity = foodQuantity;
        //convert string to date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateExpiryDate = LocalDate.parse(expiryDate, formatter);
        this.expiryDate = dateExpiryDate;
    }

    protected String getFoodName(){
        return foodName;
    }

    protected String getFoodCategory(){
        return foodCategory;
    }

    protected int getFoodQuantity(){
        return foodQuantity;
    }

    protected String getExpiryDate(){
        return expiryDate.toString();
    }
}

