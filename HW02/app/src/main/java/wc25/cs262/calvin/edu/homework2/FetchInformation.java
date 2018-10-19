package wc25.cs262.calvin.edu.homework2;


import android.net.Uri;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class FetchInformation extends AsyncTask<String, Void, String> {
    private TextView mPlayerList;
    private TextView mPlayerIdList;
    private EditText mInput;

    private static final String LOG_TAG = FetchInformation.class.getSimpleName();

    public FetchInformation(TextView mPlayerList, TextView mPlayerIdList, EditText mInput) {
        this.mPlayerList = mPlayerList;
        this.mPlayerIdList = mPlayerIdList;
        this.mInput = mInput;
    }

    @Override
    protected String doInBackground(String... params){

        String queryString = params[0];

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String playerJSONString = null;

        try {
            final String PLAYER_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?";

            final String QUERY_PARAM = "q"; // Parameter for the search string.
            final String MAX_RESULTS = "maxResults"; // Parameter that limits search results.
            final String PRINT_TYPE = "printType"; // Parameter to filter by print type.

            Uri builtURI = Uri.parse(PLAYER_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            URL requestURL = new URL(builtURI.toString());


            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                return null;
            }
            playerJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return playerJSONString;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            for(int i = 0; i<itemsArray.length(); i++){
                JSONObject book = itemsArray.getJSONObject(i);
                String title=null;
                String authors=null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");


                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e){
                    e.printStackTrace();
                }


                if (title != null && authors != null){
                    mPlayerList.setText(title);
                    mPlayerIdList.setText(authors);
                    return;
                }
            }


            mPlayerList.setText("No Results Found");
            mPlayerIdList.setText("");


        } catch (Exception e){
            mPlayerList.setText("No Results Found");
            mPlayerIdList.setText("");
            e.printStackTrace();
        }
    }
}
