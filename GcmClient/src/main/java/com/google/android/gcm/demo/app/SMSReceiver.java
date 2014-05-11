package com.google.android.gcm.demo.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dainiusjocas on 5/11/14.
 */
public class SMSReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("SMSRECEIVER", "SMS was received");
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);

                nameValuePairs.add(new BasicNameValuePair("from", msgs[i].getOriginatingAddress()));
                nameValuePairs.add(new BasicNameValuePair("body", msgs[i].getMessageBody().toString()));

                str += "SMS from " + msgs[i].getOriginatingAddress();
                str += " :";
                str += msgs[i].getMessageBody().toString();
                str += "\n";
            }
            //---display the new SMS message---
            //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

            // make a http rest call the system
            postData(nameValuePairs, context);
        }
    }

    public void postData(List<NameValuePair> nameValuePairs, Context context) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.sfdj14.inf.unibz.it/rest_services/r/sms/receive");

        try {
            // Add your data
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            Toast.makeText(context, httppost.getParams().toString(), Toast.LENGTH_LONG).show();

            // Execute HTTP Post Request
            // HttpResponse response = httpclient.execute(httppost);

//        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }
}
