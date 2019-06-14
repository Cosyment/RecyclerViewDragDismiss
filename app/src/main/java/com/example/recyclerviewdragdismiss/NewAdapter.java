package com.example.recyclerviewdragdismiss;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created on 2019/6/14 0014
 * Created by hechao
 * Description
 */
public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewViewHolder> {
    private List<String> mDatas;

    public NewAdapter(List<String> mDatas) {
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public NewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0)
            return new TitleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_title, viewGroup, false));
        else
            return new ContentViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_browser_content, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder viewHolder, int i) {
        if (viewHolder instanceof TitleViewHolder) {
            viewHolder.textView.setText("title");
        } else if (viewHolder instanceof ContentViewHolder) {
            viewHolder.textView.setText(mDatas.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else return 1;
    }

    class TitleViewHolder extends NewViewHolder {
        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ContentViewHolder extends NewViewHolder {
        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class NewViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public NewViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textName);
        }
    }
}
