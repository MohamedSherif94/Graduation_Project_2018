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

public class LivingRoom extends AppCompatActivity {

    private Switch mSwitchLight;
    //device1
    private Switch mSwitchDoor;

    JSONObject jsonObjectData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);

        mSwitchLight =  findViewById(R.id.switch_living_room_light);
        mSwitchDoor =  findViewById(R.id.switch_living_room_door);

        JSONParser parser = new JSONParser();
        try {
            //Log.v(Living_room.class.getSimpleName(),GetData.data);
            jsonObjectData = (JSONObject) parser.parse(GetData.data);
            jsonObjectData.put("submit", "updateHomeData");
            jsonObjectData.put("username", LoginActivity.emailString);
            String living_lights = (String) jsonObjectData.get("living_lights");
            String living_door = (String) jsonObjectData.get("device1");
            boolean b = false;

            b = MyHome.getBoolean(living_lights);
            mSwitchLight.setChecked( b );

            b = MyHome.getBoolean(living_door);
            mSwitchDoor.setChecked( b );
        } catch (ParseException e) {
            Log.v(LivingRoom.class.getSimpleName(),e.toString());
        }
    }

    public void updateLivingRoomLight(View view) {
        boolean b = mSwitchLight.isChecked();
        int i = MyHome.getInteger(b);
        jsonObjectData.put( "living_lights", i +"");

        UpdateData updateData = new UpdateData(this);
        updateData.execute("update", jsonObjectData.toString());
    }

    public void updateLivingRoomDoor(View view) {
        boolean b = mSwitchDoor.isChecked();
        int i = MyHome.getInteger(b);
        jsonObjectData.put( "device1", i +"");

        UpdateData updateData = new UpdateData(this);
        updateData.execute("update", jsonObjectData.toString());
    }
}
