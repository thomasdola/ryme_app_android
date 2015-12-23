package primr.apps.eurakacachet.com.rhyme.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import primr.apps.eurakacachet.com.rhyme.model.Category;
import primr.apps.eurakacachet.com.rhyme.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    private RecyclerView mCategoryListRecyclerView;
    private CategoryListAdapter mCategoryListAdapter;
    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sent, container, false);

        mCategoryListRecyclerView = (RecyclerView) rootView.findViewById(R.id.category_card_view);
        mCategoryListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCategoryListAdapter = new CategoryListAdapter(Category.getCategories());
        mCategoryListRecyclerView.setAdapter(mCategoryListAdapter);

        return rootView;
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView mCategoryName;
        private Button mFollowButton;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            mCategoryName = (TextView) itemView.findViewById(R.id.category_name_text_view);
            mFollowButton = (Button) itemView.findViewById(R.id.follow_category_button);
        }

    }

    private class CategoryListAdapter extends RecyclerView.Adapter<CategoriesFragment.CategoryViewHolder> {

        private List<Category> mCategoryList;

        public CategoryListAdapter(List<Category> categories){
            mCategoryList = categories;
        }

        @Override
        public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.category_card_view, parent, false);
            return new CategoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CategoryViewHolder holder, int position) {
            holder.mCategoryName.setText(mCategoryList.get(position).getCategoryName());
        }

        @Override
        public int getItemCount() {
            return mCategoryList.size();
        }
    }
}
