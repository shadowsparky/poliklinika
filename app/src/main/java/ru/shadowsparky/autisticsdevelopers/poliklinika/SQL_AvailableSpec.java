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
    @Override
    public void set_context(Context _context) {
        this._context = _context;
    }

    public String getResult(){return result;}

    SQL_AvailableSpec(String[] bindValues, String[] Values, String url) {
        super(bindValues, Values, url);
    }
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
    public String[] QueryResult() {
        String[] BestResult = null;
        ArrayList<SQL_Engine> res = null;
        SQL_AvailableSpec result = null;
        try {
            String[] bindValues = {"Key"};
            String[] values = {"EnableExecute"};
            SQL_AvailableSpec SA = new SQL_AvailableSpec(bindValues, values, "https://autisticapi.shadowsparky.ru/getavailablespec.php");
            SA.set_context(_context);
            SQLThread thread = new SQLThread();
            thread.execute(SA);
            try {
                res = thread.get();
                if (res == null) {return null;}
            } catch (Exception e) {
                e.printStackTrace();
            }
            BestResult = new String[res.size()];
            for (int i = 0; i < res.size(); i++) {
                BestResult[i] = ((SQL_AvailableSpec) res.get(i)).getResult();
            }
        } catch (Exception e) {
            Toast.makeText(_context, "Во время выполнения запроса проищзошла ошибка. Проверьте свое подключение к интернету", Toast.LENGTH_LONG).show();
        }
        return BestResult;
    }
}
