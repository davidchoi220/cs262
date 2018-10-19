package wc25.cs262.calvin.edu.hw02;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class PlayerLoader extends AsyncTaskLoader<String> {
    private String mQueryString;

    public PlayerLoader(Context context, String queryString){
        super(context);
        mQueryString = queryString;
    }

    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        return NetworkUtils.getPlayerInfo(mQueryString);
    }
}
