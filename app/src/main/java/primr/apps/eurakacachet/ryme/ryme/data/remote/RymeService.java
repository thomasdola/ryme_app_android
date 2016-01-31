package primr.apps.eurakacachet.ryme.ryme.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.data.model.Category;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;
import primr.apps.eurakacachet.ryme.ryme.data.model.LoginResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.data.model.UserProfile;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;


public interface RymeService {

    String ENDPOINT = "https://rymeapp.com/api/";

    @POST("register")
    Observable<LoginResponse> register();

    @POST("verify")
    Observable<LoginResponse> verify();

    @POST("login")
    Observable<LoginResponse> login();

    @GET("categories/lists")
    Observable<List<Category>> getCategories();

    @POST("categories/{uuid}/follow")
    Observable<Category> followCategory();

    @POST("categories/{uuid}/unFollow")
    Observable<Category> unFollowCategory();

    @GET("search")
    Observable<List<Artist>> searchArtist();

    @GET("tracks/{uuid}")
    Observable<Track> getTrack(@Path("uuid") UUID uuid);

    @GET("tracks/lists")
    Observable<List<Track>> getTrendingTracks();

    @GET("tracks/lists")
    Observable<List<Track>> getNewTracks();

    @POST("tracks/{uuid}/like")
    Observable<Track> likeTrack(@Path("uuid") UUID uuid);

    @POST("tracks/{uuid}/dislike")
    Observable<Track> dislikeTrack(@Path("uuid") UUID uuid);

    @POST("tracks/{uuid}/download")
    Observable<Track> downloadTrack(@Path("uuid") UUID uuid);

    @POST("tracks/{uuid}/view")
    Observable<Track> viewTrack(@Path("uuid") UUID uuid);

    @POST("tracks/{uuid}/comment")
    Observable<Track> commentTrack(@Path("uuid") UUID uuid);

    @GET("events/lists")
    Observable<List<EventAd>> getEventAds();

    @POST("events/{uuid}/view")
    Observable<EventAd> viewEventAd(@Path("uuid") UUID uuid);

    @GET("artists/{uuid}")
    Observable<Artist> getArtist(@Path("uuid") UUID uuid);

    @POST("artists/{uuid}/follow")
    Observable<Artist> followArtist(@Path("uuid") UUID uuid);

    @POST("artists/{uuid}/unFollow")
    Observable<Artist> unFollowArtist(@Path("uuid") UUID uuid);

    @GET("artists/{uuid}/tracks")
    Observable<List<Track>> getArtistTracks(@Path("uuid") UUID uuid);

    @GET("users/{uuid}/favorites")
    Observable<List<Artist>> getFavoriteArtists(@Path("uuid") UUID uuid);

    @GET("users/{uuid}/favorites")
    Observable<List<Track>> getFavoriteTracks(@Path("uuid") UUID uuid);

    @PUT("users/{uuid}/update")
    Observable<UserProfile> updateUserSomething(@Path("uuid") UUID uuid);
    //different types of update

    @POST("vouch")
    Observable<Void> makeArtistRequest();

    @POST("vouch/{uuid}")
    Observable<Void> respondToVouch(@Path("uuid") UUID uuid);


    class Creator {

        public static final String USER_TOKEN = "jkajdkff5aafsfadsdfdfsdd";

        public static RymeService newRymeService(){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Interceptor mInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization: Bearer ", USER_TOKEN)
                            .build();
                    return chain.proceed(request);
                }
            };

            OkHttpClient client = new OkHttpClient();
            client.interceptors().add(mInterceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RymeService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(RymeService.class);
        }
    }

}
