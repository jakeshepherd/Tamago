package com.example.jakeshepherd.tamago;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONObject;
import org.json.JSONException;
import com.tamago.spoonclient4.SpoonClient;

public class APILink extends AppCompatActivity {

    private String apiKey = "ac668d27e9mshb785cfffd22f03dp1f71e6jsn171dfa398c3b";
    private String baseUrl = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apilayout);



        Button test = (Button) findViewById(R.id.testButton);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpoonClient sc = new SpoonClient();
                /* For now will just assume that it is input in the correct form of a string
                 of ingredients split by comma and no whitespace (eg: "apples,flour,sugar") */
                String ingredients = ((EditText)findViewById(R.id.foodItem)).getText().toString();
                /* Generates URL, again just for now we'll get one recipe back but can change
                resultNum to get more once all of this is working */
                String URL = sc.URLGeneratorRBI(ingredients, 1, 1);
                try {
                    JSONObject returnedRecipe = sc.getAllRecipeData(URL, apiKey, 1);
                    /* For now I'm just getting the recipe instructions but there are other parameters
                    to get back once it's all fully working */
                    String returnedInstructions = returnedRecipe.get("instructions").toString();
                    TextView textView = (TextView) findViewById(R.id.testResults);
                    textView.setText(returnedInstructions);
                } catch(JSONException e) {

                }
            }
        });
    }
}

