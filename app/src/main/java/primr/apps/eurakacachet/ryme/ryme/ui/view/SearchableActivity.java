package primr.apps.eurakacachet.ryme.ryme.ui.view;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import primr.apps.eurakacachet.ryme.ryme.R;

public class SearchableActivity extends AppCompatActivity {

    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            mQuery = intent.getStringExtra(SearchManager.QUERY);
        }
    }
}
