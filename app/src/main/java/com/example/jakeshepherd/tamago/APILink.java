package com.example.jakeshepherd.tamago;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class APILink extends AppCompatActivity {

    private String apiKey = "ac668d27e9mshb785cfffd22f03dp1f71e6jsn171dfa398c3b";
    private String baseUrl = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/";
    private TamagoRecipeDB RDB = new TamagoRecipeDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apilayout);



        Button test = (Button) findViewById(R.id.testButton);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * at the moment set to return the instructions on first search result that uses eggs
             */
            public void onClick(View view) {
                /* For now will just assume that it is input in the correct form of a string
                 of ingredients split by comma and no whitespace (eg: "apples,flour,sugar") */
                String ingredient = ((EditText)findViewById(R.id.foodItem)).getText().toString();
                /* Generates URL, again just for now we'll get one recipe back but can change
                resultNum to get more once all of this is working */
                String searchQuery = RDB.getRecipeIDwithIng(ingredient);
                String returnedInstructions = RDB.getInstructions(searchQuery);
                /* For now I'm just getting the recipe instructions but there are other parameters
                to get back once it's all fully working */
                TextView textView = (TextView) findViewById(R.id.testResults);
                textView.setText(returnedInstructions);

            }
        });
    }
}

