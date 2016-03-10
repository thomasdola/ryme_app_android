package primr.apps.eurakacachet.ryme.ryme.data.local.storio.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import primr.apps.eurakacachet.ryme.ryme.data.local.storio.tables.CategoriesTable;

@StorIOSQLiteType(table = CategoriesTable.TABLE_NAME)
public class Category {

    @Nullable
    @StorIOSQLiteColumn(name = CategoriesTable.COLUMN_ID, key = true)
    Long id;

    @NonNull
    @StorIOSQLiteColumn(name = CategoriesTable.COLUMN_NAME)
    String name;

    @NonNull
    @StorIOSQLiteColumn(name = CategoriesTable.COLUMN_UUID)
    String uuid;

    @NonNull
    public static Category newCategory(@Nullable Long id, @NonNull String name, @NonNull String uuid){
        Category category = new Category();
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

        Category category = (Category) o;

        return name.equals(category.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
