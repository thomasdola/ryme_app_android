package primr.apps.eurakacachet.ryme.ryme.ui.view.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Category;


public class CategoryListAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private CategoriesFragment mCategoriesFragment;
    private List<Category> mCategoryList;

    public CategoryListAdapter(CategoriesFragment categoriesFragment, List<Category> categories) {
        mCategoriesFragment = categoriesFragment;
        mCategoryList = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCategoriesFragment.getActivity());
        View view = layoutInflater.inflate(R.layout.category_card_view, parent, false);
        return new CategoryViewHolder(view);
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
