package com.example.mohamedsherif.smarthome;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.example.mohamedsherif.smarthome.connectionToServer.GetData;
import com.example.mohamedsherif.smarthome.connectionToServer.UpdateData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Kitchen extends AppCompatActivity {

    private Switch mSwitchLightKitchen;
    private Switch mSwitchGasKitchen;
    private JSONObject jsonObjectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        mSwitchLightKitchen = findViewById(R.id.switch_light_kitchen);
        mSwitchGasKitchen =  findViewById(R.id.switch_gas_kitchen);

        JSONParser parser = new JSONParser();
        try {
            jsonObjectData = (JSONObject) parser.parse(GetData.data);
            String lightsKitchen = (String) jsonObjectData.get("kitchen_lights");
            String gasKitchen = (String) jsonObjectData.get("gas");

            boolean b = false;
            b = MyHome.getBoolean(lightsKitchen);
            mSwitchLightKitchen.setChecked( b );

            b = MyHome.getBoolean(gasKitchen);
            mSwitchGasKitchen.setChecked( b );
        } catch (ParseException e) {
            Log.v(Kitchen.class.getSimpleName(),e.toString());
        }
    }

    public void updateLightKitchen(View view) {
        boolean b = mSwitchLightKitchen.isChecked();
        int i = MyHome.getInteger(b);
        jsonObjectData.put( "kitchen_lights", i +"");
        jsonObjectData.put("submit", "updateHomeData");
        jsonObjectData.put("username", LoginActivity.emailString);

        UpdateData updateData = new UpdateData(this);
        updateData.execute("update", jsonObjectData.toString());
    }

    public void updateGasKitchen(View view) {
        boolean b = mSwitchGasKitchen.isChecked();
        int i = MyHome.getInteger(b);
        jsonObjectData.put( "gas", i +"");
        jsonObjectData.put("submit", "updateHomeData");
        jsonObjectData.put("username", LoginActivity.emailString);

        UpdateData updateData = new UpdateData(this);
        updateData.execute("update", jsonObjectData.toString());
    }
}

