package com.graduate.outofclass.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.graduate.outofclass.R;
import com.graduate.outofclass.bean.PlanBean;

import java.util.List;

public class AfterOfSchoolAdapter extends RecyclerView.Adapter<AfterOfSchoolAdapter.AfterOfSchoolViewHolder> {

    private Context context;
    private List<PlanBean> data;
    ItemClicker clicker;

    /**
     * 定义item点击事件的接口
     */
    public interface ItemClicker {
        void onItemClick(PlanBean p);
    }

    public AfterOfSchoolAdapter(Context context, ItemClicker itemClicker) {
        this.context = context;
        this.clicker = itemClicker;
    }

    @Override
    public void onBindViewHolder(final AfterOfSchoolViewHolder afterOfSchoolViewHolder, final int position) {

        String title = data.get(position).title;
        String goal = data.get(position).goal;

        afterOfSchoolViewHolder.title.setText(title);
        afterOfSchoolViewHolder.goal.setText(goal);
    }

    @Override
    public AfterOfSchoolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        final AfterOfSchoolViewHolder afterOfSchoolViewHolder = new AfterOfSchoolViewHolder(view);

        afterOfSchoolViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicker != null) {
                    clicker.onItemClick(data.get(afterOfSchoolViewHolder.getAdapterPosition()));
                }
            }
        });

        return afterOfSchoolViewHolder;
    }

    @Override
    public int getItemCount() {
        if (null != data) {
            return data.size();
        } else {
            return 0;
        }
    }

    //自定义Holder
    class AfterOfSchoolViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView goal;
        private LinearLayout linearLayout;

        public AfterOfSchoolViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.item_view);
            title = itemView.findViewById(R.id.title);
            goal = itemView.findViewById(R.id.goal);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * 从fragment传递数据到adapter
     * @param d
     */
    public void setData(List<PlanBean> d) {
        this.data = d;
        notifyDataSetChanged();
    }

}
