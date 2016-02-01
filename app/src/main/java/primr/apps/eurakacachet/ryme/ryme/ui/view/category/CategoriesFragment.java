package primr.apps.eurakacachet.ryme.ryme.ui.view.category;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Category;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    RecyclerView mCategoryListRecyclerView;

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

        List<Category> categories = new ArrayList<>();
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this, categories);
        mCategoryListRecyclerView.setAdapter(categoryListAdapter);

        return rootView;
    }

}
