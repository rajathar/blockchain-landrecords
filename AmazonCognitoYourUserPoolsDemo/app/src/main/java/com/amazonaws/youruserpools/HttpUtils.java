package com.amazonaws.youruserpools;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class HttpUtils {

     static JSONObject resp;
     static  JSONArray JSONarrayResp;
     static int SC;
    private static final String BASE_URL = "http://ec2-13-58-19-178.us-east-2.compute.amazonaws.com:3000/propert";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);

    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }



    public static Land invokeService(String id) {
        RequestParams rp = new RequestParams();

        rp.add("assetId",id);



        HttpUtils.post("/getLandRecordsForAssetId", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    System.out.println("Response here");
                    System.out.println(serverResp);
                    resp=serverResp;
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

          @Override
          public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline
                resp=response;
                System.out.println(response);

            }
        });
        return  convertJsontoLAndObj(resp);

    }

    private static Land convertJsontoLAndObj(JSONObject resp1) {
         Land sland=new Land();


        if(resp1!=null) {
            try {
                Map<String, Object> mapObj = new Gson().fromJson(
                        resp1.get("data").toString(), new TypeToken<HashMap<String, Object>>() {
                        }.getType());
                System.out.println("Printing from HttpuUtils class" + mapObj.get("data"));
                sland.setAssetId(mapObj.get("assetId").toString());
                sland.setAddress(mapObj.get("address").toString());
                sland.setLength(mapObj.get("length").toString());
                sland.setBreadth(mapObj.get("breadth").toString());
                sland.setLatitude(mapObj.get("latitude").toString());
                sland.setLongitude(mapObj.get("longitude").toString());
                sland.setOwnerId(mapObj.get("ownerId").toString());
                sland.setOwnerName(mapObj.get("ownerName").toString());
                return  sland;

            } catch (Exception e) {
                System.out.println("Json Parsing Failed");
                return null;
            }

        }
        else  return null;


    }

    private static List<Land> convertJsonListtoLAndList(JSONArray jsonArray1){
        List<Land> lands=new ArrayList<>();

        try{
            for (int i = 0; i < jsonArray1.length(); i++)
            {
                Gson gson = new Gson();
                Land sland = gson.fromJson(jsonArray1.getJSONObject(i).toString(), Land.class);
                Log.d("convertJson",""+sland);
                lands.add(sland);
            }
            return lands;
        }catch(Exception e){
            System.out.println("Error in converting json object");
            return null;
        }

    }

    public static int TransferOwnership(String id,String RecipentID,String RecipeintName) {

        RequestParams rp = new RequestParams();

        rp.add("assetId",id);
        rp.add("ownerId",RecipentID);
        rp.add("ownerName",RecipeintName);

        HttpUtils.post("/transferAsset", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    System.out.println("Response here");
                    System.out.println(serverResp);
                    resp = serverResp;
                    SC = statusCode;
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline
                resp = response;
                try {
                    SC = Integer.parseInt(response.get("code").toString());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return SC;
    }

    public static List<Land> ViewMyLandRecords(String OwnerID) {

        RequestParams rp = new RequestParams();
        rp.add("ownerId",OwnerID);

        HttpUtils.post("/getMyLandRecords", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                try {
                    //JSONObject serverResp = new JSONObject(response.toString());
                    //System.out.println("Response here");
                    //System.out.println(serverResp);
                    //resp=serverResp;

                    JSONArray jsonarrayobj=response.getJSONArray("data");
                    Log.d("asd", "---------------- this is This is the server resposne : " + jsonarrayobj);
                    JSONarrayResp=jsonarrayobj;
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Pull out the first event on the public timeline

//                JSONarrayResp = response;
//                try {
//                    System.out.println("Response here");
//                    System.out.println(JSONarrayResp);
//                    JSONarrayResp=JSONarrayResp;
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }


            }
        });
        return  convertJsonListtoLAndList(JSONarrayResp);

    }



}
