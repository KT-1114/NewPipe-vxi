package org.schabi.newpipe.vox;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.schabi.newpipe.MainActivity;

public class VoxNewPipeReceiver extends BroadcastReceiver {
    private static final String TAG = "VoxNewPipeReceiver";

    public static final String ACTION_VOX_SEARCH = "org.schabi.newpipe.VOX_SEARCH";
    public static final String ACTION_VOX_PLAY = "org.schabi.newpipe.VOX_PLAY";
    public static final String ACTION_VOX_TRENDING = "org.schabi.newpipe.VOX_TRENDING";

    public static final String EXTRA_QUERY = "org.schabi.newpipe.EXTRA_QUERY";
    public static final String EXTRA_AUTO_PLAY = "org.schabi.newpipe.EXTRA_AUTO_PLAY";

    public static final String ACTION_RESPONSE = "com.kt.vox.ACTION_RESPONSE";
    public static final String EXTRA_RESPONSE_TEXT = "response_text";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final String action = intent.getAction();
        if (action == null) {
            return;
        }

        String query = intent.getStringExtra(EXTRA_QUERY);
        if (query == null) {
            query = intent.getStringExtra("1");
        }
        Log.d(TAG, "onReceive: action=" + action + ", query=" + query);

        final Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String responseText = "";
        switch (action) {
            case ACTION_VOX_SEARCH:
                mainIntent.setAction(Intent.ACTION_SEARCH);
                mainIntent.putExtra(SearchManager.QUERY, query);
                responseText = "Opening " + query + " on NewPipe";
                break;
            case ACTION_VOX_PLAY:
                mainIntent.setAction(Intent.ACTION_SEARCH);
                mainIntent.putExtra(SearchManager.QUERY, query);
                mainIntent.putExtra(EXTRA_AUTO_PLAY, true);
                responseText = "Playing " + query + " on NewPipe";
                break;
            case ACTION_VOX_TRENDING:
                mainIntent.setAction(ACTION_VOX_TRENDING);
                responseText = "Opening Trending on NewPipe";
                break;
            default:
                return;
        }

        context.startActivity(mainIntent);

        final Intent responseIntent = new Intent(ACTION_RESPONSE);
        responseIntent.putExtra(EXTRA_RESPONSE_TEXT, responseText);
        context.sendBroadcast(responseIntent);
    }
}
