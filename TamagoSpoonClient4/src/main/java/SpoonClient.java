import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpoonClient {
    private String apiKey = "ac668d27e9mshb785cfffd22f03dp1f71e6jsn171dfa398c3b";
    String urlTest = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?number=5&ranking=1&ingredients=apples%2Cflour%2Csugar";
    public JSONArray getAsJSONArray(String Url, String Key){
        JSONArray json;
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

    public JSONObject getAsJSONObject(String Url, String Key) {
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
    public String getRecipeImageURL(String Url, String Key, int index){
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
    public String getRecipeID(String Url, String Key, int index){
        JSONArray json = getAsJSONArray(Url, Key);
        String ID = json.getJSONObject(index).get("id").toString();
        return ID;
    }

    public JSONObject getRecipeInfo(String Url, String Key, int index){
        String ID = getRecipeID(Url, Key, index);
        String InfoUrl = String.format("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/%s/information",ID);
        JSONObject recipeJson = getAsJSONObject(InfoUrl, apiKey);
        return recipeJson;
    }
    // the following functions use the GetRecipeInformation endpoint
    public String getRecipeInstructions(String Url, String Key, int index){
        JSONObject json = getRecipeInfo(Url, Key, index);
        String Instructions = json.get("instructions").toString();
        return Instructions;
    }

    public String getIngredientName(String Url, String Key, int index, int rIndex){
        JSONObject json = getRecipeInfo(Url, Key, index);
        String ingredientName = json.getJSONArray("extendedIngredients").getJSONObject(rIndex).get("name").toString();
        return ingredientName;
    }
    public String getIngredientInfo(String Url, String Key, int index, int rIndex){
        JSONObject json = getRecipeInfo(Url, Key, index);
        String ingredientName = json.getJSONArray("extendedIngredients").getJSONObject(rIndex).get("originalString").toString();
        return ingredientName;
    }
    public String getIngredientInfos(JSONArray IngredientsList, int rIndex){
        String ingredientName = IngredientsList.getJSONObject(rIndex).get("originalString").toString();
        return ingredientName;
    }

    public String[] getIngredientList(String Url, String Key, int index) {
        JSONArray recipeInfo = getRecipeInfo(Url, Key, index).getJSONArray("extendedIngredients");
        int listLength = recipeInfo.length();
        String[] ingredientsList = new String[listLength];
        for (int i = 0; i <= listLength-1; i++) {
            String ingredient = getIngredientInfos(recipeInfo, i);
            ingredientsList[i] = ingredient;
        }
        return ingredientsList;
    }
}