package primr.apps.eurakacachet.ryme.ryme.data.local.storio.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.FollowedArtistsTable;

@StorIOSQLiteType(table = FollowedArtistsTable.TABLE_NAME)
public class FollowedArtist{

    @Nullable
    @StorIOSQLiteColumn(name = FollowedArtistsTable.COLUMN_ID, key = true)
    Long id;

    @NonNull
    @StorIOSQLiteColumn(name = FollowedArtistsTable.COLUMN_UUID)
    String uuid;

    @Nullable
    @StorIOSQLiteColumn(name = FollowedArtistsTable.COLUMN_NAME)
    String name;

    @Nullable
    public Long  id() {
        return id;
    }

    @NonNull
    public String uuid() {
        return uuid;
    }

    @Nullable
    public String  name() {
        return name;
    }


    public static FollowedArtist newArtist(@Nullable Long id, @NonNull String uuid,
                                                @Nullable String name) {
        FollowedArtist artist = new FollowedArtist();
        artist.id = id;
        artist.uuid = uuid;
        artist.name = name;
        return artist;
    }

    @Override
    public String toString() {
        return "FollowedArtist{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                '}';
    }
}
