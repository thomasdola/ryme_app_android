package primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables;


import android.support.annotation.NonNull;

public class CategoriesTable {

    @NonNull
    public static final String TABLE_NAME = "categories";

    @NonNull
    public static final String COLUMN_ID = "_id";

    @NonNull
    public static final String COLUMN_UUID = "uuid";

    @NonNull
    public static final String COLUMN_NAME = "name";

    private CategoriesTable(){
        throw new IllegalStateException("No instances please");
    }

    @NonNull
    public static String getCreateTableQuery(){
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_UUID + " TEXT NOT NULL," +
                COLUMN_NAME + " TEXT NOT NULL" +
                ");";
    }

}
