package com.example.json;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonParsing extends AsyncTask<Void, Void, Void>{
    private static final String USG_URL = "https://www.weatherapi.com/docs/conditions.json";
    private String bufferdata = "";
    private String finalData= "";
    private String Data = "";

    @Override
    protected Void doInBackground(Void... voids) {

        URL url = null;
        try {
            url = new URL(USG_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                bufferdata = bufferdata + line;
            }

            JSONArray jsonArray = new JSONArray(bufferdata);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int code = jsonObject.getInt("code");
                String day = jsonObject.getString("day");
                String night = jsonObject.getString("night");
                int icon = jsonObject.getInt("icon");

                JSONArray languagesArray = jsonObject.getJSONArray("languages");
                for (int j = 0; j < languagesArray.length(); j++) {
                    JSONObject languageObject = languagesArray.getJSONObject(j);

                    String langName = languageObject.getString("lang_name");
                    String langIso = languageObject.getString("lang_iso");
                    String dayText = languageObject.getString("day_text");
                    String nightText = languageObject.getString("night_text");

                    finalData = "Code: " + code + "\n"
                            + "Day: " + day + "\n"
                            + "Night: " + night + "\n"
                            + "Icon: " + icon + "\n"
                            + "Language Name: " + langName + "\n"
                            + "Language ISO: " + langIso + "\n"
                            + "Day Text: " + dayText + "\n"
                            + "Night Text: " + nightText + "\n";

                    Data = Data + finalData + "\n------------------------\n";
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        // Assuming MainActivity has a static textView field
        MainActivity.textView.setText(this.Data);
    }
}
