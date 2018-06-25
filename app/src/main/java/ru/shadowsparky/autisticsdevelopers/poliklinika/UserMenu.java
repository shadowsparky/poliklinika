package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class UserMenu extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private AppointmentFragment _appointmentFrag = new AppointmentFragment();
    private ServiceFragment _serviceFrag = new ServiceFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setTitle(R.string.My_Appointments);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        FragmentTransaction ftrans = getFragmentManager().beginTransaction();
        ftrans.replace(R.id.container, _appointmentFrag).commit();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        int id = menuItem.getItemId();
                        FragmentTransaction ftrans = getFragmentManager().beginTransaction();
                        if (id == R.id.myAppointments){
                            ftrans.replace(R.id.container, _appointmentFrag);

                            Toast.makeText(getBaseContext(), "Appointments click", Toast.LENGTH_SHORT).show();
                        } else if (id == R.id.myServices){
                            ftrans.replace(R.id.container, _serviceFrag);
                            Toast.makeText(getBaseContext(), "Services click", Toast.LENGTH_SHORT).show();
                        } ftrans.commit();

                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                });
        navigationView.setCheckedItem(R.id.myAppointments);
    }

    @Override
    public void onClick(View v) {
        Intent addAppointment = new Intent(this, AddAppointmentMenu.class);
        startActivity(addAppointment);
    }

    public void DropSavedData(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exititem:
                Auth_Menu.getsPref().edit().clear().commit();
                Intent i = new Intent(this, Auth_Menu.class);
                startActivity(i);
                finish();
                Toast.makeText(this, R.string.account_exit, Toast.LENGTH_SHORT).show();
                Shorcuts_Create sc = new Shorcuts_Create(this);
                sc.setShortcut(false);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exitmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
