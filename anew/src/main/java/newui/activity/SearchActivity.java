package newui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;


import com.newui.interesting.music.R;

import newui.base.BaseActivity;

/**
 * Created by sjning
 * created on: 2019/1/11 上午8:51
 * description:
 */
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {
    private SearchView mSearchView;
    private RecyclerView mRecycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        mSearchView.setOnQueryTextListener(this);
        mSearchView = findViewById(R.id.sv_search);
        mRecycleView = findViewById(R.id.rv_items);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
