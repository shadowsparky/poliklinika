package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import okhttp3.Response;

public class SQL_AvailableSpec extends SQL_Engine implements ISQL {
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
