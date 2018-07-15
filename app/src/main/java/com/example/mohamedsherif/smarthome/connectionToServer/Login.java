package com.example.mohamedsherif.smarthome.connectionToServer;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.example.mohamedsherif.smarthome.LoginActivity;
import com.example.mohamedsherif.smarthome.MyHome;

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

public class Login extends AsyncTask<String, Void, String> {

    AlertDialog alertDialog;
    private Context context;
    private View mProgressBarLogin;

    String url_login = "http://mosaied07.000webhostapp.com/API/loginAndRegister.php";

    public Login(Context ctx, View progressBarLogin) {
        context = ctx;
        mProgressBarLogin = progressBarLogin;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        try {
            URL url = new URL(url_login);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            if (type.equals("login")) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        showProgress(true);
                    }
                });
                return performLoginOperation(httpURLConnection, params);
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
        // Log.v(context.getClass().getSimpleName(), result);
        showProgress(false);
        if (result.equals("\"" + LoginActivity.emailString + "\"")) {
            Intent intent = new Intent(context, MyHome.class);
            context.startActivity(intent);
        } else {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }

    public String performLoginOperation(HttpURLConnection httpURLConnection, String... params) {
        String user_name = params[1];
        String password = params[2];
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("submit", "login_api");
            jsonObject.put("username", user_name);
            jsonObject.put("password", password);
            final String post_data = jsonObject.toString();

            bufferedWriter.write(post_data);
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
        } catch (JSONException e) {
            return "JSONException";
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressBarLogin.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressBarLogin.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressBarLogin.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressBarLogin.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressBarLogin.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressBarLogin.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressBarLogin.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressBarLogin.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
