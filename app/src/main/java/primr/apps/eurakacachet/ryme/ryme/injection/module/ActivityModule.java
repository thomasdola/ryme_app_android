package primr.apps.eurakacachet.ryme.ryme.injection.module;

import android.app.Activity;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    Activity mActivity;

    public ActivityModule(Activity activity){
        mActivity = activity;
    }

    @Provides
    Activity providesActivity(){
        return mActivity;
    }

    @Singleton
    @Provides
    Context providesContext(){
        return mActivity;
    }
}
