package newui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.newui.interesting.music.R;

import newui.base.BaseFragment;

/**
 * Created by sjning
 * created on: 2019/1/10 下午5:14
 * description:
 */
public class BrowFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mIvBack, mIvSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.brow_fragment, container, false);
        mIvBack = rootView.findViewById(R.id.iv_back);
        mIvSearch = rootView.findViewById(R.id.iv_right);

        initView();
        return rootView;
    }


    private void initView() {
        mIvBack.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {

        } else if (v.getId() == R.id.iv_right) {

        }
    }
}
