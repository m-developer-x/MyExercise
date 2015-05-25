package com.tcs.example.androidstudio.myexercise;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by JAVAXIAN on 24/05/15.
 */
public class WebServiceConnection {


    final String URL_WEBSERVICE = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";

    public JSONObject getResponseService(){

        String jsonResponse = "{'response':'null'}";

        Log.i("START CONNECTION", "Initiate WebService Connection");

        try{

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(URL_WEBSERVICE);
            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity httpentity = httpresponse.getEntity();

            jsonResponse = EntityUtils.toString(httpentity);


        }catch (UnsupportedEncodingException e)
        {
            Log.i("EXCEPTION", "UNSUPPORTEDENCODINGEXCEPTION");
            jsonResponse = "{'response':'unsupportedexception'}";
            e.printStackTrace();
        }
        catch (ClientProtocolException e)
        {
            Log.i("EXCEPTION", "CLIENTPROTOCOLEXCEPTION");
            jsonResponse = "{'response':'clientprotocolexception'}";
            e.printStackTrace();
        }
        catch (IOException e)
        {
            Log.i("EXCEPTION", "IOEXCEPTION");
            jsonResponse = "{'response':'ioexception'}";
            e.printStackTrace();
        }
        catch(Exception e)
        {
            Log.i("EXCEPTION", e.toString());
            jsonResponse = "{'response':'exception'}";
            e.printStackTrace();
        }

        //Log.i("RESPONSE",jsonResponse);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject;

    }

}
