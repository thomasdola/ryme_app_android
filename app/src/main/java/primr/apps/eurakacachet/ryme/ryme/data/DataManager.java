package primr.apps.eurakacachet.ryme.ryme.data;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.local.DatabaseHelper;
import primr.apps.eurakacachet.ryme.ryme.data.local.PreferencesHelper;
import primr.apps.eurakacachet.ryme.ryme.data.remote.RymeService;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.event.EventPostHelper;

/**
 * Created by GURU on 1/28/2016.
 */
public class DataManager {

    private final RymeService mRymeService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final EventPostHelper mEventPoster;

    @Inject
    public DataManager(RymeService ribotsService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper, EventPostHelper eventPosterHelper) {
        mRymeService = ribotsService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
        mEventPoster = eventPosterHelper;
    }

}
