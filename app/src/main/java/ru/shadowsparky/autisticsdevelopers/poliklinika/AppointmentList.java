package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppointmentList extends ListFragment {
    private String [] ids = null;
    TextView tv = null;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setAppointments();
    }

    private void setAppointments() {
        ArrayList<HashMap<String, String>> data = throwListResult();
        if (data != null) {
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, android.R.layout.simple_list_item_2,
                    new String[] {"Specialization", "Date and Time"},
                    new int[]{android.R.id.text1, android.R.id.text2});
            setListAdapter(adapter);
        }
    }

    private ArrayList<HashMap<String, String>> throwListResult(){
        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

        tv = (TextView) getActivity().findViewById(R.id.isEmptyTV);
        String [] bindValues = {"Key", "Login"};
        String [] values = {"EnableExecute", Auth_Menu.getLogin()};
        SQL_GetUserAppointments SG = new SQL_GetUserAppointments(bindValues, values, "https://autisticapi.shadowsparky.ru/getalluserappointments.php");
        SG.set_context(getActivity());
        ArrayList<SQL_Engine> res = SG.CatchResult();
        ids = new String[res.size()];
        if (!((SQL_GetUserAppointments)res.get(0)).getAppointmentNumber().equals("Записи отсутствуют")) {
            for (int i = 0; i < res.size(); i++) {
                ids[i] = ((SQL_GetUserAppointments) res.get(i)).getAppointmentNumber();
                HashMap<String, String> TMPResult = new HashMap<String, String>();
                TMPResult.put("Specialization",((SQL_GetUserAppointments) res.get(i)).getPosition());
                TMPResult.put("Date and Time", ( "Время: "+((SQL_GetUserAppointments) res.get(i)).getTime() + ", дата: " + ((SQL_GetUserAppointments) res.get(i)).getDate()));
                result.add(TMPResult);
            }
            tv.setVisibility(View.INVISIBLE);
            getView().setVisibility(View.VISIBLE);
        } else {
            getView().setVisibility(View.INVISIBLE);
            tv.setVisibility(View.VISIBLE);
            return null;
        }
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        setAppointments();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String[] result = new String[9];
        String[] bindValues = {"Key", "AppointmentNumber"};
        String[] values = {"EnableExecute", ids[position]};
        SQL_GetAdditionalAppointmentInfo SGAAI = new SQL_GetAdditionalAppointmentInfo(bindValues, values, "https://autisticapi.shadowsparky.ru/getAdditionalAppointmentInfo.php");
        ArrayList<SQL_Engine> res = SGAAI.CatchResult();
        if (res != null) {
            result[0] = ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getPacientFirstName() + " " + ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getPacientLastName() +
                    " " + ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getPacientPathronymic();
            result[1] = ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getDoctorFirstName() + " " + ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getDoctorLastName() + " " +
                    ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getDoctorPathronymic();
            result[2] = ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getPosition();
            result[3] = ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getDate();
            result[4] = ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getTime();
            result[5] = ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getCabinetNumber();
            Intent i = new Intent(getActivity(), DeployedAppointmentActivity.class);
            i.putExtra("PacientInfo", result[0]);
            i.putExtra("DoctorInfo", result[1]);
            i.putExtra("Position", result[2]);
            i.putExtra("Date", result[3]);
            i.putExtra("Time", result[4]);
            i.putExtra("CabinetNumber", result[5]);
            i.putExtra("AppointmentNumber", ids[position]);
            startActivity(i);
        } else {Toast.makeText(getActivity(), "Во время соединения с сервером произошла ошибка. Проверьте своё интернет соединение", Toast.LENGTH_SHORT).show();}
    }
}
