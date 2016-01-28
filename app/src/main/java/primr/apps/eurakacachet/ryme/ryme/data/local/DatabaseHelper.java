package primr.apps.eurakacachet.ryme.ryme.data.local;

import android.database.Cursor;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.model.Category;
import primr.apps.eurakacachet.ryme.ryme.data.model.DownloadedTrack;
import primr.apps.eurakacachet.ryme.ryme.data.model.FollowingArtist;
import primr.apps.eurakacachet.ryme.ryme.data.model.FollowingCategory;
import primr.apps.eurakacachet.ryme.ryme.data.model.LikedTrack;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;


public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
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
                mDb.insert(Db.LikedTrackTable.TABLE_NAME, Db.LikedTrackTable.toContentValues(track));
                subscriber.onNext(track);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<LikedTrack> deleteLikedTrack(final LikedTrack track){
        return Observable.create(new Observable.OnSubscribe<LikedTrack>() {
            @Override
            public void call(Subscriber<? super LikedTrack> subscriber) {
                mDb.delete(Db.LikedTrackTable.TABLE_NAME, "id", String.valueOf(track.id));
                subscriber.onNext(track);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<FollowingCategory> saveFollowingCategory(final FollowingCategory category){
        return Observable.create(new Observable.OnSubscribe<FollowingCategory>() {
            @Override
            public void call(Subscriber<? super FollowingCategory> subscriber) {
                mDb.insert(Db.FollowingCategoryTable.TABLE_NAME,
                        Db.FollowingCategoryTable.toContentValues(category));
                subscriber.onNext(category);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<FollowingCategory> deleteFollowingCategory(final FollowingCategory category){
        return Observable.create(new Observable.OnSubscribe<FollowingCategory>() {
            @Override
            public void call(Subscriber<? super FollowingCategory> subscriber) {
                mDb.delete(Db.FollowingCategoryTable.TABLE_NAME, "id", String.valueOf(category.id));
                subscriber.onNext(category);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<FollowingArtist> saveFollowingArtist(final FollowingArtist artist){
        return Observable.create(new Observable.OnSubscribe<FollowingArtist>() {
            @Override
            public void call(Subscriber<? super FollowingArtist> subscriber) {
                mDb.insert(Db.FollowingArtistTable.TABLE_NAME, Db.FollowingArtistTable.toContentValues(artist));
                subscriber.onNext(artist);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<FollowingArtist> deleteFollowingArtist(final FollowingArtist artist){
        return Observable.create(new Observable.OnSubscribe<FollowingArtist>() {
            @Override
            public void call(Subscriber<? super FollowingArtist> subscriber) {
                mDb.delete(Db.FollowingArtistTable.TABLE_NAME, "id", String.valueOf(artist.id));
                subscriber.onNext(artist);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<DownloadedTrack> saveDownloadedTrack(final DownloadedTrack track){
        return Observable.create(new Observable.OnSubscribe<DownloadedTrack>() {
            @Override
            public void call(Subscriber<? super DownloadedTrack> subscriber) {
                mDb.insert(Db.DownloadedTrackTable.TABLE_NAME, Db.DownloadedTrackTable.toContentValues(track));
                subscriber.onNext(track);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<DownloadedTrack> deleteDownloadedTrack(final DownloadedTrack track){
        return Observable.create(new Observable.OnSubscribe<DownloadedTrack>() {
            @Override
            public void call(Subscriber<? super DownloadedTrack> subscriber) {
                mDb.delete(Db.DownloadedTrackTable.TABLE_NAME, "id", String.valueOf(track.id));
                subscriber.onNext(track);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<List<DownloadedTrack>> getDownloadedTracks(){
        return mDb.createQuery(Db.DownloadedTrackTable.TABLE_NAME,
                "SELECT * FROM " + Db.DownloadedTrackTable.TABLE_NAME)
                .map(new Func1<SqlBrite.Query, List<DownloadedTrack>>() {
                    @Override
                    public List<DownloadedTrack> call(SqlBrite.Query query) {
                        Cursor cursor = query.run();
                        List<DownloadedTrack> result = new ArrayList<>();
                        while (cursor.moveToNext()){
                            result.add(Db.DownloadedTrackTable.parseCursor(cursor));
                        }
                        cursor.close();
                        return result;
                    }
                });
    }

    public Observable<Category> setCategories(final List<Category> categories){
        return Observable.create(new Observable.OnSubscribe<Category>() {
            @Override
            public void call(Subscriber<? super Category> subscriber) {
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try{
                    mDb.delete(Db.CategoryTable.TABLE_NAME, null);
                    for (Category category: categories) {
                        mDb.insert(Db.CategoryTable.TABLE_NAME, Db.CategoryTable.toContentValues(category));
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
        return mDb.createQuery(Db.CategoryTable.TABLE_NAME,
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
