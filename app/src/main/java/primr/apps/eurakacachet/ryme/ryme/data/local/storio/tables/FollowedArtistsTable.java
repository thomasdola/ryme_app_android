package primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables;


import android.support.annotation.NonNull;

public class FollowedArtistsTable {

    public static final String TABLE_NAME = "followed_artists";

    @NonNull
    public static final String COLUMN_ID = "_id";
    @NonNull
    public static final String COLUMN_UUID = "uuid";
    @NonNull
    public static final String COLUMN_NAME = "name";

    private FollowedArtistsTable(){
        throw new IllegalStateException("No instances please");
    }

    @NonNull
    public static String getCreateTableQuery(){
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_UUID + " TEXT NOT NULL," +
                COLUMN_NAME + " TEXT" +
                ");";
    }

}
