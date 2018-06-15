package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddAppointmentMenu extends AppCompatActivity {
    private int selectedSpecID;
    private int year_x, day_x, month_x;
    private static final int DIALOG_ID = 0;
    private Spinner spinner;
    private Spinner DocSpinner;
    private String choosedDate;
    private String currentDocNumber;
    Spinner DateSpinner;
    DatePickerDialog DPD;
    String[] docNumbers;
    String[] docsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment_menu);
        DateSpinner = findViewById(R.id.DateSpinner);
        DocSpinner = findViewById(R.id.DoctorsSpinner);
        DateSpinner.setEnabled(false);
        DocSpinner.setEnabled(false);
        FillAvailableSpec(catchSpec());
        getSupportActionBar().setTitle("Запись на прием");
        final Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        DateSpinner.setOnTouchListener(spinnerOnTouch);
    }
    private String[] catchSpec(){
        String[] result = null;
        String [] bindValues = {"Key"};
        String [] Values = {"EnableExecute"};
        SQL_AvailableSpec SA = new SQL_AvailableSpec(bindValues, Values, "https://autisticapi.shadowsparky.ru/getavailablespec.php");
        SA.set_context(this);
        ArrayList<SQL_Engine> res = SA.CatchResult();
        result = new String[res.size() + 1];
        result[0] = "Выберите специальность";
        for (int i = 0; i < res.size(); i++) {
            result[i + 1] = ((SQL_AvailableSpec)res.get(i)).getResult();
        }
        return result;
    }

    private void FillAvailableSpec(String [] data){
        customSpinner cs = new customSpinner();
        ArrayAdapter<String> adapter = customSpinner.throwCustomSpinner(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Выбор специализации
                clearSpinner(DateSpinner);
                clearSpinner(DocSpinner);
                selectedSpecID = position;
                DateSpinner.setEnabled(true);
                String[] txt = {"Выберите дату"};
                changeDateSpinner(txt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                DPD = new DatePickerDialog(AddAppointmentMenu.this, dpickerListner, year_x, month_x, day_x);
                DPD.show();
            }
            return false;
        }
    };

    private void changeDateSpinner(String[] res){
        customSpinner cs = new customSpinner();
        ArrayAdapter<String> adapter = customSpinner.throwCustomSpinner(this, android.R.layout.simple_spinner_item, res);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DateSpinner.setAdapter(adapter);
        clearSpinner(DocSpinner);
        DocSpinner.setEnabled(true);
    }

    private void clearSpinner(Spinner _spinner){
        String [] emptyArray = {""};
        ArrayAdapter<String> adapter = customSpinner.throwCustomSpinner(this, android.R.layout.simple_spinner_item, emptyArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinner.setAdapter(adapter);
    }

    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month;
            int FixedMonth = month + 1;
            day_x = dayOfMonth;
            choosedDate = day_x+ "."+ FixedMonth + "."+year_x;
            String[] Date = {choosedDate};
            changeDateSpinner(Date);
            setDoctors();
        }
    };

    private void setDoctors() {
        String[] bindValues = {"Key", "Position", "Date"};
        String[] error = {"Нет доступных врачей"};
        String[] Values = {"EnableExecute", spinner.getSelectedItem().toString(), choosedDate};
        SQL_AvailableDoctors SA = new SQL_AvailableDoctors(bindValues, Values, "https://autisticapi.shadowsparky.ru/getavailabledocs.php");
        ArrayList<SQL_Engine> res = SA.CatchResult();
        this.docNumbers = new String[res.size() + 1];
        this.docsData = new String[res.size() + 1];
        this.docsData[0] = "Выберите врача";
        for (int i = 0; i < res.size(); i++){
            if (((SQL_AvailableDoctors)res.get(i)).get_Result() == null) {
                String TMPStr = ((SQL_AvailableDoctors)res.get(i)).getFirst_Name() + " " + ((SQL_AvailableDoctors)res.get(i)).getLast_Name() + " " + ((SQL_AvailableDoctors)res.get(i)).getPathronymic();
                if (!TMPStr.equals("null null null")) {
                    docsData[i+1] = (TMPStr);
                    docNumbers[i+1] = ((SQL_AvailableDoctors) res.get(i)).getDoctor_Number();
                }
            }
        }
        if (docsData[1] == null) {
            this.docsData = error;
            Toast.makeText(this, R.string.DoctorsSelectFail, Toast.LENGTH_SHORT).show();
        }
        fillAvaliableDocs();
    }

    private void fillAvaliableDocs(){
        DocSpinner = findViewById(R.id.DoctorsSpinner);
        ArrayAdapter<String> adapter = customSpinner.throwCustomSpinner(this, android.R.layout.simple_spinner_item, docsData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DocSpinner.setAdapter(adapter);
        DocSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Выбор врача
                Toast.makeText(AddAppointmentMenu.this, docNumbers[position] + " " + DocSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
