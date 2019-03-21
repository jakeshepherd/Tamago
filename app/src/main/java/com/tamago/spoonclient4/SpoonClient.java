package com.tamago.spoonclient4;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpoonClient {
    private String apiKey = "ac668d27e9mshb785cfffd22f03dp1f71e6jsn171dfa398c3b";
    private String baseUrl = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/";

    /**
     *  Generates a URL to be used for the Search Recipes By Ingredients Endpoint
     * @param ingredients: single string of all ingredients split by comma and no whitespace (eg: "apples,flour,sugar")
     * @param resultNum: number of results you want URL to return
     * @param ranking: int value 1 or 2:
     *               1: maximize used ingredients
     *               2: minimize missing ingredients
     * @return a functioning URL to perform API request with
     */
    public String URLGeneratorRBI(String ingredients, int resultNum, int ranking){
        String ingURL = ingredients.replace(",", "%2C");
        String ApiURL = baseUrl.concat("findByIngredients")
                .concat(String.format("?number=%d", resultNum))
                .concat(String.format("&ranking=%d", ranking))
                .concat(String.format("&ingredients=%s", ingURL));
        return ApiURL;
    }

    /**
     * API Request functions below with test url
     */
    String urlTest = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?number=5&ranking=1&ingredients=apples%2Cflour%2Csugar";

    // Requesting Function if Endpoint returns JSONArray
    public JSONArray getAsJSONArray(String Url, String Key) throws JSONException {
        JSONArray json;
        System.out.println(Url);
        try {
            HttpResponse<JsonNode> response = Unirest.get(Url)
                    .header("X-RapidAPI-Key", Key)
                    .asJson();
            JsonNode node = response.getBody();

            if (!node.isArray()) {
                throw new UnirestException("The request returns a JSON Object. Json: "+node.getObject().toString(4));
            } else {
                json = node.getArray();
            }
        } catch (UnirestException e) {
            throw new JSONException("Error Occurred while getting JSON Array: "+e.getLocalizedMessage());
        }
        return json;
    }

    // Requesting function if Endpoint returns a JSONObject
    public JSONObject getAsJSONObject(String Url, String Key) throws JSONException{
        JSONObject json;
        try {
            HttpResponse<JsonNode> response = Unirest.get(Url)
                    .header("X-RapidAPI-Key", Key)
                    .asJson();
            JsonNode node = response.getBody();

            if (node.isArray()) {
                throw new UnirestException("The request returns a JSON Array. Json: "+node.getArray().toString(4));
            } else {
                json = node.getObject();
            }
        } catch (UnirestException e) {
            throw new JSONException("Error Occurred while getting JSON Object: "+e.getLocalizedMessage());
        }
        return json;
    }

    // functions below work off of getRecipesByIngredient Endpoint
    /**
     *
     * @param Url: API Request URL
     * @param Key: API Key
     * @param index: index of the request result (which recipe) to get the image URL from
     * @return imageURL: string that links to the image resource of the recipe provided by API
     */
    public String getRecipeImageURL(String Url, String Key, int index) throws JSONException{
        JSONArray json = getAsJSONArray(Url, Key);
        String ImageURL = json.getJSONObject(index).get("image").toString();
        return ImageURL;
    }

    /**
     *
     * @param Url: API Request URL
     * @param Key: API Key
     * @param index: index of the request result (which recipe) to get the recipe ID from
     * @return id: string of the requested recipe ID
     */
    public String getRecipeID(String Url, String Key, int index) throws JSONException{
        JSONArray json = getAsJSONArray(Url, Key);
        String ID = json.getJSONObject(index).get("id").toString();
        return ID;
    }

    //the following function obtains the JSONObject from the Get Recipe Information Endpoint
    public JSONObject getRecipeInfo(String Url, String Key, int index) throws JSONException{
        String ID = getRecipeID(Url, Key, index);
        String InfoUrl = String.format("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/%s/information",ID);
        JSONObject recipeJson = getAsJSONObject(InfoUrl, apiKey);
        return recipeJson;
    }

    // the following functions use the GetRecipeInformation endpoint
    /**
     *
     * @param Url: API Request URL
     * @param Key: API Key
     * @param index: index of the request result (which recipe) to get the recipe info from
     * @return Instructions: String containing recipe instructions
     */
    public String getRecipeInstructions(String Url, String Key, int index) throws JSONException{
        JSONObject json = getRecipeInfo(Url, Key, index);
        String Instructions = json.get("instructions").toString();
        return Instructions;
    }

    /**
     *
     * @param Url: API Request URL
     * @param Key: API Key
     * @param index: index of the request result (which recipe) to get the recipe info from
     * @return RecipeTitle: String of the Recipe's name given by the API
     */
    public String getRecipeTitle(String Url, String Key, int index) throws JSONException{
        JSONObject json = getRecipeInfo(Url, Key, index);
        String RecipeTitle = json.get("title").toString();
        return RecipeTitle;
    }

    /**
     *
     * @param Url: API Request URL
     * @param Key: API Key
     * @param index: index of the request result (which recipe) to get the recipe info from
     * @return RecipeMins: String of time needed to make recipe
     */
    public String getRecipeMins(String Url, String Key, int index) throws JSONException{
        JSONObject json = getRecipeInfo(Url, Key, index);
        String RecipeMins = json.get("readyInMinutes").toString();
        return RecipeMins;
    }

    /**
     *
     * @param IngredientsList: JSONArray of Ingredients from the JSON Response of the API
     * @param rIndex: index of which ingredient of the recipe to get name of
     * @return Ingredient: String of ingredient name and quantity needed for recipe
     */
    public String getIngredientInfos(JSONArray IngredientsList, int rIndex) throws JSONException{
        String ingredient = IngredientsList.getJSONObject(rIndex).get("originalString").toString();
        return ingredient;
    }

    /**
     *
     * @param Url: API Request URL
     * @param Key: API Key
     * @param index: index of the request result (which recipe) to get the recipe info from
     * @return ingredientsList: String array that contains all the ingredients for given recipe
     */
    public String[] getIngredientList(String Url, String Key, int index) throws JSONException{
        JSONArray recipeInfo = getRecipeInfo(Url, Key, index).getJSONArray("extendedIngredients");
        int listLength = recipeInfo.length();
        String[] ingredientsList = new String[listLength];
        for (int i = 0; i <= listLength-1; i++) {
            String ingredient = getIngredientInfos(recipeInfo, i);
            ingredientsList[i] = ingredient;
        }
        return ingredientsList;
    }

    /**
     * To get values from this function use returned.get("parameter").toString();
     * @param Url API Request URL
     * @param Key: API Key
     * @param index: index of the request result (which recipe) to get the recipe info from
     * @return a filtered version of the JSON Object returned by the Endpoint excluding ingredients data
     */
    public JSONObject getAllRecipeData(String Url, String Key, int index) throws JSONException{
        JSONObject recipeInfo = getRecipeInfo(Url, Key, index);
        JSONObject recipeArr = new JSONObject();
        //Vegetarian
        recipeArr.put("vegetarian", recipeInfo.get("vegetarian").toString());
        //Vegan
        recipeArr.put("vegan", recipeInfo.get("vegan").toString());
        //Gluten-free
        recipeArr.put("glutenFree", recipeInfo.get("glutenFree").toString());
        //title
        recipeArr.put("name", recipeInfo.get("title").toString());
        //mins
        recipeArr.put("readyMins", recipeInfo.get("readyInMinutes").toString());
        //imageURL
        recipeArr.put("imageURL", recipeInfo.get("image").toString());
        //Instructions
        recipeArr.put("instructions", recipeInfo.get("instructions").toString());
        return recipeArr;
    }
}
