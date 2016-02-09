package primr.apps.eurakacachet.ryme.ryme.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

import primr.apps.eurakacachet.ryme.ryme.data.model.Category;
import primr.apps.eurakacachet.ryme.ryme.data.model.DownloadedTrack;
import primr.apps.eurakacachet.ryme.ryme.data.model.FollowedArtist;
import primr.apps.eurakacachet.ryme.ryme.data.model.FollowedCategory;
import primr.apps.eurakacachet.ryme.ryme.data.model.LikedTrack;
import primr.apps.eurakacachet.ryme.ryme.data.model.UserProfile;

public class Db {

    public Db(){}

    public abstract static class UserProfileTable{
        public static final String TABLE_NAME = "user_profile";

        public static final String COLUMN_ID = "uuid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_IS_ARTIST = "is_artist";
        public static final String COLUMN_STAGE_NAME = "stage_name";
        public static final String COLUMN_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_AVATAR = "avatar";
        public static final String COLUMN_BACKGROUND_PICTURE = "background_picture";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " TEXT PRIMARY KEY NOT NULL, " +
                        COLUMN_USERNAME + " TEXT NOT NULL, " +
                        COLUMN_IS_ARTIST + " TEXT NOT NULL, " +
                        COLUMN_STAGE_NAME + " TEXT NULL, " +
                        COLUMN_PHONE_NUMBER + " TEXT NOT NULL, " +
                        COLUMN_AVATAR + " TEXT NULL, " +
                        COLUMN_BACKGROUND_PICTURE + " TEXT NULL, " +
                " ); ";

        public static ContentValues toContentValues(UserProfile userProfile){
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, String.valueOf(userProfile.uuid));
            contentValues.put(COLUMN_USERNAME, userProfile.username);
            contentValues.put(COLUMN_IS_ARTIST, userProfile.is_artist);
            contentValues.put(COLUMN_PHONE_NUMBER, userProfile.phone_number);
            if(userProfile.stage_name != null) contentValues.put(COLUMN_STAGE_NAME,
                    userProfile.stage_name);
            if(userProfile.avatar != null) contentValues.put(COLUMN_AVATAR, userProfile.avatar);
            if(userProfile.background_picture != null) contentValues.put(COLUMN_BACKGROUND_PICTURE,
                    userProfile.background_picture);
            return contentValues;
        }

        public static UserProfile parseCursor(Cursor cursor){
            UserProfile userProfile = new UserProfile();
            userProfile.uuid = UUID.fromString(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            userProfile.username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            userProfile.is_artist = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IS_ARTIST)));
            userProfile.stage_name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STAGE_NAME));
            userProfile.phone_number = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER));
            userProfile.avatar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR));
            userProfile.background_picture = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BACKGROUND_PICTURE));
            return userProfile;
        }
    }

    public abstract static class CategoryTable{
        public static final String TABLE_NAME = "categories";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        COLUMN_UUID + " TEXT NOT NULL," +
                        COLUMN_NAME + " TEXT NOT NULL," +
                        " ); ";

        public static ContentValues toContentValues(Category category){
            ContentValues values = new ContentValues();
            values.put(COLUMN_UUID, String.valueOf(category.uuid));
            values.put(COLUMN_NAME, category.name);
            return values;
        }

        public static Category parseCursor(Cursor cursor){
            Category category = new Category();
            category.uuid = UUID.fromString(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UUID)));
            category.name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            return category;
        }
    }

    public abstract static class FollowedCategoryTable {
        public static final String TABLE_NAME = "followed_categories";

        public static final String COLUMN_ID = "uuid";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        COLUMN_UUID + " TEXT NOT NULL," +
                        COLUMN_NAME + " TEXT NOT NULL," +
                        " ); ";

        public static ContentValues toContentValues(FollowedCategory category){
            ContentValues values = new ContentValues();
            values.put(COLUMN_UUID, String.valueOf(category.id));
            values.put(COLUMN_NAME, category.name);
            return values;
        }

        public static FollowedCategory parseCursor(Cursor cursor){
            FollowedCategory category = new FollowedCategory();
            category.id = UUID.fromString(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UUID)));
            category.name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            return category;
        }
    }

    public abstract static class FollowedArtistTable {
        public static final String TABLE_NAME = "followed_artists";

        public static final String COLUMN_ID = "uuid";
        public static final String COLUMN_UUID = "uuid";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY AUTOINCREMENT KEY NOT NULL," +
                        COLUMN_UUID + " TEXT NOT NULL," +
                        " ); ";

        public static ContentValues toContentValues(FollowedArtist artist){
            ContentValues values = new ContentValues();
            values.put(COLUMN_UUID, String.valueOf(artist.uuid));
            return values;
        }

        public static FollowedArtist parseCursor(Cursor cursor){
            FollowedArtist artist = new FollowedArtist();
            artist.uuid = UUID.fromString(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UUID)));
            return artist;
        }
    }

    public abstract static class LikedTrackTable{
        public static final String TABLE_NAME = "liked_tracks";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UUID = "uuid";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY AUTOINCREMENT KEY NOT NULL," +
                        COLUMN_UUID + " TEXT NOT NULL," +
                        " ); ";

        public static ContentValues toContentValues(LikedTrack track){
            ContentValues values = new ContentValues();
            values.put(COLUMN_UUID, String.valueOf(track.uuid));
            return values;
        }

        public static LikedTrack parseCursor(Cursor cursor){
            LikedTrack track = new LikedTrack();
            track.uuid = UUID.fromString(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UUID)));
            return track;
        }
    }

    public abstract static class DownloadedTrackTable{
        public static final String TABLE_NAME = "downloaded_tracks";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_ARTIST = "artist";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_COVER = "cover";
        public static final String COLUMN_PATH = "path";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        COLUMN_UUID + " TEXT NOT NULL," +
                        COLUMN_TITLE + " TEXT NOT NULL," +
                        COLUMN_DURATION + " INTEGER NOT NULL," +
                        COLUMN_ARTIST + " TEXT NOT NULL," +
                        COLUMN_COVER + " TEXT NOT NULL," +
                        COLUMN_PATH + " TEXT NOT NULL," +
                        " ); ";

        public static ContentValues toContentValues(DownloadedTrack track){
            ContentValues values = new ContentValues();
            values.put(COLUMN_UUID, String.valueOf(track.uuid));
            values.put(COLUMN_ARTIST, track.artist);
            values.put(COLUMN_DURATION, track.duration);
            values.put(COLUMN_TITLE, track.title);
            values.put(COLUMN_PATH, track.path);
            values.put(COLUMN_COVER, track.cover);
            return values;
        }

        public static DownloadedTrack parseCursor(Cursor cursor){
            DownloadedTrack track = new DownloadedTrack();
            track.uuid = UUID.fromString(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UUID)));
            track.path = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATH));
            track.title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATH));
            track.artist = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATH));
            track.duration = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
            track.cover = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COVER));
            return track;
        }
    }
}
