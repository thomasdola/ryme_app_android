package primr.apps.eurakacachet.ryme.ryme.ui.view.category;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.ui.view.category.inApp.StoredCategoriesFragment;


public class CategoryListAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private Fragment mFragment;
    private List<Category> mCategoryList;

    @Inject
    public CategoryListAdapter() {
        mCategoryList = new ArrayList<>();
    }

    public void setCategories(List<Category> categories, CategoriesFragment fragment){
        mCategoryList = categories;
        mFragment = fragment;
    }

    public void setCategories(List<Category> categories, StoredCategoriesFragment fragment){
        mCategoryList = categories;
        mFragment = fragment;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mFragment.getActivity());
        View view = layoutInflater.inflate(R.layout.category_card_view, parent, false);
        return new CategoryViewHolder(view, mFragment);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = mCategoryList.get(position);
        holder.bindCategory(category);
    }


    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }
}
