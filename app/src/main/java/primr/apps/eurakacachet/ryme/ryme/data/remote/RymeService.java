package primr.apps.eurakacachet.ryme.ryme.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import primr.apps.eurakacachet.ryme.ryme.data.local.PreferencesHelper;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedCategory;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.AddCommentResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.ArtistData;
import primr.apps.eurakacachet.ryme.ryme.data.model.AuthResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.CommentsData;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAdData;
import primr.apps.eurakacachet.ryme.ryme.data.model.FavoriteArtistsData;
import primr.apps.eurakacachet.ryme.ryme.data.model.FavoriteTracksData;
import primr.apps.eurakacachet.ryme.ryme.data.model.SearchResult;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.data.model.TrackData;
import primr.apps.eurakacachet.ryme.ryme.data.model.TracksData;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;


public interface RymeService {

    String ENDPOINT = "http://10.0.3.2:8000/api/";

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

    @FormUrlEncoded
    @POST("categories/{uuid}/follow")
    Observable<ActionResponse> followCategory(@Path("uuid") String uuid,
                                              @Field("token") String token);

    @FormUrlEncoded
    @POST("categories/{uuid}/unfollow")
    Observable<ActionResponse> unFollowCategory(@Path("uuid") String uuid,
                                                @Field("token") String token);

    @GET("search")
    Observable<SearchResult> searchArtist(@Query("q") String query);

    @GET("tracks/{uuid}")
    Observable<TrackData> getTrack(@Path("uuid") String uuid);

    @GET("tracks/lists")
    Observable<TracksData> getPublicTracks(@QueryMap Map<String , String> specifications);

    @POST("tracks/{uuid}/like")
    Observable<ActionResponse> likeTrack(@Path("uuid") String uuid);

    @POST("tracks/{uuid}/dislike")
    Observable<ActionResponse> dislikeTrack(@Path("uuid") String uuid);

    @POST("tracks/{uuid}/download")
    Observable<Track> downloadTrack(@Path("uuid") String uuid);

    @POST("tracks/{uuid}/view")
    Observable<ActionResponse> viewTrack(@Path("uuid") String uuid);

    @POST("tracks/{uuid}/play")
    Observable<ActionResponse> streamTrack(@Path("uuid") String uuid);

    @FormUrlEncoded
    @POST("tracks/{uuid}/comment")
    Observable<AddCommentResponse> commentTrack(@Path("uuid") String uuid,
                                                @FieldMap Map<String, String> comment);

    @GET("tracks/{uuid}/comments")
    Observable<CommentsData> getTrackComments(@Path("uuid") String trackId);

    @GET("events/lists")
    Observable<EventAdData> getEventAds();

    @POST("events/{uuid}/view")
    Observable<ActionResponse> viewEventAd(@Path("uuid") String uuid);

    @POST("audio-ads/{uuid}/view")
    Observable<ActionResponse> streamAudioAd(@Path("uuid") String uuid);

    @GET("artists/{uuid}")
    Observable<ArtistData> getArtist(@Path("uuid") String uuid);

    @FormUrlEncoded
    @POST("artists/{uuid}/follow")
    Observable<ActionResponse> followArtist(@Path("uuid") String uuid,
                                            @Field("token") String token);

    @FormUrlEncoded
    @POST("artists/{uuid}/unfollow")
    Observable<ActionResponse> unFollowArtist(@Path("uuid") String uuid
            , @Field("token") String token);

    @GET("artists/{uuid}/tracks")
    Observable<TracksData> getArtistTracks(@Path("uuid") String uuid,
                                            @QueryMap Map<String, String> specifications);

    @GET("user/allowed")
    Observable<ActionResponse> isUserAllowed();

    @GET("user/favorites")
    Observable<FavoriteArtistsData> loadFavoriteArtists(@Query("type") String typeOfFavorite);

    @GET("user/favorites")
    Observable<FavoriteTracksData> loadFavoriteTracks(@Query("type") String typeOfFavorite);

    @FormUrlEncoded
    @POST("user/update")
    Observable<ActionResponse> updateUserInfo(@FieldMap HashMap<String , String> payload);

    @Multipart
    @POST("user/photo")
    Observable<ActionResponse> updateUserPhoto(@Part("file\"; filename=\"image.jpg\" ")
                                               RequestBody file ,@Part("type") RequestBody type);

    @Multipart
    @POST("user/upload")
    Observable<ActionResponse> uplaodTrack(@PartMap Map<String , RequestBody> trackPayload);

    @FormUrlEncoded
    @POST("request/make")
    Observable<ActionResponse> makeRequest(@FieldMap HashMap<String, String> payload,
                                           @Field("regToken") String token);

    @FormUrlEncoded
    @POST("vouch/{uuid}/answer")
    Observable<ActionResponse> respondToVouch(@Path("uuid") String uuid,
                                              @Field("answer") boolean answer,
                                              @Field("token") String token);

    @GET("user/categories")
    Observable<List<FollowedCategory>> getFollowedCategories();

    @FormUrlEncoded
    @POST("tracks/{uuid}/update")
    Observable<ActionResponse> updateTrackInfo(@Path("uuid") String uuid,
                                               @Field("downloadable") int downloadable);


    class Creator {

        public static RymeService newRymeService(final PreferencesHelper preferencesHelper){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Interceptor mInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization: Bearer ", preferencesHelper.apiToken())
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
