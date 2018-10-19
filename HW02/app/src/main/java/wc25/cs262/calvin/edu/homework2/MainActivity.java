package wc25.cs262.calvin.edu.homework2;

import android.content.Context;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private EditText mInput;
    private TextView mTitleText;
    private TextView mAuthorText;
    private Context context;
    private ArrayList<String> playerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void searchInformation(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        String queryString = mInput.getText().toString();

        if(queryString.equals(" ")){
        queryString = "players";
    }

        else{
        queryString = "player/" + queryString + "/";
    }

        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryString", queryString);


    }

    public Loader<String> onCreateLoader(int i, Bundle bundle){
        return new PlayerLoader(this, bundle.getString("queryString"));
    }

    public void onLoadFinished(Loader<String> loader, String s){
        playerData.clear();

        if (s.startsWith(context.getString(R.string.error))) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(s);
            String playerInfo;
            try{
                JSONArray itemsArray = jsonObject.getJSONArray("items");
                int num_players = itemsArray.length();
                for(int i=0; i < num_players; i++) {
                    JSONObject player = itemsArray.getJSONObject(i); //get the current player
                    playerInfo = parseJSONPlayer(player); //parse the data
                    playerData.add(playerInfo);
                }
            } catch (Exception e) {
                playerInfo = parseJSONPlayer(jsonObject);
                playerData.add(playerInfo);
            } finally {
                playerData.setPlayers(playerData);
            }

        } catch (Exception e) {
            Toast.makeText(context, getString(R.string.connection_fail), Toast.LENGTH_SHORT).show();
        }
    }
    private String parseJSONPlayer(JSONObject player) {
        JSONObject jsonObject = new JSONObject(player);

    }
}
