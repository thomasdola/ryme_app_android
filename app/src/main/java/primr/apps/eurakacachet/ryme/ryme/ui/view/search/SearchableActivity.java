package primr.apps.eurakacachet.ryme.ryme.ui.view.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;

public class SearchableActivity extends BaseActivity implements SearchableMvpView{

    @Inject SearchablePresenter mSearchablePresenter;
    @Inject SearchResultAdapter mResultAdapter;
    private Toolbar mToolbar;
    private RecyclerView mResultRecylerView;
    private FrameLayout mOopsView;
    private String mQuery;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_search);
        mSearchablePresenter.attachView(this);
        mSearchablePresenter.getUserId();
        Intent intent = getIntent();
        handleIntent(intent);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if(mQuery == null){
            mToolbar.setTitle(R.string.search_text);
        }else {
            mToolbar.setTitle(mQuery);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
    }

    private void initViews() {
        mOopsView = (FrameLayout) findViewById(R.id.oops_frame_layout);
        mResultRecylerView = (RecyclerView) findViewById(R.id.search_result_list_recycler);
        mResultRecylerView.setLayoutManager(new LinearLayoutManager(this));
        mResultRecylerView.setAdapter(mResultAdapter);
    }

    private void performSearch(String query) {
        mSearchablePresenter.performSearch(query);
        Log.d("search", "performSearch called with => " + query);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Log.d("search", "handleIntent called ");
        if( Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            mQuery = query;
            performSearch(query);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setResults(List<Object> results) {
        mResultAdapter.setResultData(results, this, mUserId);
        mResultAdapter.notifyDataSetChanged();
    }

    @Override
    public void setOops(boolean isError) {
        mResultRecylerView.setVisibility(View.GONE);
        mOopsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUserId(String user_id) {
        mUserId = user_id;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        return true;
    }
}
