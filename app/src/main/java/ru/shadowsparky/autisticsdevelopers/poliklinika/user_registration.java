/*
 * Copyright (c) Samsonov Eugene, 2018.
 */

package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class user_registration extends AppCompatActivity {
    EditText PolicyNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
    }

    private Boolean checkPolicyNumber(){
        EditText PolicyNumber = (EditText) findViewById(R.id.reg_PolicyNumber);
        if (PolicyNumber.getText().toString().length() != 16) {
            PolicyNumber.setError("Номер полиса состоит из 16 символов");
            return false;
        }
        return true;
    }

    private Boolean checkEmpty(EditText e, String raiseError){
        if (e.getText().toString().isEmpty()) {
            e.setError(raiseError);
            return false;
        }
        return true;
    }

    private Boolean checkPassword(EditText e){
        if (e.getText().toString().length() <= 3) {
            e.setError("У вас слишком слабый пароль");
            return false;
        }
        return true;
    }

    public void onClickRegisterButton(View v){
        CheckBox cb = (CheckBox) findViewById(R.id.PersonalDataBOX);
        EditText Login = (EditText)findViewById(R.id.reg_Login);
        EditText Password = (EditText) findViewById(R.id.reg_Password);
        if (cb.isChecked()) {
            if (checkPolicyNumber()){
                if (checkEmpty(Login, "Ваш логин не может быть пустым")){
                    if (checkEmpty(Password, "Ваш пароль не может быть пустым")) {
                        if (checkPassword(Password)) {
                            createUser(v);
                        }
                    }
                }
            }
        } else {
            Snackbar.make(v, "Вы должны дать согласие на обработку Ваших персональных данных", Snackbar.LENGTH_SHORT).show();}
    }
    public void createUser(View v){
        EditText PolicyNumber = (EditText) findViewById(R.id.reg_PolicyNumber);
        EditText Login = (EditText)findViewById(R.id.reg_Login);
        EditText Password = (EditText) findViewById(R.id.reg_Password);
        String[] bindValues = {"Key", "Policy_Number", "Login", "Password"};
        String[] values = {"EnableExecute",PolicyNumber.getText().toString(), Login.getText().toString(), Password.getText().toString()};
        SQL_CreateUser SCU = new SQL_CreateUser(bindValues, values, "https://autisticapi.shadowsparky.ru/userRegister.php");
        ArrayList<SQL_Engine> res = SCU.CatchResult();
        if (res != null) {
            String result = ((SQL_CreateUser) res.get(0)).getResult();
            if (result != null) {
                if (!result.equals("Ваш аккаунт успешно зарегистрирован")) {
                    Snackbar.make(v, result, Snackbar.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else
                Snackbar.make(v, "При подключении к серверу произошла ошибка. Проверьте ваше соединение с интернетом", Snackbar.LENGTH_SHORT).show();
        } else
            Snackbar.make(v, "При подключении к серверу произошла ошибка. Проверьте ваше соединение с интернетом", Snackbar.LENGTH_SHORT).show();
    }
}
