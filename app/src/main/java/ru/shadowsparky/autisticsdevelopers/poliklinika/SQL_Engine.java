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
