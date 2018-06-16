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

import java.sql.Time;
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
    private int testkostil1 = 0;
    private int testkostil2 = 0;
    private int testkostil3 = 0;
    Spinner DateSpinner;
    Spinner TimeSpinner;
    DatePickerDialog DPD;
    String[] docNumbers;
    String[] docsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment_menu);
        DateSpinner = findViewById(R.id.DateSpinner);
        DocSpinner = findViewById(R.id.DoctorsSpinner);
        TimeSpinner = findViewById(R.id.TimeSpinner);
        DateSpinner.setEnabled(false);
        DocSpinner.setEnabled(false);
        TimeSpinner.setEnabled(false);
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
                if ((!spinner.getSelectedItem().toString().equals("Выберите специальность")) && (!spinner.getSelectedItem().toString().equals(""))) {
                    clearSpinner(DateSpinner);
                    clearSpinner(DocSpinner);
                    clearSpinner(TimeSpinner);
                    selectedSpecID = position;
                    DateSpinner.setEnabled(true);
                    String[] txt = {"Выберите дату"};
                    changeDateSpinner(txt);
                }
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
        if ((!DateSpinner.getSelectedItem().toString().equals("Выберите дату")) && (!DateSpinner.getSelectedItem().toString().equals(""))) {
            clearSpinner(DocSpinner);
            clearSpinner(TimeSpinner);
            DocSpinner.setEnabled(true);
            setDoctors();
        }
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
            choosedDate = day_x + "." + FixedMonth + "." + year_x;
            String[] Date = {choosedDate};
            changeDateSpinner(Date);
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
                if ((!DocSpinner.getSelectedItem().toString().equals("Выберите врача")) && (!DocSpinner.getSelectedItem().toString().equals("")) && !DocSpinner.getSelectedItem().toString().equals("Нет доступных врачей")) {
                    clearSpinner(TimeSpinner);
                    currentDocNumber = docNumbers[position];
                    TimeSpinner.setEnabled(true);
                    fillAvailableTime();
                }
                //Toast.makeText(AddAppointmentMenu.this, docNumbers[position] + " " + DocSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private String[] GetTime(){
        String[] bindValues = {"Key", "Doctor_Number", "Date"};
        String[] AvailableTime = null;
        String[] Values = {"EnableExecute", currentDocNumber, choosedDate};
        SQL_AvailableTime SA = new SQL_AvailableTime(bindValues, Values, "https://autisticapi.shadowsparky.ru/getavailabletime.php");
        ArrayList<SQL_Engine> res = SA.CatchResult();
        if (res != null) {
            AvailableTime = new String[res.size() + 1];
            AvailableTime[0] = "Выберите время";
            for (int i = 0; i < res.size(); i++) {
                AvailableTime[i + 1] = ((SQL_AvailableTime) res.get(i)).getResult();
            }
            if (AvailableTime[1].equals("У выбранного Вами врача нет доступного времени для записи")) {
                String[] error = {"RaiseError"};
                Toast.makeText(this, "У выбранного Вами врача нет доступного времени для записи", Toast.LENGTH_SHORT).show();
                return error;
            }
        } else {
            String[] error = {"RaiseError"};
            Toast.makeText(this, "В процессе выбора времени произошла критическая ошибка", Toast.LENGTH_SHORT).show();
            return error;
        }
        return AvailableTime;
    }

    private void fillAvailableTime() {
        String[] TimeArray = GetTime();
        if (!TimeArray[0].equals("RaiseError")) {
            ArrayAdapter<String> adapter = customSpinner.throwCustomSpinner(this, android.R.layout.simple_spinner_item, TimeArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            TimeSpinner.setAdapter(adapter);
        }
    }
    public void AddAppointmentButtonClick(View view) {
        if (SpinnersChecker()) {
            String ExecuteResult = getAddAppointmentResult();
            if (!ExecuteResult.equals("RaiseError")) {
                Toast.makeText(this, "Запись на приём успешно добавлена", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    private String getAddAppointmentResult(){
        String[] bindValues = {"Key", "Login", "Doctor_Number", "Date", "Time"};
        String result = null;
        String[] Values = {"EnableExecute", Auth_Menu.getLogin(), currentDocNumber, choosedDate, TimeSpinner.getSelectedItem().toString()};
        SQL_AddAppointment SA = new SQL_AddAppointment(bindValues, Values, "https://autisticapi.shadowsparky.ru/addAppointment.php");
        ArrayList<SQL_Engine> res = SA.CatchResult();
        if (res != null) {
            result = ((SQL_AddAppointment) res.get(0)).getResult();
            if (result.equals("Регистрирование на прием невозможно. Попробуйте выбрать другое время") ||
                    (result.equals("Во время регистрации на прием произошла техническая ошибка. Обратитесь к системному администратору")) ||
                        (result.equals("На эту дату приема больше нет. Пожалуйста, выберите другую дату"))){
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                return "RaiseError";
            }
        } else {
            Toast.makeText(this, "Во время добавления записи произошла критическая ошибка. Проверьте ваше интернет соединение", Toast.LENGTH_SHORT).show();
            return "RaiseError";
        }
        return result;
    }

    private Boolean checkSpecSpinner(){
        if (spinner != null) {
            if ((spinner.getSelectedItem().toString().equals("Выберите специальность")) || (spinner.getSelectedItem().toString().equals(""))) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    private Boolean checkDateSpinner(){
        if (DateSpinner != null) {
            if ((DateSpinner.getSelectedItem().toString().equals("Выберите дату")) || (DateSpinner.getSelectedItem().toString().equals("Выберите дату"))) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    private Boolean checkDocSpinner(){
        if (DocSpinner != null) {
            if ((DocSpinner.getSelectedItem().toString().equals("Выберите врача")) || (DocSpinner.getSelectedItem().toString().equals("")) || (DocSpinner.getSelectedItem().toString().equals("Нет доступных врачей"))) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    private Boolean checkTimeSpinner(){
        if (TimeSpinner != null) {
            if ((TimeSpinner.getSelectedItem().toString().equals("")) || (TimeSpinner.getSelectedItem().toString().equals("Выберите время"))) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private Boolean SpinnersChecker() {
        if (checkSpecSpinner()){
            if (checkDateSpinner()) {
                if (checkDocSpinner()){
                    if (checkTimeSpinner()){
                        return true;
                    } else {Toast.makeText(this, "Пожалуйста, выберите информацию о необходимом времени", Toast.LENGTH_SHORT).show(); return false;}
                } else { Toast.makeText(this, "Пожалуйста, выберите информацию о необходимом враче", Toast.LENGTH_SHORT).show(); return false;}
            } else { Toast.makeText(this, "Пожалуйста, выберите информацию о необходимой дате", Toast.LENGTH_SHORT).show(); return false;}
        } else { Toast.makeText(this, "Пожалуйста, выберите информацию о необходимой специализации", Toast.LENGTH_SHORT).show(); return false;}
    }
}