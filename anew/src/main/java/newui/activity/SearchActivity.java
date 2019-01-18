package newui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.newui.interesting.music.R;
import com.old.interesting.music.Config;
import com.old.interesting.music.intercepter.HttpUtil;
import com.old.interesting.music.intercepter.QueryInterceptor;
import com.old.interesting.music.intercepter.TransformUtil;
import com.old.interesting.music.interfaces.StreamService;
import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.searchResponse.SearchResponseBean;
import com.old.interesting.music.utilities.CommonUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import newui.adapter.SearchListAdapter;
import newui.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sjning
 * created on: 2019/1/11 上午8:51
 * description:
 */
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {
    private SearchView mSearchView;
    private RecyclerView mRecycleView;
    private SearchListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        mSearchView = findViewById(R.id.sv_search);
        mSearchView.setOnQueryTextListener(this);
        mRecycleView = findViewById(R.id.rv_items);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchListAdapter(this, new ArrayList<Track>());
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        CommonUtils.hideKeyboard(this);
        searchCloudTrack(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void searchCloudTrack(String query) {
        startLoadingIndicator();
        StreamService ss = HttpUtil.getApiService(Config.API_SERACH, new QueryInterceptor());
        String encodeStr = "";
        try {
            encodeStr = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Call<SearchResponseBean> call = ss.searchSong(query, 20);
        call.enqueue(new Callback<SearchResponseBean>() {

            @Override
            public void onResponse(Call<SearchResponseBean> call, Response<SearchResponseBean> response) {
                if (response.isSuccessful()) {
                    mAdapter.setDataList(TransformUtil.searchResponse2Track(response.body()));
                }
                stopLoadingIndicator();
                Log.d("RETRO", response.body() + "");
            }

            @Override
            public void onFailure(Call<SearchResponseBean> call, Throwable t) {
                Log.d("RETRO1", t.getMessage());
                stopLoadingIndicator();
            }

        });
    }
}
