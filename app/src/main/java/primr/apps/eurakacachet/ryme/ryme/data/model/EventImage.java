package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.support.annotation.NonNull;

public class EventImage {

    @NonNull
    String path;
    @NonNull
    String type;

    public static EventImage newImage(@NonNull String path, @NonNull String type){
        EventImage image = new EventImage();
        image.path = path;
        image.type = type;
        return image;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    @NonNull
    public String getType() {
        return type;
    }
}
