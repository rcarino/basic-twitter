package com.codepath.apps.basictwitter.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.fragments.SearchResultsFragment;

public class SearchResultsActivity extends BaseTwitterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        String query = getIntent().getStringExtra("query");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SearchResultsFragment searchResultsFragment = SearchResultsFragment.newInstance(query);
        ft.replace(R.id.flSearchResults, searchResultsFragment);
        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
