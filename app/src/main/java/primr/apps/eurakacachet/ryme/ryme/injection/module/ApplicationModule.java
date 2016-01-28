package primr.apps.eurakacachet.ryme.ryme.injection.module;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import primr.apps.eurakacachet.ryme.ryme.data.remote.RymeService;
import primr.apps.eurakacachet.ryme.ryme.injection.ApplicationContext;

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application providesApplication(){
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context providesContext(){
        return mApplication;
    }

    @Provides
    @Singleton
    Bus providesBus(){
        return new Bus();
    }

    @Provides
    @Singleton
    RymeService providesRymeService(){
        return RymeService.Creator.newRymeService();
    }
}
