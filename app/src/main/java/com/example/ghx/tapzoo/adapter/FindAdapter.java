package com.example.ghx.tapzoo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ghx.tapzoo.R;
import com.example.ghx.tapzoo.bean.Animal;
import com.example.ghx.tapzoo.ui.activity.AnimalActivity;

import java.util.List;

/**
 * Created by ghx on 2019/5/26.
 * 发现适配器
 */

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> {

    private static final String ANIMAL_ID = "animal_id";

    private Context mContext;
    private List<Animal> mAnimalList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View animalView;

        ImageView iv_animal_pic;
        TextView tv_animal_name;

        public ViewHolder(View itemView) {
            super(itemView);

            animalView = itemView;

            iv_animal_pic = itemView.findViewById(R.id.iv_animal_pic);
            tv_animal_name = itemView.findViewById(R.id.tv_animal_name);
        }

    }


    public FindAdapter(Context context, List<Animal> animalList) {
        mContext = context;
        mAnimalList = animalList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.find_item, parent, false);
        final FindAdapter.ViewHolder holder = new FindAdapter.ViewHolder(view);

        holder.animalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Animal animal = mAnimalList.get(position);
                String animal_id = animal.getObjectId();
                Intent intent = new Intent(mContext, AnimalActivity.class);
                intent.putExtra(ANIMAL_ID, animal_id);
                mContext.startActivity(intent);
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animal animal = mAnimalList.get(position);
        String url = animal.getAnimalpic();
        if (url != null) {
            Glide.with(mContext).load(url).into(holder.iv_animal_pic);
        }
        holder.tv_animal_name.setText(animal.getAnimalname());
    }

    @Override
    public int getItemCount() {
        return mAnimalList.size();
    }


}
