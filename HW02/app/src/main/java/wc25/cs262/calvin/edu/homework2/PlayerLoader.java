package wc25.cs262.calvin.edu.homework2;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class PlayerLoader extends AsyncTaskLoader<String> {
    private String queryString;

    public PlayerLoader(Context context, String queryString){
        super(context);
        queryString = queryString;
    }

    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return NetworkUtils.getPlayerInfo(queryString);
    }
}
