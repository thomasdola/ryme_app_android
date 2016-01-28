package primr.apps.eurakacachet.ryme.ryme.data.model;


import java.util.ArrayList;
import java.util.List;

public class Category {

    private String mCategoryName;
    private static List<Category> mCategoryList;

    public Category(String name){
        mCategoryName = name;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public static List<Category> getCategories(){

        mCategoryList = new ArrayList<>();
        mCategoryList.add(new Category("DanceHall"));
        mCategoryList.add(new Category("HipLife"));
        mCategoryList.add(new Category("HighLife"));
        mCategoryList.add(new Category("Gospel"));
        mCategoryList.add(new Category("HipHop"));

        return mCategoryList;
    }
}
