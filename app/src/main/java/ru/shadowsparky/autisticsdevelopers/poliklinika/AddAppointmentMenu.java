package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class AddAppointmentMenu extends AppCompatActivity {
    private int selectedSpecID;
    private int year_x, day_x, month_x;
    private static final int DIALOG_ID = 0;
    TextView tv;
    DatePickerDialog DPD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment_menu);
        getSupportActionBar().setTitle("Запись на прием");
        final Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        SQL_AvailableSpec SA = new SQL_AvailableSpec(null, null, null);
        SA.set_context(this);
        String [] qryRes = SA.QueryResult();
        if (qryRes == null) return;
        FillAvailableDocs(SA.QueryResult());
        tv = findViewById(R.id.dateView);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

    }

    private void FillAvailableDocs(String [] data){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Выберите специальность");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpecID = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createDialog(){
        DPD = new DatePickerDialog(this, dpickerListner, year_x, month_x, day_x);
        DPD.setTitle("Выберите дату");
        DPD.show();
    }
    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month;
            day_x = dayOfMonth;
            tv.setText(year_x + "."+ month_x + "." + day_x);
        }
    };
}
