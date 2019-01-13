package newui.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newui.interesting.music.R;

/**
 * Created by sjning
 * created on: 2019/1/11 下午3:03
 * description:
 */
public class HomeBottomItemView extends LinearLayout {

    TextView tv;
    ImageView iv;

    public HomeBottomItemView(Context context) {
        this(context, null);
    }

    public HomeBottomItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBottomItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.new_main_bottom_item_view, this, true);
        tv = findViewById(R.id.tv_bottom);
        iv = findViewById(R.id.iv_bottom);
    }

    public void setText(String text) {
        tv.setText(text);
    }

    public void setImage(@DrawableRes int id) {
        iv.setImageResource(id);
    }
}
