package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;

public class Auth_Menu extends AppCompatActivity implements View.OnClickListener {
    public static final String AUTH_URL = "https://autisticapi.shadowsparky.ru/auth.php";

    private Button _btn;
    private EditText _login;
    private EditText _password;
    private static String Login;

    public static SharedPreferences getsPref() {
        return sPref;
    }
    private static SharedPreferences sPref;
    public static String getLogin() {
        return Login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth__menu);
        _login = findViewById(R.id.LoginBox);
        _password = findViewById(R.id.PasswordBox);
        LoadAccountData();
    }

    @Override
    public void onClick(View v) {
        auth();
    }
    private void raiseAuthError() {
        Toast.makeText(this, getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
    }
    public void registrationButtonClick(View view){
        Intent i = new Intent(this, user_registration.class);
        startActivity(i);
    }
    private void SaveAccountData(){
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("Login", _login.getText().toString());
        ed.putString("Password", _password.getText().toString());
        ed.commit();
    }
    private void auth(){
        if ((!_login.getText().toString().equals("")) && (!_password.getText().toString().equals(""))) {
            try {
                ArrayList<SQL_Engine> res = null;
                SQL_Auth result = null;
                String[] bindValues = {"Key", "Login", "Password"};
                String[] values = {"EnableExecute", _login.getText().toString(), _password.getText().toString()};
                SQL_Auth SA = new SQL_Auth(bindValues, values, AUTH_URL);
                SA.set_context(this);
                res = SA.CatchResult();
                if (res == null) {
                    raiseAuthError();
                    return;
                }
                if (SA.HandleResult((SQL_Auth) res.get(0))) {
                    Login = _login.getText().toString();
                    Intent i = new Intent(this, UserMenu.class);
                    SaveAccountData();
                    startActivity(i);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
                raiseAuthError();
            }
        }
    }
    private void LoadAccountData(){
        sPref = getPreferences(MODE_PRIVATE);
        _login.setText(sPref.getString("Login", ""));
        _password.setText(sPref.getString("Password", ""));
        auth();
    }
}
