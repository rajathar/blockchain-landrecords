package com.amazonaws.youruserpools;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class HttpUtils {

     static JSONObject resp;
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

    public static JSONObject invokeService(String id) {
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
        return  resp;

    }

    public static JSONObject TransferOwnership(String id,String RecipeintName,String RecipentID) {

        RequestParams rp = new RequestParams();

        rp.add("assetId",id);
        rp.add("ownerId",RecipeintName);
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
        return  resp;

    }



}
