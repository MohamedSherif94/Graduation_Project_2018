package com.example.mohamedsherif.smarthome.connectionToServer;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

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

public class UpdateData extends AsyncTask<String, Void, String> {
    AlertDialog alertDialog;
    private Context context;

    String dataUpdated = "";
    String url_home_data = "http://mosaied07.000webhostapp.com/API/home.php";

    public UpdateData(Context ctx) {
        context = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Update Status");
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        dataUpdated = params[1];
        try {
            URL url = new URL(url_home_data);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            if (type.equals("update")) {
                return updateData(httpURLConnection);
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
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    public String updateData(HttpURLConnection httpURLConnection) {
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(dataUpdated);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
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
        }
    }
}
