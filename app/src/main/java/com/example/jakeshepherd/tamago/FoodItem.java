package com.example.jakeshepherd.tamago;

import android.util.Log;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FoodItem {
    private String foodName;
    private LocalDate expiryDate;
    private String foodCategory;

    protected FoodItem(String newFoodName, String newExpiryDate, String newFoodCategory){
        foodName = newFoodName;
        foodCategory = newFoodCategory;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try{
            expiryDate = LocalDate.parse(newExpiryDate, formatter);
        }
        catch(DateTimeParseException e){
            Log.d("error", "error parsing date: " + newExpiryDate);
        }
    }

    protected String getFoodName(){
        return foodName;
    }


    protected LocalDate getExpiryDate(){
        return expiryDate;
    }

    protected String getFoodCategory(){
        return foodCategory;
    }

    protected int getDaysToExpiry(){
        Period period = Period.between(LocalDate.now(), getExpiryDate());
        return period.getDays();
    }
}

