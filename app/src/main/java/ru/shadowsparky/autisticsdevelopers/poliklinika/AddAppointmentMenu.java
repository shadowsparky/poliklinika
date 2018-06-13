package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddAppointmentMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment_menu);
        getSupportActionBar().setTitle("Добавление записи");
    }
}
