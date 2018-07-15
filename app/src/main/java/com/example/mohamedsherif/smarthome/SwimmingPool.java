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

public class SwimmingPool extends AppCompatActivity {

    private Switch mSwitchSwimmingPoolCover;
    private JSONObject jsonObjectData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swimming_pool);

        mSwitchSwimmingPoolCover = findViewById(R.id.switch_swimming_pool_cover);

        JSONParser parser = new JSONParser();
        try {
            jsonObjectData = (JSONObject) parser.parse(GetData.data);
            String swimmingPoolCoverState = (String) jsonObjectData.get("pool_cover");

            boolean b = false;
            b = MyHome.getBoolean(swimmingPoolCoverState);
            mSwitchSwimmingPoolCover.setChecked( b );
        } catch (ParseException e) {
            Log.v(SwimmingPool.class.getSimpleName(),e.toString());
        }
    }

    public void updateSwimmingPoolCover(View view) {
        boolean b = mSwitchSwimmingPoolCover.isChecked();
        int i = MyHome.getInteger(b);
        jsonObjectData.put( "pool_cover", i +"");
        jsonObjectData.put("submit", "updateHomeData");
        jsonObjectData.put("username", LoginActivity.emailString);

        UpdateData updateData = new UpdateData(this);
        updateData.execute("update", jsonObjectData.toString());
    }
}
