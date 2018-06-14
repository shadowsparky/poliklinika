package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddAppointmentMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment_menu);
        getSupportActionBar().setTitle("Запись на прием");
        FillAvailableDocs(SQL_AvailableSpecFun());
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
                Toast.makeText(getBaseContext(), "test - " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public String[] SQL_AvailableSpecFun() {
        String[] BestResult = null;
        ArrayList<SQL_Engine> res = null;
        SQL_AvailableSpec result = null;
        String[] bindValues = {"Key"};
        String[] values = {"EnableExecute"};
        SQL_AvailableSpec SA = new SQL_AvailableSpec(bindValues, values, "https://autisticapi.shadowsparky.ru/getavailablespec.php");
        SA.set_context(this);
        SQLThread thread = new SQLThread();
        thread.execute(SA);
        try {
            res = thread.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BestResult = new String[res.size()];
        for (int i = 0; i < res.size(); i++) {
            BestResult [i] = ((SQL_AvailableSpec) res.get(i)).getResult();
        }
        return BestResult;
    }
}
