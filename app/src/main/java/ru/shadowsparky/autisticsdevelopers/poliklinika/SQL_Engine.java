package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class SQL_Engine implements ISQL {
    private String[] bindValues;
    private String[] Values;
    private String url;
    private Context _context;

    public SQL_Engine(String[] bindValues, String[] values, String url) {
        this.bindValues = bindValues;
        this.Values = values;
        this.url = url;
    }

    public void set_context(Context _context) {
        this._context = _context;
    }
    @Override
    public Response Post(){
        Response response = null;
        try {
            OkHttpClient client = new OkHttpClient();
            MultipartBody.Builder fb = new MultipartBody.Builder();
            fb.setType(MultipartBody.FORM);
            for (int i = 0; i < bindValues.length; i++) {
                fb.addFormDataPart(bindValues[i], Values[i]);
            }
            RequestBody req_body = fb.build();
            Request request = new Request.Builder().url(url).post(req_body).build();
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    @Override
    public JSONArray parseJSON(String response){
        JSONArray jArr = null;
        try {
            jArr = new JSONArray(response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return jArr;
    }
    @Override
    public ArrayList<SQL_Engine> CatchResult(){
        ArrayList<SQL_Engine> res = null;
        try{
            this.set_context(_context);
            SQLThread thread = new SQLThread();
            thread.execute(this);
            res = thread.get();
            if (res == null) { return null; }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    @Override
    public ArrayList<SQL_Engine> fromJson(final JSONArray array) {
        final ArrayList<SQL_Engine> result = new ArrayList<SQL_Engine>();

        for (int index = 0; index < array.length(); ++index) {
            try {
                final SQL_Engine SE = parseJson(array.getJSONObject(index));
                if (null != SE) result.add(SE);
            } catch (final JSONException ignored) {
            }
        }
        return result;
    }
    public Context get_context(){
        return _context;
    }
    public abstract SQL_Engine parseJson(final JSONObject object);
    public abstract boolean HandleResult(SQL_Engine se);
}

class SQL_Auth extends SQL_Engine implements ISQL {
    private String result;

    public SQL_Auth(String[] bindValues, String[] values, String url) {
        super(bindValues, values, url);
    }

    @Override
    public SQL_Engine parseJson(JSONObject object) {
        SQL_Auth SA = new SQL_Auth(null, null, null);
        try {
            SA.result = object.getString("Result");
        } catch(Exception e){
            e.printStackTrace();
        }
        return SA;
    }
    @Override
    public boolean HandleResult(SQL_Engine se) {
        SQL_Auth result = (SQL_Auth) se;
        switch(result.result){
            case "Not Exists":
                Toast.makeText(super.get_context(), "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
                break;
            case "Exists":
                Toast.makeText(super.get_context(), "Авторизация прошла успешно!", Toast.LENGTH_SHORT).show();
                return true;
        }
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

class SQL_AvailableDoctors extends SQL_Engine implements ISQL {
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

class SQL_AvailableSpec extends SQL_Engine implements ISQL {
    private String result;
    private Context _context;

    public SQL_AvailableSpec(String[] bindValues, String[] values, String url) {
        super(bindValues, values, url);
    }

    @Override
    public void set_context(Context _context) {
        this._context = _context;
    }

    public String getResult(){return result;}

    @Override
    public SQL_Engine parseJson(JSONObject object) {
        SQL_AvailableSpec SA = new SQL_AvailableSpec(null, null, null);
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
    @Override
    public ArrayList<SQL_Engine> CatchResult() {
        return super.CatchResult();
    }
}

class SQL_AvailableTime extends SQL_Engine implements ISQL {
    private String result;
    public SQL_AvailableTime (String[] bindValues, String[] values, String url) {
        super(bindValues, values, url);
    }
    public String getResult(){return result;}

    @Override
    public SQL_Engine parseJson(JSONObject object) {
        SQL_AvailableTime SA = new SQL_AvailableTime (null, null, null);
        try {
            SA.result = object.getString("Result");
        } catch(Exception e){
            e.printStackTrace();
        }
        return SA;
    }
    @Override
    public boolean HandleResult(SQL_Engine se) {
        SQL_AvailableTime result = (SQL_AvailableTime ) se;
        switch(result.result){
            case "Not Exists":
                Toast.makeText(super.get_context(), "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
                break;
            case "Exists":
                Toast.makeText(super.get_context(), "Авторизация прошла успешно!", Toast.LENGTH_SHORT).show();
                return true;
        }
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