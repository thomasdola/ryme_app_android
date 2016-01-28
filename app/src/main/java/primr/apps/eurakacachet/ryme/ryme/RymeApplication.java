package primr.apps.eurakacachet.ryme.ryme;

import android.app.Application;
import android.content.Context;

import primr.apps.eurakacachet.ryme.ryme.injection.component.ApplicationComponent;
import primr.apps.eurakacachet.ryme.ryme.injection.component.DaggerApplicationComponent;
import primr.apps.eurakacachet.ryme.ryme.injection.module.ApplicationModule;


public class RymeApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate(){
        super.onCreate();
    }

    public static RymeApplication get(Context context){
        return (RymeApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent(){
        if( mApplicationComponent == null ){
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent){
        mApplicationComponent = applicationComponent;
    }
}
