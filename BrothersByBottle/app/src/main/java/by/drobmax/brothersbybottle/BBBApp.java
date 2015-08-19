package by.drobmax.brothersbybottle;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

import by.drobmax.brothersbybottle.activities.MainActivity;

/**
 * Created by Admin on 11.08.2015.
 */
public class BBBApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, getString(R.string.application_id),
                getString(R.string.client_id));
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
