package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.support.annotation.NonNull;

public class CategoryToFU {

    @NonNull
    String category_channel_name;

    @NonNull
    String action;

    public static CategoryToFU newCat(@NonNull String category_channel_name,
                                      @NonNull String action){
        CategoryToFU categoryToFU = new CategoryToFU();
        categoryToFU.category_channel_name = category_channel_name;
        categoryToFU.action = action;
        return categoryToFU;
    }

    @NonNull
    public String getCategory_channel_name() {
        return category_channel_name;
    }

    @NonNull
    public String getAction() {
        return action;
    }
}
