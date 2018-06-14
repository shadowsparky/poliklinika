package ru.shadowsparky.autisticsdevelopers.poliklinika;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Response;

public class SQL_AvailableDoctors extends SQL_Engine implements ISQL {
    private String result;
    public String getResult(){return result;}
    SQL_AvailableDoctors(String[] bindValues, String[] Values, String url) {
        super(bindValues, Values, url);
    }
    @Override
    public SQL_Engine parseJson(JSONObject object) {
        SQL_AvailableDoctors SA = new SQL_AvailableDoctors(null, null, null);
        try {
            SA.result = object.getString("Position");
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
}
