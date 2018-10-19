package wc25.cs262.calvin.edu.hw02;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    private EditText mInput;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInput = (EditText)findViewById(R.id.numberInput);
        mText = (TextView)findViewById(R.id.titleText);


        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }

        String queryString = "0"; //set to 0 so results won't show once app starts

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
            mText.setText(R.string.loading);
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        }

        else {
            if (queryString.length() == 0) {
                mText.setText(R.string.enter_search);
            } else {
                mText.setText(R.string.check_internet);
            }
        }
    }

    public void fetchPlayer(View view) {
        String queryString = mInput.getText().toString();

        if (queryString.toString().length() == 0) {
            queryString = "-1";
        }
        //code implemented from lab05
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
            mText.setText(R.string.loading);
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        }

        else {
            if (queryString.length() == 0) {
                mText.setText(R.string.input_text);
            } else {
                mText.setText(R.string.internet_error);
            }
        }
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new PlayerLoader(this, bundle.getString("queryString"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");


            int i = 0;
            String title = null;


            while (i < itemsArray.length() || (title == null)) {

                JSONObject book = itemsArray.getJSONObject(i);

                try {
                    String id = book.getString("id");
                    String eMail = book.getString("emailAddress");
                    String name = null;
                    try {
                        name = book.getString("name");
                    } catch (Exception e) {
                        name = "No Name";
                    }
                    if (title == null) {
                        title = id + ", " + name + ", " + eMail;
                    } else {
                        title = title + "\n" + id + ", " + name + ", " + eMail;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                i++;
            }


            if (title != null){
                mText.setText(title);
                mInput.setText("");
            } else {

                mText.setText("2");
            }

        } catch(Exception e) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                String title = null;
                String id = jsonObject.getString("id");
                String eMail = jsonObject.getString("emailAddress");
                String name = null;
                try {
                    name = jsonObject.getString("name");
                } catch (Exception q) {
                    name = "No Name";
                }
                title = id + ", " + name + ", " + eMail;

                if (title != null) {
                    mText.setText(title);
                    mInput.setText("");
                } else {

                    mText.setText(R.string.no_results);
                }
            } catch (Exception q) {
                mText.setText(R.string.hint);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {}
}