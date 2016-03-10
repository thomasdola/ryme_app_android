package primr.apps.eurakacachet.ryme.ryme.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.CategoriesTable;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.DownloadedTracksTable;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.FollowedArtistsTable;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.FollowedCategoriesTable;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.LikedTracksTable;
import primr.apps.eurakacachet.ryme.ryme.injection.context.ApplicationContext;


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
            Log.d("adapter", "DB Creating Called");
            db.execSQL(DownloadedTracksTable.getCreateTableQuery());
            db.execSQL(FollowedCategoriesTable.getCreateTableQuery());
            db.execSQL(FollowedArtistsTable.getCreateTableQuery());
            db.execSQL(CategoriesTable.getCreateTableQuery());
            db.execSQL(LikedTracksTable.getCreateTableQuery());
            db.setTransactionSuccessful();
            Log.d("adapter", "DB Created Called");
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
