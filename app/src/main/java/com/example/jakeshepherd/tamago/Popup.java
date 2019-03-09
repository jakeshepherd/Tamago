package com.example.jakeshepherd.tamago;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.display.DisplayManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//TODO -- the popup is insanely ugly but its being a nightmare
//TODO -- upercase thing for deleting
public class Popup extends AppCompatActivity {
    Button cancelButton, confirmButton;
    EditText nameToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.65), (int) (height*0.25));

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setElevation(20);
<<<<<<< HEAD
        getWindow().setLayout((int) (width*0.6), (int) (height*0.25));

=======
>>>>>>> parent of 421094f... thing next thing might break the app
        setOnCLickListeners();
    }

    public void setOnCLickListeners(){
        final Intent returnIntent = new Intent();
        cancelButton = findViewById(R.id.buttonCancel);
        confirmButton = findViewById(R.id.buttonConfirm);
        nameToDelete = findViewById(R.id.editFoodName);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameToDelete.getText().length() == 0){
                    nameToDelete.setError("Must be filled in");
                }else{
                    returnIntent.putExtra("FoodName", String.valueOf(nameToDelete.getText()).toUpperCase());
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }
}
