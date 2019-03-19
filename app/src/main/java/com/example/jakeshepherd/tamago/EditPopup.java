package com.example.jakeshepherd.tamago;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class EditPopup extends AppCompatActivity {
    String date;
    TextView calenderSetter, updateName, updateCategory, updateQuantity;
    ImageView updateDate;
    Button updateCancel, updateConfirm;

    Database db = new Database(this);

    ArrayList<FoodItem> foodArray;
    ShoppingList shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_popup);

        shoppingList = ShoppingList.getInstance();

        calenderSetter = findViewById(R.id.textCalender);

        updateName = findViewById(R.id.updateFoodName);
        updateCategory = findViewById(R.id.updateFoodCategory);
        updateQuantity = findViewById(R.id.updateFoodQuantity);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.65), (int) (height * 0.53));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setElevation(20);

        setOnCLickListeners();
    }

    public void setOnCLickListeners() {
        updateDate = findViewById(R.id.updateExpirationDate);
        updateCancel = findViewById(R.id.updateCancel);
        updateConfirm = findViewById(R.id.updateConfirm);

        updateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        updateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        updateConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateName.getText().length() == 0 || updateCategory.getText().length() == 0 || updateQuantity.getText().length() == 0) {
                    if (updateName.getText().length() == 0) {
                        updateName.setError("Cannot be blank");
                    }
                    if (updateCategory.getText().length() == 0) {
                        updateCategory.setError("Cannot be blank");
                    }
                    if (updateQuantity.getText().length() == 0) {
                        updateQuantity.setError("Cannot be blank");
                    }

                } else {
                    if (Integer.parseInt(String.valueOf(updateQuantity.getText())) <= 0) {
                        updateQuantity.setError("Must be more than 0");
                    }

                    // todo-- this will be a lot better with the drop down option
                    int i = searchShoppingList(String.valueOf(updateName.getText()));

                    db.updateData(String.valueOf(i), new FoodItem(String.valueOf(updateName.getText()), String.valueOf(updateCategory.getText()),
                            Integer.parseInt(String.valueOf(updateQuantity.getText())), date));

                    startActivity(new Intent(EditPopup.this, MainActivity.class));

                }
            }
        });
    }

    public int searchShoppingList(String foodName){
        foodArray = shoppingList.getShoppingList();
        for(int i = 0; i<foodArray.size(); i++){
            if(foodArray.get(i).getFoodName().toUpperCase().equals(foodName)){
                Log.d("success?", "FOOD NAME FOUND");
                return i;
            }
        }
        return -1;
    }

    private void showDatePicker() {
        Calendar calender = Calendar.getInstance();

        DatePickerDialog dpd = new DatePickerDialog(EditPopup.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                date = Integer.toString(dayOfMonth) + "/" + Integer.toString(month) + "/" + Integer.toString(year);

                //todo add the is expired thing
                calenderSetter.setText(date);
            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));

        dpd.show();
    }
}