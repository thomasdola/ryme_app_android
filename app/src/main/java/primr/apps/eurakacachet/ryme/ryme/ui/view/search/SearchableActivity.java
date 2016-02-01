package primr.apps.eurakacachet.ryme.ryme.ui.view.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Searchable;

public class SearchableActivity extends AppCompatActivity implements SearchableMvpView{

    @Inject SearchablePresenter mSearchablePresenter;

    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            mQuery = intent.getStringExtra(SearchManager.QUERY);
        }

        performSearch(mQuery);
    }

    private void performSearch(String query) {
        List<Searchable> result = new ArrayList<>();
        result = mSearchablePresenter.performSearch(query);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
