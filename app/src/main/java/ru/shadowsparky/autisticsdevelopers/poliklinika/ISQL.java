package ru.shadowsparky.autisticsdevelopers.poliklinika;

import org.json.JSONArray;
import java.util.ArrayList;
import okhttp3.Response;

public interface ISQL {
    Response Post();
    JSONArray parseJSON(String response);
    ArrayList<SQL_Engine> fromJson(final JSONArray array);
    boolean HandleResult(SQL_Engine se);
    ArrayList<SQL_Engine> CatchResult();
}
