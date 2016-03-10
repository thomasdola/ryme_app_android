package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class PlayBackEvent implements Parcelable {

    public static final int FINISH = 20;
    @NonNull
    int type;

    public static PlayBackEvent newEvent(@NonNull int type){
        PlayBackEvent event = new PlayBackEvent();
        event.type = type;
        return event;
    }

    @NonNull
    public int geteventType(){
        return type;
    }

    @Override
    public String toString() {
        return "PlayBackEvent{" +
                "type -> " + type +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
    }

    public PlayBackEvent() {
    }

    protected PlayBackEvent(Parcel in) {
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<PlayBackEvent> CREATOR = new Parcelable.Creator<PlayBackEvent>() {
        public PlayBackEvent createFromParcel(Parcel source) {
            return new PlayBackEvent(source);
        }

        public PlayBackEvent[] newArray(int size) {
            return new PlayBackEvent[size];
        }
    };
}
