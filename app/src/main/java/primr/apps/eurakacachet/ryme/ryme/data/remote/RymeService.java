package primr.apps.eurakacachet.ryme.ryme.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.local.PreferencesHelper;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.data.model.AuthResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.Category;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;


public interface RymeService {

    String ENDPOINT = "https://rymeapp.com/api/";

    @FormUrlEncoded
    @POST("register")
    Observable<AuthResponse> register(@FieldMap Map<String, String> payload);

    @FormUrlEncoded
    @POST("verify")
    Observable<AuthResponse> verify(@Field("code") String code);

    @FormUrlEncoded
    @POST("login")
    Observable<AuthResponse> login(@FieldMap Map<String, String> credentials);

    @GET("categories/lists")
    Observable<List<Category>> getCategories();

    @POST("categories/{uuid}/follow")
    Observable<ActionResponse> followCategory(@Path("uuid") String uuid);

    @POST("categories/{uuid}/unFollow")
    Observable<ActionResponse> unFollowCategory(@Path("uuid") String uuid);

    @GET("search")
    Observable<List<Artist>> searchArtist(@Query("query") String query);

    @GET("tracks/{uuid}")
    Observable<Track> getTrack(@Path("uuid") String uuid);

    @GET("tracks/lists")
    Observable<List<Track>> getPublicTracks(@QueryMap Map<String , String> specifications);

    @POST("tracks/{uuid}/like")
    Observable<ActionResponse> likeTrack(@Path("uuid") String uuid);

    @POST("tracks/{uuid}/dislike")
    Observable<ActionResponse> dislikeTrack(@Path("uuid") String uuid);

    @POST("tracks/{uuid}/download")
    Observable<Track> downloadTrack(@Path("uuid") String uuid);

    @POST("tracks/{uuid}/view")
    Observable<ActionResponse> viewTrack(@Path("uuid") String uuid);

    @FormUrlEncoded
    @POST("tracks/{uuid}/comment")
    Observable<ActionResponse> commentTrack(@Path("uuid") String uuid, @FieldMap Map<String, String> commentDetail);

    @GET("events/lists")
    Observable<List<EventAd>> getEventAds(@QueryMap Map<String , String> specifications);

    @POST("events/{uuid}/view")
    Observable<ActionResponse> viewEventAd(@Path("uuid") String uuid);

    @GET("artists/{uuid}")
    Observable<Artist> getArtist(@Path("uuid") String uuid);

    @POST("artists/{uuid}/follow")
    Observable<ActionResponse> followArtist(@Path("uuid") String uuid);

    @POST("artists/{uuid}/unFollow")
    Observable<ActionResponse> unFollowArtist(@Path("uuid") String uuid);

    @GET("artists/{uuid}/tracks")
    Observable<List<Track>> getArtistTracks(@Path("uuid") String uuid,
                                            @QueryMap Map<String, String> specifications);

    @GET("users/{uuid}/favorites")
    Observable<List<Artist>> getFavoriteArtists(@Path("uuid") String uuid,
                                                @Field("type") String typeOfFavorite);

    @GET("users/{uuid}/favorites")
    Observable<List<Track>> getFavoriteTracks(@Path("uuid") String uuid,
                                              @Field("type") String typeOfFavorite);

    @FormUrlEncoded
    @PUT("users/{uuid}/update")
    Observable<ActionResponse> updateUser(@Path("uuid") String uuid,
                                                @FieldMap Map<String , String> payload);

    @Multipart
    @PUT("users/{uuid}/photo")
    Observable<ActionResponse> updateUser(@Path("uuid") String uuid,
                                          @Part("file\"; filename=\"picture.jpg\" ")RequestBody photo);

    @Multipart
    @POST("users/uuid/upload")
    Observable<ActionResponse> uplaodTrack(@Path("uuid") String uuid, @PartMap Map<String , RequestBody> trackPayload);

    @POST("vouch")
    Observable<ActionResponse> makeRequest();

    @FormUrlEncoded
    @POST("vouch/{uuid}")
    Observable<ActionResponse> respondToVouch(@Path("uuid") String uuid,
                                              @Field("answer") boolean answer);


    class Creator {

        @Inject
        public static PreferencesHelper mPref;

        public static RymeService newRymeService(){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Interceptor mInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization: Bearer ", mPref.apiToken())
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
