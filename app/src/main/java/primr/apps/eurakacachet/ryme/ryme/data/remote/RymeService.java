package primr.apps.eurakacachet.ryme.ryme.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.UUID;

import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.data.model.Category;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;


public interface RymeService {

    String ENDPOINT = "https://rymeapp.com/api/";

    @GET("tracks/{uuid}")
    Observable<Track> getTrack(@Path("uuid") UUID uuid);

    @GET("artists/{uuid}")
    Observable<Artist> getArtist(@Path("uuid") UUID uuid);

    @GET("categories")
    Observable<List<Category>> getCategories();

    class Creator {

        public static RymeService newRymeService(){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RymeService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(RymeService.class);
        }
    }

}
