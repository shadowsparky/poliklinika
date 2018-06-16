package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

public class
SQLThread extends AsyncTask<SQL_Engine, Integer, ArrayList<SQL_Engine>> {

    @Override
    protected ArrayList<SQL_Engine> doInBackground(SQL_Engine... objects) {
        String stringResponse = null;
        Response response = objects[0].Post();
        if (response != null) {
//            Response response = objects[0].Post();
            try {
                stringResponse = response.body().string();
                if (stringResponse == null) return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONArray jarr = objects[0].parseJSON(stringResponse);
            ArrayList<SQL_Engine> result = objects[0].fromJson(jarr);
            return result;
        } else { return null; }
    }
    @Override
    protected void onPostExecute(ArrayList<SQL_Engine> s) {
        super.onPostExecute(s);
    }
}