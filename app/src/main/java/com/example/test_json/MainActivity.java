package com.example.test_json;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText searchCity;
    public  void searchCity (View view){
    Log.i("CityName",searchCity.getText().toString());
        DownloadJson obj = new DownloadJson();
        obj.execute("https://milkiyat.bangalore2.com/api/home/");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchCity = (EditText) findViewById(R.id.searchCityEditText);


    }
    public class DownloadJson extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... urls) {
            String results="";
            URL url;
            HttpURLConnection connection=null;
            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data=reader.read();
                while (data!=-1)
                {
                    char current = (char) data;
                    results+=current;
                    data= reader.read();

                }
                return  results;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String results) {
            super.onPostExecute(results);
            try {
                JSONObject jsonObject =new JSONObject(results);
                String categories = jsonObject.getString("categories");
                Log.i("Categoiries are", categories);
                JSONArray jsonArray = new JSONArray(categories);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonPart = jsonArray.getJSONObject(i);
                    Log.i("name", jsonPart.getString("name"));
                    Log.i("slug", jsonPart.getString("slug"));
                    Log.i("icon", jsonPart.getString("icon"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("WEBSITE CONTENT", results);
        }
    }
}