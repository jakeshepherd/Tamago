package com.example.jakeshepherd.tamago;

//import java.time.LocalDate;
//import java.time.Period;
//import java.time.format.DateTimeFormatter;

public class foodItem {
    private String foodName;
    //private LocalDate expiryDate;
    private String foodCategory;
    private int quantity;
    private String unit;

    public foodItem(String newFoodName, String newExpiryDate, String newFoodCategory, int newQuantity, String newUnit){
        foodName = newFoodName;
        foodCategory = newFoodCategory;
        quantity = newQuantity;
        unit = newUnit;

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //expiryDate = LocalDate.parse(newExpiryDate, formatter);
    }

    public String getFoodName(){
        return foodName;
    }

    public int getExpiryDate(){
        //return expiryDate;
        return 1;
    }

    public String getFoodCategory(){
        return foodCategory;
    }

    public int getDaysToExpiry(){
        //Period period = Period.between(LocalDate.now(), getExpiryDate());
        //return period.getDays();
        return 1;
    }
}
