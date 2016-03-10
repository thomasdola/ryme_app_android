package primr.apps.eurakacachet.ryme.ryme.data.local.storio;


import android.util.Log;

import com.pushtorefresh.storio.sqlite.Changes;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.local.DbOpenHelper;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.CategoryStorIOSQLiteDeleteResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.CategoryStorIOSQLiteGetResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.CategoryStorIOSQLitePutResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtistStorIOSQLiteDeleteResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtistStorIOSQLiteGetResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtistStorIOSQLitePutResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedCategory;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedCategoryStorIOSQLiteDeleteResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedCategoryStorIOSQLiteGetResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedCategoryStorIOSQLitePutResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.LikedTrack;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.LikedTrackStorIOSQLiteDeleteResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.LikedTrackStorIOSQLiteGetResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.LikedTrackStorIOSQLitePutResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrackStorIOSQLiteDeleteResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrackStorIOSQLiteGetResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrackStorIOSQLitePutResolver;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.CategoriesTable;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.DownloadedTracksTable;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.FollowedArtistsTable;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.FollowedCategoriesTable;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.LikedTracksTable;
import rx.Observable;

public class StorioDatabaseHelper {

    private final StorIOSQLite mDb;

    @Inject
    public StorioDatabaseHelper(DbOpenHelper dbOpenHelper){
        mDb = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(dbOpenHelper)
                .addTypeMapping(Category.class, SQLiteTypeMapping.<Category>builder()
                        .putResolver(new CategoryStorIOSQLitePutResolver())
                        .getResolver(new CategoryStorIOSQLiteGetResolver())
                        .deleteResolver(new CategoryStorIOSQLiteDeleteResolver()).build())
                .addTypeMapping(FollowedCategory.class, SQLiteTypeMapping.<FollowedCategory>builder()
                        .putResolver(new FollowedCategoryStorIOSQLitePutResolver())
                        .getResolver(new FollowedCategoryStorIOSQLiteGetResolver())
                        .deleteResolver(new FollowedCategoryStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(FollowedArtist.class, SQLiteTypeMapping.<FollowedArtist>builder()
                        .putResolver(new FollowedArtistStorIOSQLitePutResolver())
                        .getResolver(new FollowedArtistStorIOSQLiteGetResolver())
                        .deleteResolver(new FollowedArtistStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(LikedTrack.class, SQLiteTypeMapping.<LikedTrack>builder()
                        .putResolver(new LikedTrackStorIOSQLitePutResolver())
                        .getResolver(new LikedTrackStorIOSQLiteGetResolver())
                        .deleteResolver(new LikedTrackStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(SavedTrack.class, SQLiteTypeMapping.<SavedTrack>builder()
                        .putResolver(new SavedTrackStorIOSQLitePutResolver())
                        .getResolver(new SavedTrackStorIOSQLiteGetResolver())
                        .deleteResolver(new SavedTrackStorIOSQLiteDeleteResolver())
                        .build())
                .build();
    }

    public StorIOSQLite getStorIODb(){
        return mDb;
    }

    public Observable<PutResult> putLikedTrack(LikedTrack track){
        return getStorIODb()
                .put()
                .object(track)
                .prepare()
                .asRxObservable();
    }

    public Observable<DeleteResult> deleteLikedTrack(LikedTrack track){
        return getStorIODb()
                .delete()
                .byQuery(DeleteQuery.builder()
                        .table(LikedTracksTable.TABLE_NAME)
                        .where("uuid = ?")
                        .whereArgs(track.uuid())
                        .build())
                .prepare()
                .asRxObservable();
    }

    public Observable<List<LikedTrack>> getLikedTracks(){
        return getStorIODb()
                .get()
                .listOfObjects(LikedTrack.class)
                .withQuery(Query.builder().table(LikedTracksTable.TABLE_NAME).build())
                .prepare()
                .asRxObservable();
    }

    public Observable<PutResult> putFollowedCategory(FollowedCategory category){
        return getStorIODb().put()
                .object(category)
                .prepare()
                .asRxObservable();
    }

    public Observable<DeleteResult> deleteFollowedCategory(FollowedCategory category){
        return getStorIODb().delete()
                .byQuery(DeleteQuery.builder()
                        .table(FollowedCategoriesTable.TABLE_NAME)
                        .where("uuid = ?")
                        .whereArgs(category.uuid())
                        .build())
                .prepare()
                .asRxObservable();
    }

    public Observable<List<FollowedCategory>> getFollowedCategories(){
        return getStorIODb().get()
                .listOfObjects(FollowedCategory.class)
                .withQuery(Query.builder().table(FollowedCategoriesTable.TABLE_NAME).build())
                .prepare()
                .asRxObservable();
    }

    public Observable<Changes> watchFollowedCategoriesTable(){
        return getStorIODb().observeChangesInTable(FollowedCategoriesTable.TABLE_NAME);
    }

    public List<FollowedCategory> followedCategoriesBloackQuery(){
        return getStorIODb()
                .get()
                .listOfObjects(FollowedCategory.class)
                .withQuery(Query.builder().table(FollowedCategoriesTable.TABLE_NAME).build())
                .prepare()
                .executeAsBlocking();
    }

    public Observable<PutResults<Category>> putCategories(List<Category> categories){
        return getStorIODb().put()
                .objects(categories)
                .prepare()
                .asRxObservable();
    }

    public Observable<PutResults<Category>> syncCategories(List<Category> categories){
        this.clearCategoriesTable();
        return getStorIODb().put()
                .objects(categories)
                .useTransaction(true)
                .prepare()
                .asRxObservable();
    }

    protected void clearCategoriesTable(){
        getStorIODb()
                .delete()
                .byQuery(DeleteQuery
                        .builder()
                        .table(CategoriesTable.TABLE_NAME).build())
                .prepare()
                .executeAsBlocking();
    }

    public Observable<List<Category>> getCategories(){
        return getStorIODb()
                .get()
                .listOfObjects(Category.class)
                .withQuery(Query.builder().table(CategoriesTable.TABLE_NAME).build())
                .prepare()
                .asRxObservable();
    }

    public Observable<SavedTrack> getSavedTrack(String uuid) {
        Log.d("adapter", "getSavedTrack Giving the error?");
        return getStorIODb()
                .get()
                .object(SavedTrack.class)
                .withQuery(Query.builder()
                        .table(DownloadedTracksTable.TABLE_NAME)
                        .where("uuid = ?").whereArgs(uuid).build())
                .prepare()
                .asRxObservable();
    }

    public Observable<PutResult> updateTrackWithCover(SavedTrack savedTrack) {
        return getStorIODb()
                .put()
                .object(savedTrack)
                .prepare()
                .asRxObservable();
    }

    public Observable<PutResult> saveDownloadedTrack(SavedTrack savedTrack) {
        return getStorIODb()
                .put()
                .object(savedTrack)
                .prepare()
                .asRxObservable();
    }

    public Observable<PutResult> saveLikedTrack(LikedTrack likedTrack) {
        return getStorIODb()
                .put()
                .object(likedTrack)
                .prepare()
                .asRxObservable();
    }

    public Observable<List<FollowedArtist>> getFollowedArtists() {
        return getStorIODb()
                .get()
                .listOfObjects(FollowedArtist.class)
                .withQuery(Query.builder().table(FollowedArtistsTable.TABLE_NAME).build())
                .prepare()
                .asRxObservable();
    }

    public Observable<PutResult> saveFollowedArtist(FollowedArtist followedArtist) {
        return getStorIODb()
                .put()
                .object(followedArtist)
                .prepare()
                .asRxObservable();
    }

    public Observable<DeleteResult> deleteFollowedArtist(FollowedArtist followedArtist) {
        return getStorIODb()
                .delete()
                .object(followedArtist)
                .prepare()
                .asRxObservable();
    }

    public Observable<List<SavedTrack>> getSavedTracks() {
        return getStorIODb()
                .get()
                .listOfObjects(SavedTrack.class)
                .withQuery(Query.builder()
                        .table(DownloadedTracksTable.TABLE_NAME)
                        .build())
                .prepare()
                .asRxObservable();
    }

    public List<SavedTrack> syncLoadSavedTracks() {
        return getStorIODb()
                .get()
                .listOfObjects(SavedTrack.class)
                .withQuery(Query.builder()
                        .table(DownloadedTracksTable.TABLE_NAME)
                        .build())
                .prepare()
                .executeAsBlocking();
    }

    public Observable<PutResults<FollowedCategory>> putFollowedCategories(List<FollowedCategory> followedCategories) {
        return getStorIODb()
                .put()
                .objects(followedCategories)
                .prepare()
                .asRxObservable();
    }
}
