package com.example.mohamedsherif.smarthome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.example.mohamedsherif.smarthome.connectionToServer.GetData;
import com.example.mohamedsherif.smarthome.connectionToServer.UpdateData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Gate extends AppCompatActivity {

    private Switch mSwitchGate;
    private JSONObject jsonObjectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate);
        mSwitchGate = findViewById(R.id.switch_gate);

        JSONParser parser = new JSONParser();
        try {
            jsonObjectData = (JSONObject) parser.parse(GetData.data);
            String gateState = (String) jsonObjectData.get("main_gate");
            boolean b = false;
            b = MyHome.getBoolean(gateState);
            mSwitchGate.setChecked(b);
        } catch (ParseException e) {
            Log.v(Gate.class.getSimpleName(), e.toString());
        }
    }

    public void updateGate(View view) {
        boolean b = mSwitchGate.isChecked();
        int i = MyHome.getInteger(b);
        jsonObjectData.put("main_gate", i + "");
        jsonObjectData.put("submit", "updateHomeData");
        jsonObjectData.put("username", LoginActivity.emailString);

        UpdateData updateData = new UpdateData(this);
        updateData.execute("update", jsonObjectData.toString());
    }
}
