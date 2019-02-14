package com.example.jakeshepherd.tamago;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class foodItem {
    private String foodName;
    private LocalDate expiryDate;
    private String foodCategory;

    public foodItem(String newFoodName, String newExpiryDate, String newFoodCategory){
        foodName = newFoodName;
        foodCategory = newFoodCategory;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        expiryDate = LocalDate.parse(newExpiryDate, formatter);
    }

    public String getFoodName(){
        return foodName;
    }

    public LocalDate getExpiryDate(){
        return expiryDate;
    }

    public String getFoodCategory(){
        return foodCategory;
    }

    public int getDaysToExpiry(){
        Period period = Period.between(LocalDate.now(), getExpiryDate());
        return period.getDays();

    }
}
