package primr.apps.eurakacachet.ryme.ryme.utils.player.manager;


import android.app.Application;
import android.app.Service;

import com.devbrackets.android.exomedia.manager.EMPlaylistManager;

import primr.apps.eurakacachet.ryme.ryme.RymeApplication;
import primr.apps.eurakacachet.ryme.ryme.utils.player.dataInterface.OnlineAudioItemInterface;
import primr.apps.eurakacachet.ryme.ryme.utils.player.service.OnlineAudioPlayerService;

public class OnlineAudioPlaylistManager extends EMPlaylistManager<OnlineAudioItemInterface>{

    @Override
    protected Application getApplication() {
        return RymeApplication.getApplication();
    }

    @Override
    protected Class<? extends Service> getMediaServiceClass() {
        return OnlineAudioPlayerService.class;
    }
}
