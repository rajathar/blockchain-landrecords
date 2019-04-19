package com.amazonaws.youruserpools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.youruserpools.CognitoYourUserPoolsDemo.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import org.codehaus.jackson.type.ObjectMapper

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LandDetails extends AppCompatActivity {

    TextView assetId,address,assetEastCoordinatesId,assetSouthCoordinatesId,assetNorthCoordinatesId,assetWestCoordinatesId,ownerId,ownerName;
    Button fetchButton;
    EditText fetchassetId;
    LinearLayout linearLayout;
    JSONObject resp=null;
    String ASID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_details);

        fetchassetId=(EditText)findViewById(R.id.editTextAssetID);
        assetId=(TextView)findViewById(R.id.textView1);
        address=(TextView)findViewById(R.id.textView2);
        assetNorthCoordinatesId=(TextView)findViewById(R.id.textView3);
        assetEastCoordinatesId=(TextView)findViewById(R.id.textView4);
        assetWestCoordinatesId=(TextView)findViewById(R.id.textView5);
        assetSouthCoordinatesId=(TextView)findViewById(R.id.textView6);
        ownerId=(TextView)findViewById(R.id.textView7);
        ownerName=(TextView)findViewById(R.id.textView8);
        linearLayout=(LinearLayout)findViewById(R.id.LinearLay);
        linearLayout.setVisibility(View.INVISIBLE);

fetchassetId.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() == 0) {
            Toast.makeText(LandDetails.this,"Enter AssetId",Toast.LENGTH_SHORT).show();
            fetchassetId.setBackground(getDrawable(R.drawable.text_border_selector));
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
       ASID=fetchassetId.getText().toString();
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            Toast.makeText(LandDetails.this,"Enter AssetId",Toast.LENGTH_SHORT).show();
        }
    }
});

        fetchButton= (Button) findViewById(R.id.button_fetch);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                resp=HttpUtils.invokeService(ASID);
             if(resp!=null) {
                 try {
                     Map<String, Object> mapObj = new Gson().fromJson(
                             resp.get("data").toString(), new TypeToken<HashMap<String, Object>>() {
                             }.getType());
                     System.out.println("Printing from landDetails class" + mapObj.get("data"));


                /* Map<String, Object> datamapObj = new Gson().fromJson(
                         mapObj.get("data").toString(), new TypeToken<HashMap<String, Object>>() {
                         }.getType());
*/

                     linearLayout.setVisibility(View.VISIBLE);
                     assetId.setText(mapObj.get("assetId").toString());
                     address.setText(mapObj.get("address").toString());
                     assetNorthCoordinatesId.setText(mapObj.get("assetNorthCoordinatesId").toString());
                     assetSouthCoordinatesId.setText(mapObj.get("assetSouthCoordinatesId").toString());
                     assetEastCoordinatesId.setText(mapObj.get("assetEastCoordinatesId").toString());
                     assetWestCoordinatesId.setText(mapObj.get("assetWestCoordinatesId").toString());
                     ownerId.setText(mapObj.get("ownerId").toString());
                     ownerName.setText(mapObj.get("ownerName").toString());


                 } catch (Exception e) {
                     System.out.println("Json Parsing Failed");
                 }
             } else {

                 linearLayout.setVisibility(View.INVISIBLE);
                 Toast.makeText(LandDetails.this,"Land with the given ID is not registered.",Toast.LENGTH_LONG).show();
             }



            }
        });
    }
}
