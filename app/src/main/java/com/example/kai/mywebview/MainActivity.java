package com.example.kai.mywebview;


import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is able to parse a JSOn object to a Website and process its return value.
 */
public class MainActivity extends AppCompatActivity {


    /**
     * sets the layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *
     * Sets the ThreadPolice to allow building up connections in a main thread.
     * A JSon object is created with the inputs of the User and delivered to the make Request method
     * ,when the result is "OK a new Activity starts.
     *
     *
     * @param View button
     */
    public void input(View View)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        JSONObject obj = new JSONObject();
        EditText username=(EditText) findViewById(R.id.Textfeld_name);
        String benutzername = username.getText().toString();
        EditText password = (EditText) findViewById(R.id.Textfeld_pw);
        String passwort = password.getText().toString();
        try {
            obj.put("username", benutzername);
            obj.put("password", passwort);
        }
        catch (JSONException e) {}
        String jString = obj.toString();
        try {
            JSONObject jObj = new JSONObject(makeRequest("http://kais-macbook-pro/hello_world/api.php",jString));
            String result=(String) jObj.get("result");
            if (result.equals("ok"))
            {
                //username.setText("hat geklappt");
                Intent intent = new Intent(this,Web_Activity.class);
                startActivity(intent);
            }
        }
        catch (JSONException e)
        {

        }

    }

    /**
     *  This method builds up a connection to a website and post a json String towards it.
     *  It returns a Json String when it gets one from the website, otherwise it return a null String.
     *
     *
     * @param uri contains the URL of the Website
     * @param json the value, the Website returns
     * @return
     */
    public static String makeRequest(String uri, String json) {
        HttpURLConnection urlConnection;
        String result = null;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(json);
            writer.close();
            outputStream.close();

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}


