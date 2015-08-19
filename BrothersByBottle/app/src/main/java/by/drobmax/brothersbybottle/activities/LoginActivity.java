package by.drobmax.brothersbybottle.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

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
public class LoginActivity extends Activity {
    private Button btnReg, btnLogin;
    /**
     * The username edittext.
     */
    private EditText user;

    /**
     * The password edittext.
     */
    private EditText pwd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        initViews();
    }

    private void initViews() {
        btnReg = (Button) findViewById(R.id.btnReg);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        user = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pwd);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = user.getText().toString();
                String password = pwd.getText().toString();
                if (userName.length() == 0 || password.length() == 0) {
                    Utils.showDialog(LoginActivity.this, getString(R.string.err_fields_empty));
                } else {
                    loginUser(userName, password);
                }

            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String userName, String password) {
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_wait));
        ParseUser.logInInBackground(userName, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                dia.dismiss();
                if (parseUser != null)
                {
                    String sessionToken = parseUser.getSessionToken();
                    ListenerHosting.getInstance().setUser(parseUser);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    SharedPreferences sharedPreferences = getSharedPreferences(
                            Const.SH_PREFERENCE_NAME,
                            MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Const.SESSION_TOKEN,sessionToken);
                    editor.commit();
                    finish();
                }
                else
                {
                    Utils.showDialog(
                            LoginActivity.this,
                            getString(R.string.err_login) + " "
                                    + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
