package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ServicesList extends ListFragment {
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

        tv = (TextView) getActivity().findViewById(R.id.isServicesEmpty);
        String [] bindValues = {"Key", "Login"};
        String [] values = {"EnableExecute", Auth_Menu.getLogin()};
        if (getServicesList(result, bindValues, values)) return null;
        return result;
    }

    private boolean getServicesList(ArrayList<HashMap<String, String>> result, String[] bindValues, String[] values) {
        SQL_GetAllUserServices SG = new SQL_GetAllUserServices(bindValues, values, "https://autisticapi.shadowsparky.ru/getAllUserServices.php");
        SG.set_context(getActivity());
        ArrayList<SQL_Engine> res = SG.CatchResult();
        ids = new String[res.size()];
        if (!((SQL_GetAllUserServices)res.get(0)).getPacient_Policy_Number().equals("Записи отсутствуют")) {
            for (int i = 0; i < res.size(); i++) {
                ids[i] = ((SQL_GetAllUserServices) res.get(i)).getService_ID();
                HashMap<String, String> TMPResult = new HashMap<String, String>();
                TMPResult.put("Specialization",((SQL_GetAllUserServices) res.get(i)).getService_Name());
                TMPResult.put("Date and Time", ( "Время: "+((SQL_GetAllUserServices) res.get(i)).getService_Appointment_Time() + ", дата: " + ((SQL_GetAllUserServices) res.get(i)).getService_Appointment_Date()));
                result.add(TMPResult);
            }
            tv.setVisibility(View.INVISIBLE);
            getView().setVisibility(View.VISIBLE);
        } else {
            getView().setVisibility(View.INVISIBLE);
            tv.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
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
        String[] bindValues = {"Key", "AppointmentNumber"};
        String[] values = {"EnableExecute", ids[position]};
//        getAppointmentInfo(position, bindValues, values);
    }

//    private void getAppointmentInfo(int position, String[] bindValues, String[] values) {
//        SQL_GetAdditionalAppointmentInfo SGAAI = new SQL_GetAdditionalAppointmentInfo(bindValues, values, "https://autisticapi.shadowsparky.ru/getAdditionalAppointmentInfo.php");
//        ArrayList<SQL_Engine> res = SGAAI.CatchResult();
//        if (res != null) {
//            if (res.size() != 0) {
//                String [] TMPResultArray = {((SQL_GetAdditionalAppointmentInfo) res.get(0)).getPacientFirstName() + " " + ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getPacientLastName() + " " + ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getPacientPathronymic(),
//                        ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getDoctorFirstName() + " " + ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getDoctorLastName() + " " +
//                                ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getDoctorPathronymic(),
//                        ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getPosition(),
//                        ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getDate(),
//                        ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getTime(),
//                        ((SQL_GetAdditionalAppointmentInfo) res.get(0)).getCabinetNumber()
//                };
//                Intent i = new Intent(getActivity(), DeployedAppointmentActivity.class);
//                String[] valuesIntent = {"PacientInfo", "DoctorInfo", "Position", "Date", "Time", "CabinetNumber"};
//                putin(i, valuesIntent, TMPResultArray);
//                i.putExtra("AppointmentNumber", ids[position]);
//                startActivity(i);
//            } else {
//                Toast.makeText(getActivity(), "Запись не найдена", Toast.LENGTH_SHORT).show();}
//        } else {Toast.makeText(getActivity(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();}
//    }
//
//    private void putin(Intent i, String[] values, String...args){
//        for (int j = 0; j < args.length; j++){
//            i.putExtra(values[j], args[j]);
//        }
//    }
}