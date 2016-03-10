package primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;
import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;

import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.LikedTrack;

public class LikedTracksTable {

    public static final String TABLE_NAME = "liked_tracks";

    @NonNull
    public static final String COLUMN_ID = "_id";
    @NonNull
    public static final String COLUMN_UUID = "uuid";
    @NonNull
    public static final String COLUMN_ARTIST = "artist";

    private LikedTracksTable(){
        throw new IllegalStateException("No instances please");
    }

    @NonNull
    public static String getCreateTableQuery(){
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_UUID + " TEXT NOT NULL," +
                COLUMN_ARTIST + " TEXT NOT NULL" +
                ");";
    }

    public static class LikedTrackPutResolver extends DefaultPutResolver<LikedTrack>{

        @NonNull
        @Override
        protected InsertQuery mapToInsertQuery(@NonNull LikedTrack object) {
            return InsertQuery.builder()
                    .table(LikedTracksTable.TABLE_NAME)
                    .build();
        }

        @NonNull
        @Override
        protected UpdateQuery mapToUpdateQuery(@NonNull LikedTrack object) {
            return UpdateQuery.builder()
                    .table(LikedTracksTable.TABLE_NAME)
                    .where("uuid = ?")
                    .whereArgs(object.uuid())
                    .build();
        }

        @NonNull
        @Override
        protected ContentValues mapToContentValues(@NonNull LikedTrack track) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_UUID, String.valueOf(track.uuid()));
            values.put(COLUMN_ARTIST, track.artist());
            return values;
        }
    }

    public static class LikeTrackGetResolver extends DefaultGetResolver<LikedTrack>{

        @NonNull
        @Override
        public LikedTrack mapFromCursor(@NonNull Cursor cursor) {
            return LikedTrack.newTrack(
                    Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID))),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UUID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTIST))
            );
        }
    }

    public static class LikedTrackDeleteResolver extends DefaultDeleteResolver<LikedTrack>{

        @NonNull
        @Override
        protected DeleteQuery mapToDeleteQuery(@NonNull LikedTrack object) {
            return DeleteQuery.builder()
                    .table(LikedTracksTable.TABLE_NAME)
                    .where("uuid = ?")
                    .whereArgs(object.uuid())
                    .build();
        }
    }

}
