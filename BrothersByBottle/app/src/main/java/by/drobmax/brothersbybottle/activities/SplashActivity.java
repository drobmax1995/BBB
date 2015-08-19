package by.drobmax.brothersbybottle.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import by.drobmax.brothersbybottle.R;
import by.drobmax.brothersbybottle.utils.Const;
import by.drobmax.brothersbybottle.utils.ListenerHosting;
import by.drobmax.brothersbybottle.utils.Utils;

/**
 * Created by Admin on 11.08.2015.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
    }

    private String _sessionToken;

    @Override
    protected void onResume() {
        super.onResume();
        launchSplash();
    }

    private void launchSplash() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    if (!isHasSessionToken()) {
                        Log.d("myLogs", "sesion not exists");
                        launchLoginActivity();
                    } else {
                        Log.d("myLogs", "sesion  exist");

                        startStoredSession();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void startStoredSession() {
        restoreSession(_sessionToken);
    }

    private void restoreSession(String sessionToken) {
        Log.d("myLogs", "try restore session");
        Looper.prepare();
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_wait));
        ParseUser.becomeInBackground(sessionToken, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                dia.dismiss();
                Log.d("myLogs", "sesion restored");

                if (parseUser != null) {
                    String sessionToken = parseUser.getSessionToken();
                    ListenerHosting.getInstance().setUser(parseUser);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SharedPreferences sharedPreferences = getSharedPreferences(
                            Const.SH_PREFERENCE_NAME,
                            MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Const.SESSION_TOKEN, sessionToken);
                    editor.commit();
                    finish();
                } else {
                    Log.d("myLogs", "sesion not restored");

                    Utils.showDialog(
                            SplashActivity.this,
                            getString(R.string.err_login) + " "
                                    + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isHasSessionToken() {
        return (_sessionToken = getSharedPreferences(Const.SH_PREFERENCE_NAME, MODE_PRIVATE)
                .getString(Const.SESSION_TOKEN, "")) == "" ? false : true;

    }

    private void launchLoginActivity() {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
