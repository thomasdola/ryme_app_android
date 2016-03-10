package primr.apps.eurakacachet.ryme.ryme.data.local.SqlBrite;

import android.database.Cursor;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.local.Db;
import primr.apps.eurakacachet.ryme.ryme.data.local.DbOpenHelper;
import primr.apps.eurakacachet.ryme.ryme.data.model.Category;
import primr.apps.eurakacachet.ryme.ryme.data.model.DownloadedTrack;
import primr.apps.eurakacachet.ryme.ryme.data.model.FollowedArtist;
import primr.apps.eurakacachet.ryme.ryme.data.model.LikedTrack;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;


public class SqlBriteDatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public SqlBriteDatabaseHelper(DbOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper);
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<Void> clearTables() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    Cursor cursor = mDb.query("SELECT name FROM sqlite_master WHERE type='table'");
                    while (cursor.moveToNext()) {
                        mDb.delete(cursor.getString(cursor.getColumnIndex("name")), null);
                    }
                    cursor.close();
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<LikedTrack> saveLikedTrack(final LikedTrack track){
        return Observable.create(new Observable.OnSubscribe<LikedTrack>() {
            @Override
            public void call(Subscriber<? super LikedTrack> subscriber) {
                getBriteDb().insert(Db.LikedTrackTable.TABLE_NAME, Db.LikedTrackTable.toContentValues(track));
                subscriber.onNext(track);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<LikedTrack> deleteLikedTrack(final LikedTrack track){
        return Observable.create(new Observable.OnSubscribe<LikedTrack>() {
            @Override
            public void call(Subscriber<? super LikedTrack> subscriber) {
                getBriteDb().delete(Db.LikedTrackTable.TABLE_NAME, "uuid = ? ", String.valueOf(track.uuid));
                subscriber.onNext(track);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<List<LikedTrack>> getLikedTracks(){
        return getBriteDb().createQuery(Db.LikedTrackTable.TABLE_NAME,
                "SELECT * FROM " + Db.LikedTrackTable.TABLE_NAME)
                .map(new Func1<SqlBrite.Query, List<LikedTrack>>() {
                    @Override
                    public List<LikedTrack> call(SqlBrite.Query query) {
                        Cursor cursor = query.run();
                        List<LikedTrack> result = new ArrayList<>();
                        while (cursor.moveToNext()) {
                            result.add(Db.LikedTrackTable.parseCursor(cursor));
                        }
                        cursor.close();
                        return result;
                    }
                });
    }

    public Observable<FollowedArtist> saveFollowedArtist(final FollowedArtist artist){
        return Observable.create(new Observable.OnSubscribe<FollowedArtist>() {
            @Override
            public void call(Subscriber<? super FollowedArtist> subscriber) {
                getBriteDb().insert(Db.FollowedArtistTable.TABLE_NAME, Db.FollowedArtistTable.toContentValues(artist));
                subscriber.onNext(artist);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<FollowedArtist> deleteFollowedArtist(final FollowedArtist artist){
        return Observable.create(new Observable.OnSubscribe<FollowedArtist>() {
            @Override
            public void call(Subscriber<? super FollowedArtist> subscriber) {
                getBriteDb().delete(Db.FollowedArtistTable.TABLE_NAME, "uuid = ? ", String.valueOf(artist.uuid));
                subscriber.onNext(artist);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<List<FollowedArtist>> getFollowedArtists(){
        return getBriteDb().createQuery(Db.FollowedArtistTable.TABLE_NAME,
                "SELECT * FROM " + Db.FollowedArtistTable.TABLE_NAME)
                .map(new Func1<SqlBrite.Query, List<FollowedArtist>>() {
                    @Override
                    public List<FollowedArtist> call(SqlBrite.Query query) {
                        Cursor cursor = query.run();
                        List<FollowedArtist> result = new ArrayList<>();
                        while (cursor.moveToNext()) {
                            result.add(Db.FollowedArtistTable.parseCursor(cursor));
                        }
                        cursor.close();
                        return result;
                    }
                });
    }

    public Observable<DownloadedTrack> saveDownloadedTrack(final DownloadedTrack track){
        return Observable.create(new Observable.OnSubscribe<DownloadedTrack>() {
            @Override
            public void call(Subscriber<? super DownloadedTrack> subscriber) {
                getBriteDb().insert(Db.DownloadedTrackTable.TABLE_NAME, Db.DownloadedTrackTable.toContentValues(track));
                subscriber.onNext(track);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<DownloadedTrack> deleteDownloadedTrack(final DownloadedTrack track){
        return Observable.create(new Observable.OnSubscribe<DownloadedTrack>() {
            @Override
            public void call(Subscriber<? super DownloadedTrack> subscriber) {
                getBriteDb().delete(Db.DownloadedTrackTable.TABLE_NAME, "uuid = ? ", String.valueOf(track.uuid));
                subscriber.onNext(track);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<List<DownloadedTrack>> getDownloadedTracks(){
        return getBriteDb().createQuery(Db.DownloadedTrackTable.TABLE_NAME,
                "SELECT * FROM " + Db.DownloadedTrackTable.TABLE_NAME)
                .map(new Func1<SqlBrite.Query, List<DownloadedTrack>>() {
                    @Override
                    public List<DownloadedTrack> call(SqlBrite.Query query) {
                        Cursor cursor = query.run();
                        List<DownloadedTrack> result = new ArrayList<>();
                        while (cursor.moveToNext()) {
                            result.add(Db.DownloadedTrackTable.parseCursor(cursor));
                        }
                        cursor.close();
                        return result;
                    }
                });
    }

    public Observable<Category> saveFollowedCategory(final Category category){
        return Observable.create(new Observable.OnSubscribe<Category>() {
            @Override
            public void call(Subscriber<? super Category> subscriber) {
                getBriteDb().insert(Db.FollowedCategoryTable.TABLE_NAME,
                        Db.FollowedCategoryTable.toContentValues(category));
                subscriber.onNext(category);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Category> deleteFollowedCategory(final Category category){
        return Observable.create(new Observable.OnSubscribe<Category>() {
            @Override
            public void call(Subscriber<? super Category> subscriber) {
                getBriteDb().delete(Db.FollowedCategoryTable.TABLE_NAME, "name = ? ", String.valueOf(category.name));
                subscriber.onNext(category);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Integer> delFolCat(final Category category){
        return Observable.just(
                getBriteDb().delete(Db.FollowedCategoryTable.TABLE_NAME, "name = ? ",
                        String.valueOf(category.name))
        );
    }

    public Observable<List<Category>> getFollowedCategories(){
        return mDb.createQuery(Db.FollowedCategoryTable.TABLE_NAME,
                "SELECT * FROM " + Db.FollowedCategoryTable.TABLE_NAME)
                .map(new Func1<SqlBrite.Query, List<Category>>() {
                    @Override
                    public List<Category> call(SqlBrite.Query query) {
                        Cursor cursor = query.run();
                        List<Category> result = new ArrayList<>();
                        while (cursor.moveToNext()) {
                            result.add(Db.FollowedCategoryTable.parseCursor(cursor));
                        }
                        cursor.close();
                        return result;
                    }
                });
    }

    public Observable<SqlBrite.Query> watchFollowedCategories(){
        return mDb.createQuery(Db.FollowedCategoryTable.TABLE_NAME,
                "SELECT * FROM " + Db.FollowedCategoryTable.TABLE_NAME);
    }

    public Observable<Category> setCategories(final List<Category> categories){
        return Observable.create(new Observable.OnSubscribe<Category>() {
            @Override
            public void call(Subscriber<? super Category> subscriber) {
                BriteDatabase.Transaction transaction = getBriteDb().newTransaction();
                try{
                    getBriteDb().delete(Db.CategoryTable.TABLE_NAME, null);
                    for (Category category: categories) {
                        getBriteDb().insert(Db.CategoryTable.TABLE_NAME, Db.CategoryTable.toContentValues(category));
                        subscriber.onNext(category);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                }finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Category>> getCategories(){
        return getBriteDb().createQuery(Db.CategoryTable.TABLE_NAME,
                "SELECT * FROM " + Db.CategoryTable.TABLE_NAME)
                .map(new Func1<SqlBrite.Query, List<Category>>() {
                    @Override
                    public List<Category> call(SqlBrite.Query query) {
                        Cursor cursor = query.run();
                        List<Category> result = new ArrayList<>();
                        while (cursor.moveToNext()){
                            result.add(Db.CategoryTable.parseCursor(cursor));
                        }
                        cursor.close();
                        return result;
                    }
                });
    }

}
