package com.example.chatandloginmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Chat> localDataSet;
    String myId = "";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.tv);
        }

        public TextView getTextView() {
            return textView;
        }
    }
    public RecyclerViewAdapter(ArrayList<Chat> dataSet, String id) {
        localDataSet = dataSet;
        this.myId = id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.left_text_item, viewGroup, false);
        if(viewType == 1) {
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
        if(localDataSet.get(position).id.equals(myId)) { // 어레이 리스트의 아이디가 내 아이디면
            return 1;
        } else {
            return 2;
        }
    }
}