package com.example.ziv.zhujiandemo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ziv.zhujiandemo.Helpers.RecipeItemClickListener;
import com.example.ziv.zhujiandemo.Models.Recipe;
import com.example.ziv.zhujiandemo.R;
import com.example.ziv.zhujiandemo.Tools.GetImageByUrl;

import java.util.List;

public class RecommendRecipeAdapter extends RecyclerView.Adapter<RecommendRecipeAdapter.MyViewHolder> {

    Context context ;
    List<Recipe> rData;
    RecipeItemClickListener recipeItemClickListener;

    public RecommendRecipeAdapter(Context context, List<Recipe> rData,RecipeItemClickListener listener) {
        this.context = context;
        this.rData = rData;
        this.recipeItemClickListener = listener;

    }

    @NonNull
    @Override
    public RecommendRecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendRecipeAdapter.MyViewHolder myViewHolder , int i) {
        myViewHolder.TvTitle.setText(rData.get(i).getTitle());
        GetImageByUrl getImageByUrl = new GetImageByUrl();
        getImageByUrl.setImage(myViewHolder.ImgRecipe,rData.get(i).getPhoto());
    }

    @Override
    public int getItemCount() {
        return rData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView TvTitle;
        private ImageView ImgRecipe;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvTitle = itemView.findViewById(R.id.item_recipe_title);
            ImgRecipe = itemView.findViewById(R.id.item_recipe_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recipeItemClickListener.onRecipeClick(rData.get(getAdapterPosition()),ImgRecipe);


                }
            });

        }
    }
}
