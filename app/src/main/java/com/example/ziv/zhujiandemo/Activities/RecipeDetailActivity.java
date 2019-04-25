package com.example.ziv.zhujiandemo.Activities;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ziv.zhujiandemo.Models.Recipe;
import com.example.ziv.zhujiandemo.R;
import com.example.ziv.zhujiandemo.Tools.GetImageByUrl;
import com.example.ziv.zhujiandemo.Tools.SpUtils;

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<String> stepDescriptionList;
    private ArrayList<String> stepImgList;
    private Button addToKitchenbtn;
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        String recipeTitle = recipe.getTitle();
        String imageURL = recipe.getImage();
        String categorie = recipe.getCategorie();
        String description = recipe.getDescription();
        materialList = recipe.getMaterial();
        stepDescriptionList = recipe.getStepDescription();
        stepImgList = recipe.getStepImg();

        RecipeImgView = findViewById(R.id.detail_recipe_photo);
        RecipeCateTV = findViewById(R.id.recipe_categorie);
        RecipeDescriptionTV = findViewById(R.id.recipe_description);
        addToKitchenbtn = findViewById(R.id.add_to_kitchen);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        GetImageByUrl getImageByUrl = new GetImageByUrl();
        getImageByUrl.setImage(RecipeImgView, imageURL);
        RecipeCateTV.setText(categorie);
        RecipeDescriptionTV.setText(description);
        collapsingToolbarLayout.setTitle(recipeTitle);

        setScrollViewContent();


        new Thread() {//创建子线程配合btnhandler按钮UI进行初始化
            public void run() {

                try {
                    Message message = new Message();
                    message.what = 0;
                    List<Recipe> kitchenRecipes = SpUtils.getObject(getApplicationContext(), Recipe.class);
                    if (kitchenRecipes == null) {
                        kitchenRecipes = new ArrayList<>();
                    } else {
                        for (Recipe kitchenRecipe : kitchenRecipes) {
                            if (recipe.getTitle().equals(kitchenRecipe.getTitle())) {
                                message.what = 1;
                            }
                        }
                    }
                    btnhandler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


        addToKitchenbtn.setOnClickListener(new View.OnClickListener() {
            List<Recipe> kitchenRecipes = SpUtils.getObject(getApplicationContext(), Recipe.class);

            @Override
            public void onClick(View view) {
                //注意需要对kitchenRecipes进行判空，不然会出现空引用异常
                if (kitchenRecipes == null) {
                    kitchenRecipes = new ArrayList<>();
                }
                kitchenRecipes.add(recipe);
                SpUtils.putObject(RecipeDetailActivity.this, kitchenRecipes);
                Toast.makeText(RecipeDetailActivity.this, "已加入我的厨房,请在首页查看", Toast.LENGTH_LONG).show();
                addToKitchenbtn.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void setScrollViewContent() {
        //NestedScrollView下的LinearLayout
        LinearLayout layout = (LinearLayout) findViewById(R.id.recipe_detial_content);

        //添加materialList
        for (int i = 0; i < materialList.size(); i++) {
            String[] split = materialList.get(i).split(":");
            View viewMaterial = View.inflate(RecipeDetailActivity.this, R.layout.recipe_row_item, null);
            ((TextView) viewMaterial.findViewById(R.id.item_name)).setText(split[0]);
            ((TextView) viewMaterial.findViewById(R.id.item_count)).setText(split[1]);
            //动态添加 子View
            layout.addView(viewMaterial);
        }

        //添加stepList
        for (int i = 0; i < stepImgList.size(); i++) {
            View viewStep = View.inflate(RecipeDetailActivity.this, R.layout.recipe_step_item, null);
            ((TextView) viewStep.findViewById(R.id.step_item_count_name)).setText("Step "+(i+1));
            GetImageByUrl getImageByUrl = new GetImageByUrl();
            getImageByUrl.setImage(((ImageView)viewStep.findViewById(R.id.step_item_img)),stepImgList.get(i));
            ((TextView) viewStep.findViewById(R.id.step_item_description)).setText(stepDescriptionList.get(i));
            //动态添加 子View
            layout.addView(viewStep);
        }
    }

    @SuppressLint("HandlerLeak")
    public Handler btnhandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    addToKitchenbtn.setText("加入我的厨房");
                    break;
                case 1:
                    addToKitchenbtn.setVisibility(View.INVISIBLE);
                    break;
            }

        }
    };

}
