package com.qts.gopik_money.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MySMSBroadcastReceiver  extends BroadcastReceiver {
    private OtpReceiveListener otpReceiveListener;
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";

    public void setOtpReceiveListener(OtpReceiveListener otpReceiveListener) {
        this.otpReceiveListener = otpReceiveListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        /*Log.d("RECEIVER", "onReceive:MY_BROAD_CAST_RECEIVER**"+intent.getAction());
       if(SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())){
            Bundle extras = intent.getExtras();
            if(extras != null){
                Status status = (Status)extras.get(SmsRetriever.EXTRA_STATUS);
                switch (status != null ? status.getStatusCode() : 0){//status != null ? status.getStatusCode() : 0
                    case CommonStatusCodes.SUCCESS:
                        String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                        Log.d("quantumware****", "onReceive: "+message);
                        if(otpReceiveListener != null){
                            otpReceiveListener.onOtpReceived(message);
                        }
                        break;
                    case CommonStatusCodes.TIMEOUT:
                        // Waiting for SMS timed out (5 minutes)
                        // Handle the error ...
                        Log.d("quantumware****", "onReceive: "+status.getResolution());
                        if(otpReceiveListener != null){
                            otpReceiveListener.onSmsTimOut();
                        }
                        break;
                    case CommonStatusCodes.API_NOT_CONNECTED:

                        if (otpReceiveListener != null) {
                            otpReceiveListener.onOtpReceivedError("API NOT CONNECTED");
                        }

                        break;

                    case CommonStatusCodes.NETWORK_ERROR:

                        if (otpReceiveListener != null) {
                            otpReceiveListener.onOtpReceivedError("NETWORK ERROR");
                        }

                        break;

                    case CommonStatusCodes.ERROR:

                        if (otpReceiveListener != null) {
                            otpReceiveListener.onOtpReceivedError("SOME THING WENT WRONG");
                        }

                        break;
                }

            }
        }*/

        Log.i(TAG, "Intent received: " + intent.getAction());

        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1) {
                    Toast.makeText(context, "Message recieved: " + messages[0].getMessageBody(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Message recieved: " + messages[0].getMessageBody());
                }
            }
        }
    }

    public interface OtpReceiveListener{
        void onOtpReceived(String otp);
        void onSmsTimOut();
        void onOtpReceivedError(String error);

    }
}

