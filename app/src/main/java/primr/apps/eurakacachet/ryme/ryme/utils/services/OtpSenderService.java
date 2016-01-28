package primr.apps.eurakacachet.ryme.ryme.utils.services;

import android.app.IntentService;
import android.content.Intent;


public class OtpSenderService extends IntentService {
    private static String TAG = OtpSenderService.class.getSimpleName();

    public OtpSenderService() {
        super("OtpSenderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String otp = intent.getStringExtra("otp");
            verifyOtp(otp);
        }
    }

    /**
     * Posting the OTP to server and activating the user
     *
     * @param otp otp received in the SMS
     */
    private void verifyOtp(String otp) {

    }
}
