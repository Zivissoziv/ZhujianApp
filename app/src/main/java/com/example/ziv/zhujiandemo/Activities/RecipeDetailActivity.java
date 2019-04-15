package com.example.ziv.zhujiandemo.Activities;


import android.app.ActionBar;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ziv.zhujiandemo.Models.Recipe;
import com.example.ziv.zhujiandemo.R;
import com.example.ziv.zhujiandemo.Tools.GetImageByUrl;

import java.util.ArrayList;

/**
 * 菜单详情页Activity
 * 主页点击菜单图片即可进入此Activity
 * 需要从ActivityIntent中获取菜单的传递过来相关信息进行展示
 */

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView RecipeImgView;
    private TextView RecipeCateTV;
    private TextView RecipeDescriptionTV;
    private ArrayList<String> materialList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        String recipeTitle = getIntent().getExtras().getString("title");
        String imageURL = getIntent().getExtras().getString("imgURL");
        String categorie = getIntent().getExtras().getString("categorie");
        String description = getIntent().getExtras().getString("description");
        materialList = getIntent().getExtras().getStringArrayList("materialList");

        RecipeImgView = findViewById(R.id.detail_recipe_photo);
        RecipeCateTV = findViewById(R.id.recipe_categorie);
        RecipeDescriptionTV = findViewById(R.id.recipe_description);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        GetImageByUrl getImageByUrl = new GetImageByUrl();
        getImageByUrl.setImage(RecipeImgView,imageURL);
        RecipeCateTV.setText(categorie);
        RecipeDescriptionTV.setText(description);
        collapsingToolbarLayout.setTitle(recipeTitle);

        setScrollViewContent();
    }

    private void setScrollViewContent() {
        //NestedScrollView下的LinearLayout
        LinearLayout layout = (LinearLayout) findViewById(R.id.recipe_detial_content);



        for (int i = 0; i < materialList.size(); i++) {
            String[] split = materialList.get(i).split(":");
            View view = View.inflate(RecipeDetailActivity.this, R.layout.recipe_row_item, null);
            ((TextView) view.findViewById(R.id.item_name)).setText(split[0]);
            ((TextView) view.findViewById(R.id.item_count)).setText(split[1]);
            //动态添加 子View
            layout.addView(view, i);
        }
    }





}
