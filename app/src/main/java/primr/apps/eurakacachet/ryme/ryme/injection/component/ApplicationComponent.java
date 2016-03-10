package primr.apps.eurakacachet.ryme.ryme.injection.component;


import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;
import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.utils.player.service.PCOnlineAudioPlayerService;
import primr.apps.eurakacachet.ryme.ryme.utils.services.RefreshTokenReceiver;
import primr.apps.eurakacachet.ryme.ryme.utils.services.SyncService;
import primr.apps.eurakacachet.ryme.ryme.data.local.SqlBrite.SqlBriteDatabaseHelper;
import primr.apps.eurakacachet.ryme.ryme.data.local.PreferencesHelper;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.StorioDatabaseHelper;
import primr.apps.eurakacachet.ryme.ryme.data.remote.RymeService;
import primr.apps.eurakacachet.ryme.ryme.injection.context.ApplicationContext;
import primr.apps.eurakacachet.ryme.ryme.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);
    void inject(RefreshTokenReceiver refreshTokenReceiver);

    @ApplicationContext Context context();
    Application application();
    RymeService rymeService();
    PreferencesHelper preferenceHelper();
    SqlBriteDatabaseHelper databaseHelper();
    StorioDatabaseHelper storioHelper();
    DataManager dataManager();
    Bus eventBus();

    void inject(PCOnlineAudioPlayerService pcOnlineAudioPlayerService);
}
