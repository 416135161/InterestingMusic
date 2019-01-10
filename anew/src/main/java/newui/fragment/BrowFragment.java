package newui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happy.interesting.music.R;

import newui.base.BaseFragment;

/**
 * Created by sjning
 * created on: 2019/1/10 下午5:14
 * description:
 */
public class BrowFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.brow_fragment, container, false);
        return rootView;
    }
}
