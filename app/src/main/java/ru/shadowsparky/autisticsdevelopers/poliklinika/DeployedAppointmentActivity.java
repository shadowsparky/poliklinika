/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeployedAppointmentActivity extends AppCompatActivity {
    private TextView _AppointmentView;
    private TextView _PacientView;
    private TextView _DoctorView;
    private TextView _SpecializationView;
    private TextView _DateView;
    private TextView _TimeView;
    private TextView _CabinetView;
    private String AppointmentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deployed_appointment);
        getSupportActionBar().setTitle("Подробная информация о записи");
        _AppointmentView = (TextView) findViewById(R.id.AppointmentNumber);
        _PacientView = (TextView)findViewById(R.id.PacientView);
        _DoctorView = (TextView)findViewById(R.id.DoctorView);
        _SpecializationView = (TextView)findViewById(R.id.SpecializationView);
        _DateView = (TextView)findViewById(R.id.DateView);
        _TimeView = (TextView)findViewById(R.id.TimeView);
        _CabinetView = (TextView)findViewById(R.id.CabinetView);
        Intent myIntent = getIntent();
        _AppointmentView.setText(_AppointmentView.getText() + myIntent.getStringExtra("AppointmentNumber"));
        _PacientView.setText(_PacientView.getText() + myIntent.getStringExtra("PacientInfo"));
        _DoctorView.setText(_DoctorView.getText() + myIntent.getStringExtra("DoctorInfo"));
        _SpecializationView.setText(_SpecializationView.getText() + myIntent.getStringExtra("Position"));
        _DateView.setText(_DateView.getText() + myIntent.getStringExtra("Date"));
        _TimeView.setText(_TimeView.getText() + myIntent.getStringExtra("Time"));
        _CabinetView.setText(_CabinetView.getText() + myIntent.getStringExtra("CabinetNumber"));
        AppointmentNumber = myIntent.getStringExtra("AppointmentNumber");
    }
    public void onDeleteAppointmentButtonClick(View view){
        String[] bindValues = {"Key", "Appointment_Number"};
        String[] values = {"EnableExecute", AppointmentNumber};
        SQL_DropAppointment SD = new SQL_DropAppointment(bindValues, values, "https://autisticapi.shadowsparky.ru/dropAppointment.php");
        ArrayList<SQL_Engine> res = SD.CatchResult();
        if (res != null) {
            Toast.makeText(this, ((SQL_DropAppointment) res.get(0)).getResult(), Toast.LENGTH_SHORT).show();
            finish();
        }
        else
            Toast.makeText(this, "Во время отмены записи произошла ошибка. Проверьте ваше интернет соединение", Toast.LENGTH_SHORT).show();
    }
}
