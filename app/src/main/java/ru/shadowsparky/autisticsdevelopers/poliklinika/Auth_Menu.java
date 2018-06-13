package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Auth_Menu extends AppCompatActivity implements View.OnClickListener {
    private Button _btn;
    private EditText _login;
    private EditText _password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth__menu);
        _login = findViewById(R.id.LoginBox);
        _password = findViewById(R.id.PasswordBox);
        getSupportActionBar().setTitle("Авторизация");
//        _btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ArrayList<SQL_Engine> res = null;
        SQL_Auth result = null;
        String[] bindValues = {"Key", "Login", "Password"};
        String[] values = {"EnableExecute", _login.getText().toString(), _password.getText().toString()};
        SQL_Auth SA = new SQL_Auth(bindValues, values, "https://autisticapi.shadowsparky.ru/auth.php");
        SA.set_context(this);
        SQLThread thread = new SQLThread();
        thread.execute(SA);
        try {
            res = thread.get();
            if (SA.HandleResult((SQL_Engine) res.get(0))){
                Intent i = new Intent(this, UserMenu.class);
                startActivity(i);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
