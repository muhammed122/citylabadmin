package com.muhammed.citylabadmin.ui.adapter.result;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.muhammed.citylabadmin.R;
import com.muhammed.citylabadmin.helper.FileData;

import java.util.ArrayList;
import java.util.List;

public class ResultImageAdapter extends RecyclerView.Adapter<ResultImageAdapter.ResultImageHolder> {


    private List<FileData> images = new ArrayList<>();

    public void addImage(List<FileData> image) {
        this.images = image;
        notifyDataSetChanged();
    }


    private ResultFileClickListener clickListener;

    public ResultImageAdapter(ResultFileClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ResultImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_choose_item, parent, false);
        return new ResultImageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultImageHolder holder, int position) {
        holder.image.setImageBitmap(images.get(position).getBitmap());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.image.setImageResource(R.drawable.ic_camera);
                clickListener.removeFile(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ResultImageHolder extends RecyclerView.ViewHolder {
        AppCompatImageView image;
        AppCompatImageButton remove;

        public ResultImageHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.result_image);
            remove = itemView.findViewById(R.id.remove_result_icon);
        }
    }
}
