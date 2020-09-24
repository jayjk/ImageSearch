package com.test.imagesearch.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.imagesearch.ImageDetailActivity;
import com.test.imagesearch.R;
import com.test.imagesearch.models.MasterData;

import java.util.ArrayList;

public class ImageSearchAdapter extends RecyclerView.Adapter<ImageSearchAdapter.CandidateViewHolder> {

    Context context;
    ArrayList<MasterData> candidateData;

    public ImageSearchAdapter(Context context, ArrayList<MasterData> articles) {
        this.context = context;
        this.candidateData = articles;
    }

    @NonNull
    @Override
    public ImageSearchAdapter.CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_img, parent, false);
        return new  CandidateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSearchAdapter.CandidateViewHolder holder, final int position) {
        holder.tv_title.setText(candidateData.get(position).getTitle());


        RequestOptions rq = new RequestOptions().placeholder(android.R.drawable.stat_sys_download);

        if (candidateData.get(position).getImages()!=null)
        {
            Glide.with(context)
                    .load(candidateData.get(position).getImages().get(0).getLink())
                    .override(500, 600)
                    .apply(rq)
                    .into(holder.iv_img);
        }



        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (candidateData.get(position).getImages()!=null)
                {
                    Intent i = new Intent(context, ImageDetailActivity.class);
                    i.setAction(Intent.ACTION_SEND);
                    i.putExtra("id",candidateData.get(position).getUniqueID());
                    context.startActivity(i);
                }else{
                    Toast.makeText(context,"Issue with the Data",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    @Override
    public int getItemCount() {
        return candidateData.size();
    }

    public class CandidateViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title;
        ImageView iv_img;
        View view;

        public CandidateViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            view = itemView;

        }
    }

}