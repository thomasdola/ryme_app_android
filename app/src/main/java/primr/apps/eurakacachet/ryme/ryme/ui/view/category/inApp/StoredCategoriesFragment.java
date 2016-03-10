package primr.apps.eurakacachet.ryme.ryme.ui.view.category.inApp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.category.CategoryListAdapter;


public class StoredCategoriesFragment extends Fragment implements StoredCategoriesMvpView{

    @Inject StoredCategoriesPresenter mPresenter;
    @Inject CategoryListAdapter mCategoriesAdapter;
    private RecyclerView mCategoryListRecyclerView;
    private ProgressBar mLoading;

    public static StoredCategoriesFragment newInstance() {
        Bundle args = new Bundle();
        StoredCategoriesFragment fragment = new StoredCategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public StoredCategoriesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stored_categories_fragement, container, false);
        initViews(rootView);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        mCategoryListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void initViews(View rootView) {
        mCategoryListRecyclerView = (RecyclerView) rootView.findViewById(R.id.stored_categories_recycler_view);
        mLoading = (ProgressBar) rootView.findViewById(R.id.loading_view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.syncCategories();
        mPresenter.syncFollowedCategories();
        mPresenter.loadCategories();
        mCategoryListRecyclerView.setAdapter(mCategoriesAdapter);
    }

    @Override
    public void setAdapter(List<Category> categories) {
        mCategoriesAdapter.setCategories(categories, this);
        mCategoriesAdapter.notifyDataSetChanged();
    }
}
