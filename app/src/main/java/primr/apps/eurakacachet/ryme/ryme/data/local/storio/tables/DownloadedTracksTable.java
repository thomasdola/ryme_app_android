package primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables;


import android.support.annotation.NonNull;

public class DownloadedTracksTable {

    @NonNull
    public static final String TABLE_NAME = "downloaded_tracks";

    @NonNull
    public static final String COLUMN_ID = "_id";
    @NonNull
    public static final String COLUMN_UUID = "uuid";
    @NonNull
    public static final String COLUMN_ARTIST = "artist";
    @NonNull
    public static final String COLUMN_DURATION = "duration";
    @NonNull
    public static final String COLUMN_TITLE = "title";
    @NonNull
    public static final String COLUMN_COVER = "cover";
    @NonNull
    public static final String COLUMN_PATH = "path";
    @NonNull
    public static final String COLUMN_EXTENSION = "extension";

    private DownloadedTracksTable(){
        throw new IllegalStateException("No instances please");
    }

    @NonNull
    public static String getCreateTableQuery(){
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_UUID + " TEXT NOT NULL," +
                COLUMN_TITLE + " TEXT NOT NULL," +
                COLUMN_DURATION + " INTEGER," +
                COLUMN_ARTIST + " TEXT NOT NULL," +
                COLUMN_COVER + " TEXT," +
                COLUMN_PATH + " TEXT NOT NULL," +
                COLUMN_EXTENSION + " TEXT NOT NULL" +
                ");";
    }


}
