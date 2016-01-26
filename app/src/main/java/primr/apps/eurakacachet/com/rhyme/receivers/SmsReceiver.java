package primr.apps.eurakacachet.com.rhyme.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import primr.apps.eurakacachet.com.rhyme.services.OtpSenderService;

/**
 * Created by GURU on 1/25/2016.
 */
public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = SmsReceiver.class.getSimpleName();
    public static final String OTP_DELIMITER = ":";

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();

        try{
            if( bundle != null){

                Object[] pdusObj = (Object[])bundle.get("pdus");

                for (Object aPdusObj: pdusObj) {
                    SmsMessage currentSms = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String senderAddress = currentSms.getOriginatingAddress();
                    String message = currentSms.getDisplayMessageBody();
                    Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                    // if the SMS is not from our gateway, ignore the message
                    if ( ! senderAddress.toLowerCase().contains("SMS_ORIGIN".toLowerCase())){
                        return;
                    }

                    // verification code from sms
                    String verificationCode = getVerificationCode(message);
                    Log.e(TAG, "OTP received: " + verificationCode);

                    //Start a service intent that will send the OTP back to our server
                    Intent otpSenderService = new Intent(context, OtpSenderService.class);
                    otpSenderService.putExtra("otp", verificationCode);
                    context.startService(otpSenderService);
                }
            }
        }catch (Exception e){
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Getting the OTP from sms message body
     * ':' is the separator of OTP from the message
     */
    private String getVerificationCode(String message) {
        String code = null;
        int index = message.indexOf(OTP_DELIMITER);

        if( index != -1 ){
            int start = index + 2;
            int length = 6;
            code = message.substring(start, start + length);
        }

        return code;
    }
}
