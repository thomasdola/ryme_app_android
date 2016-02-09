package primr.apps.eurakacachet.ryme.ryme.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.injection.ApplicationContext;


public class DbOpenHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "ryme.db";
    public static final int DATABASE_VERSION = 2;

    @Inject
    public DbOpenHelper(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(Db.UserProfileTable.CREATE);
            //Add other tables here
            db.execSQL(Db.CategoryTable.CREATE);
            db.execSQL(Db.DownloadedTrackTable.CREATE);
            db.execSQL(Db.LikedTrackTable.CREATE);
            db.execSQL(Db.FollowedArtistTable.CREATE);
            db.execSQL(Db.FollowedCategoryTable.CREATE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
