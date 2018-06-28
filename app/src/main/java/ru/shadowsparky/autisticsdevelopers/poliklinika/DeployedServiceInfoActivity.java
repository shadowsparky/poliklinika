/*
 * Copyright (c) Samsonov Eugene, 2018.
 */

package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class DeployedServiceInfoActivity extends AppCompatActivity {
    private TextView _idView;
    private TextView _pacientView;
    private TextView _cabinetView;
    private TextView _costView;
    private TextView _serviceView;
    private TextView _timeView;
    private TextView _dateView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deployed_service_info);
        _idView = findViewById(R.id.DeployedServiceIdTV);
        _pacientView = findViewById(R.id.DeployedServicePacientTV);
        _cabinetView = findViewById(R.id.DeployedServiceCabinetTV);
        _costView = findViewById(R.id.DeployedServiceCostTV);
        _serviceView = findViewById(R.id.DeployedServiceNameTV);
        _timeView = findViewById(R.id.DeployedServiceTime);
        _dateView = findViewById(R.id.DeployedServiceDate);
        Intent myIntent = getIntent();
        _idView.setText(_idView.getText() + myIntent.getStringExtra("ID"));
        _pacientView.setText(_pacientView.getText() + myIntent.getStringExtra("PacientInfo"));
        _cabinetView.setText(_cabinetView.getText() + myIntent.getStringExtra("CabinetInfo"));
        _costView.setText(_costView.getText() + myIntent.getStringExtra("CostInfo"));
        _serviceView.setText(_serviceView.getText() + myIntent.getStringExtra("ServiceInfo"));
        _timeView.setText(_timeView.getText() + myIntent.getStringExtra("Time"));
        _dateView.setText(_dateView.getText() + myIntent.getStringExtra("Date"));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Подробная информация");
        setSupportActionBar(toolbar);
    }
}
