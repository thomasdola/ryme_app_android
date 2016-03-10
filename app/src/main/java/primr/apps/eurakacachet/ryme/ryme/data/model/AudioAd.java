package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class AudioAd implements Parcelable {

    @NonNull
    String uuid;

    @NonNull
    String path;

    @NonNull
    public static AudioAd newAd(@NonNull String uuid, @NonNull String path){
        AudioAd ad = new AudioAd();
        ad.uuid = uuid;
        ad.path = path;
        return ad;
    }

    @NonNull
    public String uuid(){
        return uuid;
    }

    @NonNull
    public String path(){
        return path;
    }

    @Override
    public String toString() {
        return "AudioAd{" +
                "uuid='" + uuid + '\'' +
                ", path='" + path + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.path);
    }

    public AudioAd() {
    }

    protected AudioAd(Parcel in) {
        this.uuid = in.readString();
        this.path = in.readString();
    }

    public static final Parcelable.Creator<AudioAd> CREATOR = new Parcelable.Creator<AudioAd>() {
        public AudioAd createFromParcel(Parcel source) {
            return new AudioAd(source);
        }

        public AudioAd[] newArray(int size) {
            return new AudioAd[size];
        }
    };
}
