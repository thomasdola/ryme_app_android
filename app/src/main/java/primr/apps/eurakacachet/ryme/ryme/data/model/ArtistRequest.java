package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ArtistRequest implements Parcelable {

    @NonNull
    String stage_name;

    @Nullable
    String avatar;

    @NonNull
    String uuid;

    @NonNull
    long yes;

    @NonNull
    long no;

    @NonNull
    long start_date;

    @NonNull
    long end_date;

    @NonNull
    String artist_channel;

    @NonNull
    String user_id;

    @NonNull
    boolean deja_vu;

    public static ArtistRequest newRequestView(@NonNull String stage_name, @Nullable String avatar,
                                               @NonNull String uuid, @NonNull long yes,
                                               @NonNull long no, @NonNull long start_date, @NonNull
                                               long end_date, @NonNull String artist_channel,
                                               @NonNull String user_id, @NonNull boolean deja_vu){
        ArtistRequest request = new ArtistRequest();
        request.stage_name = stage_name;
        request.avatar = avatar;
        request.uuid = uuid;
        request.yes = yes;
        request.no = no;
        request.start_date = start_date;
        request.end_date = end_date;
        request.artist_channel = artist_channel;
        request.user_id = user_id;
        request.deja_vu = deja_vu;
        return request;
    }


    public ArtistRequest() {
    }

    @NonNull
    public String getStage_name() {
        return stage_name;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    @NonNull
    public long getYes() {
        return yes;
    }

    @NonNull
    public long getNo() {
        return no;
    }

    @NonNull
    public long getStart_date() {
        return start_date;
    }

    @NonNull
    public long getEnd_date() {
        return end_date;
    }

    @NonNull
    public String getArtist_channel() {
        return artist_channel;
    }

    @NonNull
    public String getUser_id() {
        return user_id;
    }

    @NonNull
    public boolean isDeja_vu() {
        return deja_vu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistRequest request = (ArtistRequest) o;

        if (yes != request.yes) return false;
        if (no != request.no) return false;
        if (start_date != request.start_date) return false;
        if (end_date != request.end_date) return false;
        if (deja_vu != request.deja_vu) return false;
        if (!stage_name.equals(request.stage_name)) return false;
        if (avatar != null ? !avatar.equals(request.avatar) : request.avatar != null) return false;
        if (!uuid.equals(request.uuid)) return false;
        if (!artist_channel.equals(request.artist_channel)) return false;
        return user_id.equals(request.user_id);

    }

    @Override
    public int hashCode() {
        int result = stage_name.hashCode();
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + uuid.hashCode();
        result = 31 * result + (int) (yes ^ (yes >>> 32));
        result = 31 * result + (int) (no ^ (no >>> 32));
        result = 31 * result + (int) (start_date ^ (start_date >>> 32));
        result = 31 * result + (int) (end_date ^ (end_date >>> 32));
        result = 31 * result + artist_channel.hashCode();
        result = 31 * result + user_id.hashCode();
        result = 31 * result + (deja_vu ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ArtistRequest{" +
                "stage_name='" + stage_name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", uuid='" + uuid + '\'' +
                ", yes=" + yes +
                ", no=" + no +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", artist_channel='" + artist_channel + '\'' +
                ", user_id='" + user_id + '\'' +
                ", deja_vu=" + deja_vu +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stage_name);
        dest.writeString(this.avatar);
        dest.writeString(this.uuid);
        dest.writeLong(this.yes);
        dest.writeLong(this.no);
        dest.writeLong(this.start_date);
        dest.writeLong(this.end_date);
        dest.writeString(this.artist_channel);
        dest.writeString(this.user_id);
        dest.writeByte(deja_vu ? (byte) 1 : (byte) 0);
    }

    protected ArtistRequest(Parcel in) {
        this.stage_name = in.readString();
        this.avatar = in.readString();
        this.uuid = in.readString();
        this.yes = in.readLong();
        this.no = in.readLong();
        this.start_date = in.readLong();
        this.end_date = in.readLong();
        this.artist_channel = in.readString();
        this.user_id = in.readString();
        this.deja_vu = in.readByte() != 0;
    }

    public static final Creator<ArtistRequest> CREATOR = new Creator<ArtistRequest>() {
        public ArtistRequest createFromParcel(Parcel source) {
            return new ArtistRequest(source);
        }

        public ArtistRequest[] newArray(int size) {
            return new ArtistRequest[size];
        }
    };
}
