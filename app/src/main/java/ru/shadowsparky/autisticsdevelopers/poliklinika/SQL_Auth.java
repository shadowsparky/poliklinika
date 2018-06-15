package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import okhttp3.Response;

public class SQL_Auth extends SQL_Engine implements ISQL {
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
