package primr.apps.eurakacachet.ryme.ryme.data.local.storio.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.LikedTracksTable;

@StorIOSQLiteType(table = LikedTracksTable.TABLE_NAME)
public class LikedTrack {

    @StorIOSQLiteColumn(name = LikedTracksTable.COLUMN_ID, key = true)
    Long id;

    @StorIOSQLiteColumn(name = LikedTracksTable.COLUMN_UUID)
    String uuid;

    @StorIOSQLiteColumn(name = LikedTracksTable.COLUMN_ARTIST)
    String artist;

    @Nullable
    public Long id() {
        return id;
    }

    @NonNull
    public String uuid() {
        return uuid;
    }

    @NonNull
    public String artist() {
        return artist;
    }


    @Override
    public String toString() {
        return "LikedTrack{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }

    public static LikedTrack newTrack(@Nullable Long id, @NonNull String uuid,
                      @NonNull String artist) {
        LikedTrack track = new LikedTrack();
        track.id = id;
        track.uuid = uuid;
        track.artist = artist;
        return track;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LikedTrack that = (LikedTrack) o;

        return uuid.equals(that.uuid);

    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
