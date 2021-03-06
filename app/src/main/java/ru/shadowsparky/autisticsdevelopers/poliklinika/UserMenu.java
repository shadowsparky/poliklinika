package ru.shadowsparky.autisticsdevelopers.poliklinika;

/*
* govnokod
*
*
* (c) Samsonov_Eugene, 2018.
*
* */
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class UserMenu extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        getSupportActionBar().setTitle(R.string.My_Appointments);
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
}
