package primr.apps.eurakacachet.ryme.ryme.data.local.storio.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.FollowedCategoriesTable;

@StorIOSQLiteType(table = FollowedCategoriesTable.TABLE_NAME)
public class FollowedCategory {

    @Nullable
    @StorIOSQLiteColumn(name = FollowedCategoriesTable.COLUMN_ID, key = true)
    Long id;

    @NonNull
    @StorIOSQLiteColumn(name = FollowedCategoriesTable.COLUMN_NAME)
    String name;

    @NonNull
    @StorIOSQLiteColumn(name = FollowedCategoriesTable.COLUMN_UUID)
    String uuid;

    @NonNull
    public static FollowedCategory newCategory(@Nullable Long id, @NonNull String name, @NonNull String uuid){
        FollowedCategory category = new FollowedCategory();
        category.id = id;
        category.uuid = uuid;
        category.name = name;
        return category;
    }

    @Nullable
    public Long id(){
        return id;
    }

    @NonNull
    public String name(){
        return name;
    }

    @NonNull
    public String uuid(){
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowedCategory that = (FollowedCategory) o;

        if (!name.equals(that.name)) return false;
        return uuid.equals(that.uuid);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + uuid.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FollowedCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
