package newui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class CommonRecycleAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    protected Context mContext;
    protected LayoutInflater layoutInflater;

    protected List<T> dataList;

    protected int layoutId;

    public CommonRecycleAdapter(Context context, List<T> dataList, int layoutId) {
        mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.layoutId = layoutId;
    }

    public void addData(List<T> list){
        if(list != null && !list.isEmpty()){
            this.dataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(layoutId, parent, false);
        return new CommonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        bindData(holder, dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<T> getDataList() {
        return dataList;
    }

    abstract void bindData(CommonViewHolder holder, T data);



}
