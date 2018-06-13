package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserMenu extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        getSupportActionBar().setTitle("Мои записи");
    }

    @Override
    public void onClick(View v) {
        Intent addAppointment = new Intent(this, AddAppointmentMenu.class);
        startActivity(addAppointment);
    }
}
