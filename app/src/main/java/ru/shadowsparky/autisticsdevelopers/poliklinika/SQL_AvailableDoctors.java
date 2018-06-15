package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Response;

public class SQL_AvailableDoctors extends SQL_Engine implements ISQL {
    private String Doctor_Number;
    private String First_Name;
    private String Last_Name;
    private String Pathronymic;
    private String result;
    private Context _context;
    private String[] bindValues;
    private String[] values;

    public String getDoctor_Number() {
        return Doctor_Number;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public String getPathronymic(){
        return Pathronymic;
    }

    @Override
    public void set_context(Context _context) {
        this._context = _context;
    }

    public String get_Result(){return result;}
    SQL_AvailableDoctors(String[] bindValues, String[] Values, String url) {
        super(bindValues, Values, url);
    }
    @Override
    public SQL_Engine parseJson(JSONObject object) {
        SQL_AvailableDoctors SA = new SQL_AvailableDoctors(null, null, null);
        try {
            SA.Doctor_Number = object.getString("Doctor_Number");
            SA.First_Name = object.getString("Doctor_FirstName");
            SA.Last_Name = object.getString("Doctor_LastName");
            SA.Pathronymic = object.getString("Doctor_Pathronymic");
            SA.result = object.getString("Result");
        } catch(Exception e){
            e.printStackTrace();
        }
        return SA;
    }
    @Override
    public boolean HandleResult(SQL_Engine se) {
        return false;
    }
    @Override
    public ArrayList<SQL_Engine> fromJson(JSONArray array) {
        return super.fromJson(array);
    }
    @Override
    public Response Post() {
        return super.Post();
    }
    @Override
    public ArrayList<SQL_Engine> CatchResult() {
        return super.CatchResult();
    }
}
