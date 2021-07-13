package com.example.myapplication.recycle;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<TestData> mDataset = new ArrayList<>();
    private IOnItemClickListener mItemClickListener;

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.onBind(position, mDataset.get(position));
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemCLick(position, mDataset.get(position));
                }
            }
        });
        holder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemLongCLick(position, mDataset.get(position));
                }
                return false;
            }

        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface IOnItemClickListener {

        void onItemCLick(int position, TestData data);

        void onItemLongCLick(int position, TestData data);
    }

    public MyAdapter(List<TestData> myDataset) {
        mDataset.addAll(myDataset);
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void addData(int position, TestData data) {
        mDataset.add(position, data);
        notifyItemInserted(position);
        if (position != mDataset.size()) {
            //刷新改变位置item下方的所有Item的位置,避免索引错乱
            notifyItemRangeChanged(position, mDataset.size() - position);
        }
    }

    public void removeData(int position) {
        if (null != mDataset && mDataset.size() > position) {
            mDataset.remove(position);
            notifyItemRemoved(position);
            if (position != mDataset.size()) {
                //刷新改变位置item下方的所有Item的位置,避免索引错乱
                notifyItemRangeChanged(position, mDataset.size() - position);
            }
        }
    }

    public void changeData(int position){

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIndex;
        private TextView tvTitle;
        private TextView tvHot;
        private TextView tvType;
        private View contentView;

        public MyViewHolder(View v){
            super(v);
            contentView = v;
            tvIndex = v.findViewById(R.id.tv_index);
            tvTitle = v.findViewById(R.id.tv_title);
            tvHot = v.findViewById(R.id.tv_hot);
            tvType = v.findViewById(R.id.tv_type);
        }

        public void onBind(int position, TestData data){

            tvTitle.setText(data.title);
            tvTitle.setTextColor(Color.parseColor("#000000"));
            tvHot.setText(new StringBuilder().append(data.hot).append(" ").toString());
            tvHot.setTextColor(Color.parseColor("#000000"));
            if(position == 0){
                tvIndex.setText(new StringBuilder().append("TOP ").toString());
                tvIndex.setTextColor(Color.parseColor("#FF0000"));
            }
            else if (position>=1&&position < 4) {
                tvIndex.setText(new StringBuilder().append(position).append(".  ").toString());
                tvIndex.setTextColor(Color.parseColor("#FFD700"));
            } else {
                tvIndex.setText(new StringBuilder().append(position).append(".  ").toString());
                tvIndex.setTextColor(Color.parseColor("#000000"));
            }

            if(data.type == 1){
                tvType.setText(new StringBuilder().append(" 热 ").toString());
                tvType.setBackgroundColor(Color.parseColor("#FFD700"));
            }
            else if(data.type == 2){
                tvType.setText(new StringBuilder().append(" 新 ").toString());
                tvType.setBackgroundColor(Color.parseColor("#FF0000"));
            }
            else{
                tvType.setText(new StringBuilder());
            }
        }
        public void setOnClickListener(View.OnClickListener listener) {
            if (listener != null) {
                contentView.setOnClickListener(listener);
            }
        }

        public void setOnLongClickListener(View.OnLongClickListener listener) {
            if (listener != null) {
                contentView.setOnLongClickListener(listener);
            }
        }
    }
}
