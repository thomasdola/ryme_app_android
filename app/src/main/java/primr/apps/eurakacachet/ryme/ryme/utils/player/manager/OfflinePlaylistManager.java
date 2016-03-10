package primr.apps.eurakacachet.ryme.ryme.utils.player.manager;

import android.app.Application;
import android.app.Service;

import com.devbrackets.android.exomedia.manager.EMPlaylistManager;

import primr.apps.eurakacachet.ryme.ryme.RymeApplication;
import primr.apps.eurakacachet.ryme.ryme.utils.player.dataInterface.OfflineAudioItemInterface;
import primr.apps.eurakacachet.ryme.ryme.utils.player.service.OfflineAudioPlayerService;


public class OfflinePlaylistManager extends EMPlaylistManager<OfflineAudioItemInterface> {

    @Override
    protected Application getApplication() {
        return RymeApplication.getApplication();
    }

    @Override
    protected Class<? extends Service> getMediaServiceClass() {
        return OfflineAudioPlayerService.class;
    }
}
