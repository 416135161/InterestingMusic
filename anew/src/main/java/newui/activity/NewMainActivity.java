package newui.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.happy.interesting.music.R;

import newui.base.BaseActivity;


/**
 * Created by sjning
 * created on: 2019/1/10 下午4:26
 * description:
 */
public class NewMainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main_activity);
    }
}
