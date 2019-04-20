package com.amazonaws.youruserpools;

import android.content.Intent;
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
//import org.codehaus.jackson.type.ObjectMapper

import org.json.JSONObject;

public class LandDetails extends AppCompatActivity {

    TextView assetId,address,assetSouthCoordinatesId,assetNorthCoordinatesId,ownerId,ownerName;
    Button fetchButton,MapButton;
    EditText fetchassetId;
    LinearLayout linearLayout;
    Double length,breadth,latitude,longitude;
    JSONObject resp=null;
    Land mLand;
    String ASID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_details);

        fetchassetId=(EditText)findViewById(R.id.editTextAssetID);
        assetId=(TextView)findViewById(R.id.textView1);
        address=(TextView)findViewById(R.id.textView2);
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

        MapButton=(Button) findViewById(R.id.MAPbutton);
        MapButton.setVisibility(View.INVISIBLE);
        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandDetails.this, MapsActivity.class);
                intent.putExtra("Latitude",latitude);
                intent.putExtra("Longitude",longitude);
                // intent.setComponent(SupportMapFragment.this,MapsActivity.class);
                startActivity(intent);
                // Snackbar.make(view, "see the Map for your Area", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
            }
        });


        fetchButton= (Button) findViewById(R.id.button_fetch);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLand=HttpUtils.invokeService(ASID);
             if(mLand!=null) {

                 MapButton.setVisibility(View.VISIBLE);

                 linearLayout.setVisibility(View.VISIBLE);
                     assetId.setText(mLand.getAssetId());
                     address.setText(mLand.getAddress());
                     length=Double.valueOf(mLand.getLength());
                     breadth=Double.valueOf(mLand.getBreadth());
                     latitude=Double.valueOf(mLand.getLatitude());
                     longitude=Double.valueOf(mLand.getLongitude());
                     ownerId.setText(mLand.getOwnerId());
                     ownerName.setText(mLand.getOwnerName());

             } else {

                 linearLayout.setVisibility(View.INVISIBLE);
                 Toast.makeText(LandDetails.this, "Land with the given ID is not registered.", Toast.LENGTH_LONG).show();
             }


            }
        });
    }
}
