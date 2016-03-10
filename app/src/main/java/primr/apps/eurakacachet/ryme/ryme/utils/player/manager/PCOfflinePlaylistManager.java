package primr.apps.eurakacachet.ryme.ryme.utils.player.manager;


import android.app.Application;
import android.app.Service;
import android.support.annotation.NonNull;

import com.devbrackets.android.playlistcore.manager.ListPlaylistManager;

import primr.apps.eurakacachet.ryme.ryme.RymeApplication;
import primr.apps.eurakacachet.ryme.ryme.utils.player.dataInterface.PCOfflineAudioItemInterface;
import primr.apps.eurakacachet.ryme.ryme.utils.player.service.PCOfflineAudioPlayerService;

public class PCOfflinePlaylistManager extends ListPlaylistManager<PCOfflineAudioItemInterface>{

    @NonNull
    @Override
    protected Application getApplication() {
        return RymeApplication.getApplication();
    }

    @NonNull
    @Override
    protected Class<? extends Service> getMediaServiceClass() {
        return PCOfflineAudioPlayerService.class;
    }
}
