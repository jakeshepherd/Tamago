package com.example.jakeshepherd.tamago;

public class FoodItem {
    private String foodName, foodCategory, expiryDate;
    private int foodQuantity;

    protected FoodItem(String foodName, String foodCategory, int foodQuantity, String expiryDate){
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodQuantity = foodQuantity;
        this.expiryDate = expiryDate;
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
        return expiryDate;
    }

    /* protected int getDaysToExpiry(){
        Period period = Period.between(LocalDate.now(), getExpiryDate());
        return period.getDays();
    } */
}

