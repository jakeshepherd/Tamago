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

                String ingredients = ((EditText)findViewById(R.id.foodItem)).getText().toString();
                String URL = sc.URLGeneratorRBI(ingredients, 1, 1);
                String returnedIngredients[] = sc.getIngredientList(URL, apiKey);
                //???the other thing);
                TextView textView = (TextView) findViewById(R.id.foodItem);
                textView.setText("");//returnIngredients);
            }
        });
    }

    /*public FridgeLink() {
        SpoonClient sc = new SpoonClient();
    }

    public String getURL(String ingredients, int resultNum, int ranking) {
        url = sc.URLGeneratorRBI(ingredients, resultNum, ranking);
        return url;
    }

    public String[] getList(String Url, String Key, int index) {
        String[] ingredientsList = sc.getIngredientsList(Url, Key, index);
        return ingredientsList;
    }

    public JSONObject getData(String Url, String Key, int index) {

    }
    public static void main(String args[]) {

        FridgeLink testFridge = new FridgeLink();
        System.out.println(testFridge.getURL("Steak", 1, 1,));
    } */
}

