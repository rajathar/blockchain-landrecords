package com.amazonaws.youruserpools;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.youruserpools.CognitoYourUserPoolsDemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LandRecordsActivity extends AppCompatActivity {

    private RecyclerView mLandRecyclerView;
    private LandAdapter mAdapter;
    private String mOWnerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_records);
        mLandRecyclerView=(RecyclerView)findViewById(R.id.land_recycler_view);
        mLandRecyclerView.setLayoutManager(new LinearLayoutManager(LandRecordsActivity.this));
        updateUI();

    }

    private void updateUI() {

           List<Land> lands= HttpUtils.ViewMyLandRecords(mOWnerID);

           if(lands!=null) {
               mAdapter = new LandAdapter(lands);
               mLandRecyclerView.setAdapter(mAdapter);
           }
           else
               Log.d("Lands","lands not gotten from HttpUtils");
    }


    private class LandHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView assetId,address,ownerId,ownerName;
        private Land mLand;
        public LandHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.content_land_asset, parent,false));
            assetId=(TextView)itemView.findViewById(R.id.textView1);
            address=(TextView)findViewById(R.id.textView2);
            ownerId=(TextView)itemView.findViewById(R.id.textView7);
            ownerName=(TextView)itemView.findViewById(R.id.textView8);
        }

        public void bind(Land land ){
            mLand=land;
            assetId.setText(mLand.getAssetId());
            address.setText(mLand.getAddress());
            ownerId.setText(mLand.getOwnerId());
            ownerName.setText(mLand.getOwnerName());
        }

        @Override
        public void onClick(View v) {
          // Toast.makeText(this,"Asset Id clicked", Toast.LENGTH_SHORT).show();

        }
    }

    private class LandAdapter extends RecyclerView.Adapter<LandHolder>
    {
        private List<Land> mLands;

        public LandAdapter(List<Land> lands)
        { mLands=lands;

        }

        @Override
        public LandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(LandRecordsActivity.this);
            return new LandHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(LandHolder holder, int position) {
            Land land=mLands.get(position);
           holder.bind(land);

        }

        @Override
        public int getItemCount() {
            return mLands.size();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==22)
        { Intent intent=data;
            String ownerid= intent.getStringExtra("OWNER").toString();
            mOWnerID=ownerid;

        }
    }
}
