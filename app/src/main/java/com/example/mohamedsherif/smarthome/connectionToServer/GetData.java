package com.example.mohamedsherif.smarthome.connectionToServer;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.mohamedsherif.smarthome.Gate;
import com.example.mohamedsherif.smarthome.Kitchen;
import com.example.mohamedsherif.smarthome.LivingRoom;
import com.example.mohamedsherif.smarthome.LoginActivity;
import com.example.mohamedsherif.smarthome.Rooms;
import com.example.mohamedsherif.smarthome.SwimmingPool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetData extends AsyncTask<String, Void, String> {
    private Context context;
    public static String data = "";
    String dataGeneral = "";

    String url_home_data = "http://mosaied07.000webhostapp.com/API/home.php";

    public GetData(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        dataGeneral = params[1];
        try {
            URL url = new URL(url_home_data);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            if (type.equals("getData")) {
                return getData(httpURLConnection);
            }
        } catch (MalformedURLException e) {
            return "Invalid Server URL";
        } catch (IOException e) {
            return "Invalid  HttpURLConnection";
        }
        return "said";
    }

    @Override
    protected void onPostExecute(String result) {
        //Log.v(context.getClass().getSimpleName(), "getData : " + result);
        data = result;
        switch (dataGeneral) {
            case "livingRoom":
                Intent intentLivingRoom = new Intent(context, LivingRoom.class);
                context.startActivity(intentLivingRoom);
                break;
            case "bedrooms":
                Intent intentRooms = new Intent(context, Rooms.class);
                context.startActivity(intentRooms);
                break;
            case "kitchen":
                Intent intentKitchen = new Intent(context, Kitchen.class);
                context.startActivity(intentKitchen);
                break;
            case "Gate":
                Intent intentGate = new Intent(context, Gate.class);
                context.startActivity(intentGate);
                break;
            case "swimmingPool":
                Intent intentSwimming = new Intent(context, SwimmingPool.class);
                context.startActivity(intentSwimming);
                break;
        }
    }

    public String getData(HttpURLConnection httpURLConnection) {
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("submit", "getHomeData");
            jsonObject.put("username", LoginActivity.emailString);
            final String post_data = jsonObject.toString();
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "", line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (UnsupportedEncodingException e) {
            return "UnsupportedEncodingException";
        } catch (IOException e) {
            return "Site OFF";
        } catch (JSONException e) {
            return "JSONException";
        }
    }
}
