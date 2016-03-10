package primr.apps.eurakacachet.ryme.ryme.utils.player.manager;


import android.app.Application;
import android.app.Service;
import android.support.annotation.NonNull;

import com.devbrackets.android.playlistcore.manager.ListPlaylistManager;

import primr.apps.eurakacachet.ryme.ryme.RymeApplication;
import primr.apps.eurakacachet.ryme.ryme.utils.player.dataInterface.PCOnlineAudioItemInterface;
import primr.apps.eurakacachet.ryme.ryme.utils.player.service.PCOnlineAudioPlayerService;

public class PCOnlinePlaylistManager extends ListPlaylistManager<PCOnlineAudioItemInterface> {

    @NonNull
    @Override
    protected Application getApplication() {
        return RymeApplication.getApplication();
    }

    @NonNull
    @Override
    protected Class<? extends Service> getMediaServiceClass() {
        return PCOnlineAudioPlayerService.class;
    }
}
