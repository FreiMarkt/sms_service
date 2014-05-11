package com.google.android.gcm.demo.app;

import android.telephony.SmsManager;

/**
 * Created by dainiusjocas on 5/11/14.
 */
public class SMSSender {

    /**
     * Private constructor
     */
    private SMSSender() {}

    /**
     * Factory method that always created new instance of this class.
     * @return
     */
    public static SMSSender newInstance() {
        return new SMSSender();
    }

    /**
     * Method that actually send the SMS
     * @param phoneNumber
     * @param securityCode
     */
    public void sendSMSWithSecurityCode(String phoneNumber, String securityCode) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, securityCode, null, null);
    }
}
