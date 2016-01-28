package primr.apps.eurakacachet.ryme.ryme.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import primr.apps.eurakacachet.ryme.ryme.data.model.UserProfile;

/**
 * Created by GURU on 1/27/2016.
 */
public class Db {

    public Db(){}

    public abstract static class UserProfileTable{
        public static final String TABLE_NAME = "user_profile";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_STAGE_NAME = "stage_name";
        public static final String COLUMN_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_AVATAR = "avatar";
        public static final String COLUMN_STAGE_PICTURE = "stage_picture";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " TEXT PRIMARY KEY NOT NULL, " +
                        COLUMN_USERNAME + " TEXT NOT NULL, " +
                        COLUMN_STAGE_NAME + " TEXT NULL, " +
                        COLUMN_PHONE_NUMBER + " TEXT NOT NULL, " +
                        COLUMN_AVATAR + " TEXT NULL, " +
                        COLUMN_STAGE_PICTURE + " TEXT NULL, " +
                " ); ";

        public static ContentValues toContentValues(UserProfile userProfile){
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, userProfile.id);
            contentValues.put(COLUMN_USERNAME, userProfile.username);
            if(userProfile.stage_name != null) contentValues.put(COLUMN_STAGE_NAME, userProfile.stage_name);
            contentValues.put(COLUMN_PHONE_NUMBER, userProfile.phone_number);
            if(userProfile.avatar != null) contentValues.put(COLUMN_AVATAR, userProfile.avatar);
            if(userProfile.stage_picture != null) contentValues.put(COLUMN_STAGE_PICTURE, userProfile.stage_picture);
            return contentValues;
        }

        public static UserProfile parseCursor(Cursor cursor){
            UserProfile userProfile = new UserProfile();
            userProfile.id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID));
            userProfile.username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            userProfile.stage_name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STAGE_NAME));
            userProfile.phone_number = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER));
            userProfile.avatar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR));
            userProfile.stage_picture = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STAGE_PICTURE));
            return userProfile;
        }
    }
}
