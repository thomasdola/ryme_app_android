package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.Arrays;

public class EventAdData implements Parcelable {

    @Nullable
    EventAd[] data;

    public static EventAdData newData(@Nullable EventAd[] data){
        EventAdData adData = new EventAdData();
        adData.data = data;
        return adData;
    }

    @Nullable
    public EventAd[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "EventAdData{" +
                "data -> " + Arrays.toString(data) +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(this.data, 0);
    }

    public EventAdData() {
    }

    protected EventAdData(Parcel in) {
        this.data = (EventAd[]) in.readParcelableArray(EventAd.class.getClassLoader());
    }

    public static final Parcelable.Creator<EventAdData> CREATOR = new Parcelable.Creator<EventAdData>() {
        public EventAdData createFromParcel(Parcel source) {
            return new EventAdData(source);
        }

        public EventAdData[] newArray(int size) {
            return new EventAdData[size];
        }
    };
}
