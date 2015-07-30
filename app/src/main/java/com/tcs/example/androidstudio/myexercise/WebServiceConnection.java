package com.tcs.example.androidstudio.myexercise;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by JAVAXIAN on 24/05/15.
 */

public class WebServiceConnection {


    private JSONObject jsonObject = null;
    private String jsonString = null;

    //Define URL of webservice
    final String URL_WEBSERVICE = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";

    //Method to connect with webservice and return boolean to verify success to consume webservice
    public boolean getResponseService(){

        boolean statusResponse = true;
        String jsonResponse = "{'response':'null'}";
        int timeOut = 3000;

        Log.i("START CONNECTION", "Initiate WebService Connection");

        try{

            //Establish connection and set parameters
            HttpParams httpparams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpparams,timeOut);
            HttpClient httpclient = new DefaultHttpClient(httpparams);
            HttpPost httppost = new HttpPost(URL_WEBSERVICE);

            //Execute http client and get response
            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity httpentity = httpresponse.getEntity();

            //Asign Json response to String
            jsonResponse = EntityUtils.toString(httpentity);



        //Catch exceptions
        }catch (UnsupportedEncodingException e)
        {
            Log.i("EXCEPTION", "UNSUPPORTEDENCODINGEXCEPTION");
            jsonResponse = "{'response':'unsupportedexception'}";
            e.printStackTrace();
            statusResponse = false;
        }
        catch (ClientProtocolException e)
        {
            Log.i("EXCEPTION", "CLIENTPROTOCOLEXCEPTION");
            jsonResponse = "{'response':'clientprotocolexception'}";
            e.printStackTrace();
            statusResponse = false;
        }
        catch (IOException e)
        {
            Log.i("EXCEPTION", "IOEXCEPTION");
            jsonResponse = "{'response':'ioexception'}";
            e.printStackTrace();
            statusResponse = false;
        }
        catch(Exception e)
        {
            Log.i("EXCEPTION", e.toString());
            jsonResponse = "{'response':'exception'}";
            e.printStackTrace();
            statusResponse = false;
        }

        //Log.i("RESPONSE",jsonResponse);

        //Convert String json response to JSONObject response

        try {
            jsonObject = new JSONObject(jsonResponse);
            jsonString = jsonResponse;
        } catch (JSONException e) {
            e.printStackTrace();
            statusResponse = false;
        }

        return statusResponse;

    }

    public JSONObject getJSONObject(){

        return jsonObject;
    }

    public String getJSONString(){

        return jsonString;
    }

    public void saveJSONResponse(){
/*
        try {
            FileOutputStream fileout=openFileOutput("jsonCache.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("");
            outputWriter.close();



        } catch (Exception e) {
            e.printStackTrace();
        }

*/

    }

}
