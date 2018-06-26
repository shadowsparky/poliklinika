package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.sql.SQLException;
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
    public ArrayList<SQL_Engine> CatchResult() {
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
                Toast.makeText(super.get_context(),"Неправильный  логин или пароль", Toast.LENGTH_SHORT).show();
//                Toast.makeText(super.get_context(), "Неправиль ный логин или пароль", Toast.LENGTH_SHORT).show();
                break;
            case "Exists":
                Toast.makeText(super.get_context(), "Авторизация  прошла успешно!", Toast.LENGTH_SHORT).show();
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

class SQL_AddAppointment extends SQL_Engine implements ISQL {
    private String result;
    public SQL_AddAppointment (String[] bindValues, String[] values, String url) {
        super(bindValues, values, url);
    }
    public String getResult(){return result;}

    @Override
    public SQL_Engine parseJson(JSONObject object) {
        SQL_AddAppointment SA = new SQL_AddAppointment (null, null, null);
        try {
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
class SQL_GetUserAppointments extends SQL_Engine implements ISQL {
    private String AppointmentNubmer;
    private String Time;
    private String Date;
    private String Position;
    public SQL_GetUserAppointments (String[] bindValues, String[] values, String url) {
        super(bindValues, values, url);
    }

    public String getAppointmentNumber(){return AppointmentNubmer;}
    public String getTime(){return Time;}
    public String getDate(){return Date;}
    public String getPosition(){return Position;}
    @Override
    public SQL_Engine parseJson(JSONObject object) {
        SQL_GetUserAppointments SA = new SQL_GetUserAppointments(null, null, null);
        try {
            SA.AppointmentNubmer = object.getString("Appointment_Number");
            SA.Time = object.getString("Time");
            SA.Date = object.getString("Date");
            SA.Position = object.getString("Position");
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
class SQL_GetAdditionalAppointmentInfo extends SQL_Engine implements ISQL {
    public String getPacientFirstName() {
        return pacientFirstName;
    }

    public String getPacientLastName() {
        return pacientLastName;
    }

    public String getPacientPathronymic() {
        return pacientPathronymic;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public String getDoctorPathronymic() {
        return doctorPathronymic;
    }

    public String getPosition() {
        return Position;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public String getCabinetNumber() {
        return CabinetNumber;
    }

    private String pacientFirstName;
    private String pacientLastName;
    private String pacientPathronymic;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorPathronymic;
    private String Position;
    private String Date;
    private String Time;
    private String CabinetNumber;
    public SQL_GetAdditionalAppointmentInfo  (String[] bindValues, String[] values, String url) {
        super(bindValues, values, url);
    }

    @Override
    public SQL_Engine parseJson(JSONObject object) {
        SQL_GetAdditionalAppointmentInfo SA = new SQL_GetAdditionalAppointmentInfo(null, null, null);
        try {
            SA.pacientFirstName= object.getString("0");
            SA.pacientLastName= object.getString("1");
            SA.pacientPathronymic= object.getString("2");
            SA.doctorFirstName = object.getString("3");
            SA.doctorLastName = object.getString("4");
            SA.doctorPathronymic = object.getString("5");
            SA.Position = object.getString("6");
            SA.Date = object.getString("7");
            SA.Time = object.getString("8");
            SA.CabinetNumber = object.getString("9");
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

class SQL_DropAppointment extends SQL_Engine implements ISQL {
    public SQL_DropAppointment(String[] bindValues, String[] values, String url) {
        super(bindValues, values, url);
    }
    private String result;

    public String getResult() {
        return result;
    }

    @Override
    public SQL_Engine parseJson(JSONObject object) {
        SQL_DropAppointment SA = new SQL_DropAppointment(null, null, null);
        try {
            SA.result= object.getString("0");
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

class SQL_CreateUser extends SQL_Engine implements ISQL {
    public SQL_CreateUser(String[] bindValues, String[] values, String url) {
        super(bindValues, values, url);
    }
    private String result;

    public String getResult() {
        return result;
    }

    @Override
    public SQL_Engine parseJson(JSONObject object) {
        SQL_CreateUser SA = new SQL_CreateUser(null, null, null);
        try {
            SA.result= object.getString("Result");
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
    public ArrayList<SQL_Engine> CatchResult(){
        return super.CatchResult();
    }
}

class SQL_GetAllUserServices extends SQL_Engine implements ISQL {
    public SQL_GetAllUserServices (String[] bindValues, String[] values, String url) {
        super(bindValues, values, url);
    }

    public String getPacient_Policy_Number() {
        return Pacient_Policy_Number;
    }

    public String getService_Name() {
        return Service_Name;
    }

    public String getService_Appointment_Date() {
        return Service_Appointment_Date;
    }

    public String getService_Appointment_Time() {
        return Service_Appointment_Time;
    }
    public String getService_ID() {
        return Service_ID;
    }

    private String Service_Name;
    private String Service_Appointment_Date;
    private String Service_Appointment_Time;
    private String Pacient_Policy_Number;
    private String Service_ID;



    @Override
    public SQL_Engine parseJson(JSONObject object) {
        SQL_GetAllUserServices SA = new SQL_GetAllUserServices(null, null, null);
        try {
            SA.Service_ID = object.getString("Appointment_ID");
            SA.Pacient_Policy_Number = object.getString("Policy_Number");
            SA.Service_Name = object.getString("Service_Name");
            SA.Service_Appointment_Date = object.getString("Date");
            SA.Service_Appointment_Time = object.getString("Time");
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
    public ArrayList<SQL_Engine> CatchResult(){
        return super.CatchResult();
    }
}