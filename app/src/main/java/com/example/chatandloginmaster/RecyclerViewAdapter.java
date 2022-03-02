package com.example.chatandloginmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Chat> localDataSet;
    String myEmail = "";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv;

        public ViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
        }

        public TextView getTextView() {
            return tv;
        }
    }
    public RecyclerViewAdapter(ArrayList<Chat> dataSet, String email) {
        localDataSet = dataSet;
        this.myEmail = email;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.left_text_item, viewGroup, false);
        if(viewType == 1) { // 내 아이디면 말풍선을 오른쪽에 만듬
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.right_text_item, viewGroup, false);
        }
        RecyclerView.ViewHolder holder = new RecyclerViewAdapter.ViewHolder(itemView);
        return (ViewHolder) holder;
        //return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(localDataSet.get(position).getStr());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(localDataSet.get(position).email.equals(myEmail)) { // 어레이 리스트의 아이디가 내 아이디면
            return 1;
        } else {
            return 2;
        }
    }
}