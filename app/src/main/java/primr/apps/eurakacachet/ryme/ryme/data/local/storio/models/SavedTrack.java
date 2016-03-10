package primr.apps.eurakacachet.ryme.ryme.data.local.storio.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.DownloadedTracksTable;

@StorIOSQLiteType(table = DownloadedTracksTable.TABLE_NAME)
public class SavedTrack implements Parcelable {

    @Nullable
    @StorIOSQLiteColumn(name = DownloadedTracksTable.COLUMN_ID, key = true)
    Long id;

    @NonNull
    @StorIOSQLiteColumn(name = DownloadedTracksTable.COLUMN_UUID)
    String uuid;

    @NonNull
    @StorIOSQLiteColumn(name = DownloadedTracksTable.COLUMN_TITLE)
    String title;

    @NonNull
    @StorIOSQLiteColumn(name = DownloadedTracksTable.COLUMN_ARTIST)
    String artist;

    @NonNull
    @StorIOSQLiteColumn(name = DownloadedTracksTable.COLUMN_PATH)
    String path;

    @NonNull
    @StorIOSQLiteColumn(name = DownloadedTracksTable.COLUMN_EXTENSION)
    String extension;

    @Nullable
    @StorIOSQLiteColumn(name = DownloadedTracksTable.COLUMN_DURATION)
    Long duration;

    @Nullable
    @StorIOSQLiteColumn(name = DownloadedTracksTable.COLUMN_COVER)
    String cover;

    @Nullable
    public Long id() {
        return id;
    }

    @NonNull
    public String uuid() {
        return uuid;
    }

    @NonNull
    public String title() {
        return title;
    }

    @NonNull
    public String artist() {
        return artist;
    }

    @NonNull
    public String path() {
        return path;
    }

    @Nullable
    public Long duration() {
        return duration;
    }

    @Nullable
    public String cover() {
        return cover;
    }

    @NonNull
    public String extention() {
        return extension;
    }

    public static SavedTrack newTrack(@Nullable Long id, @NonNull String uuid, @NonNull String title,
                                        @NonNull String artist, @NonNull String path, @Nullable Long duration,
                                        @Nullable String cover, @NonNull String extension) {
        SavedTrack track = new SavedTrack();
        track.id = id;
        track.uuid = uuid;
        track.title = title;
        track.duration = duration;
        track.artist = artist;
        track.path = path;
        track.cover = cover;
        track.extension = extension;
        return track;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.uuid);
        dest.writeString(this.title);
        dest.writeString(this.artist);
        dest.writeString(this.path);
        dest.writeString(this.extension);
        dest.writeValue(this.duration);
        dest.writeString(this.cover);
    }

    public SavedTrack() {
    }

    protected SavedTrack(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.uuid = in.readString();
        this.title = in.readString();
        this.artist = in.readString();
        this.path = in.readString();
        this.extension = in.readString();
        this.duration = (Long) in.readValue(Long.class.getClassLoader());
        this.cover = in.readString();
    }

    public static final Parcelable.Creator<SavedTrack> CREATOR = new Parcelable.Creator<SavedTrack>() {
        public SavedTrack createFromParcel(Parcel source) {
            return new SavedTrack(source);
        }

        public SavedTrack[] newArray(int size) {
            return new SavedTrack[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SavedTrack track = (SavedTrack) o;

        if (id != null ? !id.equals(track.id) : track.id != null) return false;
        if (!uuid.equals(track.uuid)) return false;
        if (!title.equals(track.title)) return false;
        if (!artist.equals(track.artist)) return false;
        if (!path.equals(track.path)) return false;
        if (!extension.equals(track.extension)) return false;
        return !(duration != null ? !duration.equals(track.duration) : track.duration != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + uuid.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + artist.hashCode();
        result = 31 * result + path.hashCode();
        result = 31 * result + extension.hashCode();
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SavedTrack{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", path='" + path + '\'' +
                ", extension='" + extension + '\'' +
                ", duration=" + duration +
                ", cover='" + cover + '\'' +
                '}';
    }
}
