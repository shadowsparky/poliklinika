/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DeployedAppointmentActivity extends AppCompatActivity {
    private View _AppointmentView;
    private View _PacientView;
    private View _DoctorView;
    private View _SpecializationView;
    private View _DateView;
    private View _TimeView;
    private View _CabinetView;

    public void set_AppointmentView(View _AppointmentView) {
        this._AppointmentView = _AppointmentView;
    }

    public void set_PacientView(View _PacientView) {
        this._PacientView = _PacientView;
    }

    public void set_DoctorView(View _DoctorView) {
        this._DoctorView = _DoctorView;
    }

    public void set_SpecializationView(View _SpecializationView) {
        this._SpecializationView = _SpecializationView;
    }

    public void set_DateView(View _DateView) {
        this._DateView = _DateView;
    }

    public void set_TimeView(View _TimeView) {
        this._TimeView = _TimeView;
    }

    public void set_CabinetView(View _CabinetView) {
        this._CabinetView = _CabinetView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deployed_appointment);
        getSupportActionBar().setTitle("О записи");
    }
}
