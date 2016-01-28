package primr.apps.eurakacachet.ryme.ryme.injection.component;


import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;
import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.SyncService;
import primr.apps.eurakacachet.ryme.ryme.data.local.DatabaseHelper;
import primr.apps.eurakacachet.ryme.ryme.data.local.PreferencesHelper;
import primr.apps.eurakacachet.ryme.ryme.data.remote.RymeService;
import primr.apps.eurakacachet.ryme.ryme.injection.ApplicationContext;
import primr.apps.eurakacachet.ryme.ryme.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    RymeService rymeService();
    PreferencesHelper preferenceHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    Bus eventBus();
}
