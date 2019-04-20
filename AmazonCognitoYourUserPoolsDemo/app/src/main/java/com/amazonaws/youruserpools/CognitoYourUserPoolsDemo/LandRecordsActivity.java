package com.amazonaws.youruserpools.CognitoYourUserPoolsDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class LandRecordsActivity extends AppCompatActivity {

    private RecyclerView mLandRecyclerView;
    private LandAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_records);
        mLandRecyclerView=(RecyclerView)findViewById(R.id.land_recycler_view);
        mLandRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI();

    }

    private void updateUI() {

        //write the code to get the hashmap<> from json object
        //mAdapter = new LandAdapter(/*pass the jsonobject hashmap here*/);
        mLandRecyclerView.setAdapter(mAdapter);
    }
    private class LandHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView assetId,address,assetNorthCoordinatesId,assetEastCoordinatesId,assetWestCoordinatesId,assetSouthCoordinatesId,ownerId,ownerName;
        private Land mLand;
        public LandHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.content_land_asset,parent,false));
            assetId=(TextView)findViewById(R.id.textView1);
            address=(TextView)findViewById(R.id.textView2);
            ownerId=(TextView)findViewById(R.id.textView7);
            ownerName=(TextView)findViewById(R.id.textView8);
        }

        public void bind(Land land ){
            mLand=land;
            assetId.setText(mLand.getAssetId());
            address.setText(mLand.getAddress());
            assetNorthCoordinatesId.setText(mLand.getAssetNorthCoordinatesId());
            assetEastCoordinatesId.setText(mLand.getAssetEastCoordinatesId());
            assetWestCoordinatesId.setText(mLand.getAssetWestCoordinatesId());
            assetSouthCoordinatesId.setText(mLand.getAssetSouthCoordinatesId());
            ownerId.setText(mLand.getOwnerId());
            ownerName.setText(mLand.getOwnerName());
        }

        @Override
        public void onClick(View v) {
          //  Toast.makeText(this,"Asset Id clicked", Toast.LENGTH_SHORT).show();

        }
    }

    private class LandAdapter extends RecyclerView.Adapter<LandHolder>
    {
        private HashMap<String, JSONObject> mLands;

        public LandAdapter(HashMap<String, JSONObject> lands)
        { mLands=lands;

        }

        @Override
        public LandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(LandHolder holder, int position) {
           // Land land=mLands.get(position);
           // holder.bind(land);

        }

        @Override
        public int getItemCount() {
            return mLands.size();
        }
    }
}
