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

public class Rooms extends AppCompatActivity {

    private Switch mSwitchLightRoom1;
    private Switch mSwitchLightRoom2;
    private Switch mSwitchLightRoom3;

    JSONObject jsonObjectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        mSwitchLightRoom1 = (Switch) findViewById(R.id.switch_light_room_1);
        mSwitchLightRoom2 = (Switch) findViewById(R.id.switch_light_room_2);
        mSwitchLightRoom3 = (Switch) findViewById(R.id.switch_light_room_3);

        JSONParser parser = new JSONParser();
        try {
            jsonObjectData = (JSONObject) parser.parse(GetData.data);
            String lightsRoom1 = (String) jsonObjectData.get("room1_lights");
            String lightsRoom2 = (String) jsonObjectData.get("room2_lights");
            String lightsRoom3 = (String) jsonObjectData.get("room3_lights");

            boolean b = false;
            b = MyHome.getBoolean(lightsRoom1);
            mSwitchLightRoom1.setChecked( b );

            b = MyHome.getBoolean(lightsRoom2);
            mSwitchLightRoom2.setChecked( b );

            b = MyHome.getBoolean(lightsRoom3);
            mSwitchLightRoom3.setChecked( b );

        } catch (ParseException e) {
            Log.v(Rooms.class.getSimpleName(),e.toString());
        }
    }

    public void updateLightRoom1(View view) {
        boolean b = mSwitchLightRoom1.isChecked();
        int i = MyHome.getInteger(b);
        jsonObjectData.put( "room1_lights", i +"");
        jsonObjectData.put("submit", "updateHomeData");
        jsonObjectData.put("username", LoginActivity.emailString);

        UpdateData updateData = new UpdateData(this);
        updateData.execute("update", jsonObjectData.toString());
    }

    public void updateLightRoom2(View view) {
        boolean b = mSwitchLightRoom2.isChecked();
        int i = MyHome.getInteger(b);
        jsonObjectData.put( "room2_lights", i +"");
        jsonObjectData.put("submit", "updateHomeData");
        jsonObjectData.put("username", LoginActivity.emailString);

        UpdateData updateData = new UpdateData(this);
        updateData.execute("update", jsonObjectData.toString());
    }

    public void updateLightRoom3(View view) {
        boolean b = mSwitchLightRoom3.isChecked();
        int i = MyHome.getInteger(b);
        jsonObjectData.put( "room3_lights", i +"");
        jsonObjectData.put("submit", "updateHomeData");
        jsonObjectData.put("username", LoginActivity.emailString);

        UpdateData updateData = new UpdateData(this);
        updateData.execute("update", jsonObjectData.toString());
    }
}
