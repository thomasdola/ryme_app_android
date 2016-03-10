package primr.apps.eurakacachet.ryme.ryme.ui.view.category;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.data.model.CategoryToFU;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.main.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment implements CategoriesFragmentMvpView{

    @Inject CategoriesFragmentPresenter mCategoriesFragmentPresenter;
    @Inject CategoryListAdapter mCategoriesAdapter;
    @Inject Bus mBus;

    RecyclerView mCategoryListRecyclerView;
    ProgressBar mLoading;
    FloatingActionButton mStartFab;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static CategoriesFragment newInstance() {
        Bundle args = new Bundle();
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sent, container, false);
        initViews(rootView);
        initRecyclerView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBus.unregister(this);
    }

    private void initListeners() {
        mStartFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoriesFragmentPresenter.startApp();
            }
        });
    }

    private void initViews(View rootView) {
        mCategoryListRecyclerView = (RecyclerView) rootView.findViewById(R.id.category_card_view);
        mLoading = (ProgressBar) rootView.findViewById(R.id.loading_view);
        mStartFab = (FloatingActionButton) rootView.findViewById(R.id.start_fab);
    }


    public void initRecyclerView() {
        mCategoryListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mCategoriesFragmentPresenter.attachView(this);
        mCategoriesFragmentPresenter.loadCategories();
        mCategoriesFragmentPresenter.startWatch();
        mCategoryListRecyclerView.setAdapter(mCategoriesAdapter);
        initListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setCategoriesAdapter(List<Category> categories) {
        mCategoriesAdapter.setCategories(categories, this);
        mCategoriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void launchMainActivity() {
        Intent intent = MainActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Subscribe
    public void gcmSubUnsub(CategoryToFU category){

    }
}
