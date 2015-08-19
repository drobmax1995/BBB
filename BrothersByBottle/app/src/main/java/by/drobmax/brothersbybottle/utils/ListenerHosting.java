package by.drobmax.brothersbybottle.utils;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import by.drobmax.brothersbybottle.interfaces.OnReplaceFragmentListener;

/**
 * Created by Admin on 11.08.2015.
 */
public class ListenerHosting {
    private static ListenerHosting instance;
    private ParseUser user;
    private OnReplaceFragmentListener onReplaceFragmentListener;

    public OnReplaceFragmentListener getOnReplaceFragmentListener() {
        return onReplaceFragmentListener;
    }

    public void setOnReplaceFragmentListener(OnReplaceFragmentListener onReplaceFragmentListener) {
        this.onReplaceFragmentListener = onReplaceFragmentListener;
    }

    public ParseUser getUser() {
        return user;
    }

    public void setUser(ParseUser user) {
        this.user = user;
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("owner", ParseUser.getCurrentUser().getObjectId());
        installation.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {


                if (e != null) {
                    Log.d("myLogs", e.getMessage().toString() + " inst");
                } else {
                    Log.d("myLogs", "done inst");
                }

            }
        });
    }

    public static ListenerHosting getInstance() {
        if (instance == null) {
            instance = new ListenerHosting();
        }
        return instance;
    }

    private ListenerHosting() {
    }

}
