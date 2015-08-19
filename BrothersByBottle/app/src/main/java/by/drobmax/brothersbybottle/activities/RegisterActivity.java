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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import by.drobmax.brothersbybottle.R;
import by.drobmax.brothersbybottle.utils.Const;
import by.drobmax.brothersbybottle.utils.ListenerHosting;
import by.drobmax.brothersbybottle.utils.Utils;

/**
 * Created by Admin on 11.08.2015.
 */
public class RegisterActivity extends Activity {
    /**
     * The username EditText.
     */
    private EditText user;

    /**
     * The password EditText.
     */
    private EditText pwd;

    /**
     * The email EditText.
     */
    private EditText email;

    private Button btnReg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        initViews();
    }

    private void initViews() {
        btnReg = (Button) findViewById(R.id.btnReg1);
        user = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pwd);
        email = (EditText) findViewById(R.id.email);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = user.getText().toString();
                String password = pwd.getText().toString();
                String emailValue = email.getText().toString();
                if (userName.length() == 0 || password.length() == 0) {
                    Utils.showDialog(RegisterActivity.this, getString(R.string.err_fields_empty));
                } else {
                    registerUser(userName, password, emailValue);
                }
            }
        });
    }

    private void registerUser(String userName, String password, String emailValue) {
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_wait));

        final ParseUser pu = new ParseUser();
        pu.setEmail(emailValue);
        pu.setPassword(password);
        pu.setUsername(userName);
        pu.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e) {
                dia.dismiss();
                if (e == null) {
                    ListenerHosting.getInstance().setUser(pu);
                    String sessionToken = pu.getSessionToken();
                    ListenerHosting.getInstance().setUser(pu);
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    SharedPreferences sharedPreferences = getSharedPreferences(
                            Const.SH_PREFERENCE_NAME,
                            MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Const.SESSION_TOKEN,sessionToken);
                    editor.commit();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else {
                    Utils.showDialog(
                            RegisterActivity.this,
                            getString(R.string.err_singup) + " "
                                    + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }


}
