package primr.apps.eurakacachet.ryme.ryme.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.injection.ApplicationContext;

/**
 * Created by GURU on 1/27/2016.
 */
public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "ryme_pref_file";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

}
